package com.dummy.shared.infrastructure.middleware.errors;

import com.dummy.shared.domain.DomainError;
import com.dummy.shared.domain.Utils;
import com.dummy.shared.domain.bus.command.CommandHandlerExecutionError;
import com.dummy.shared.domain.bus.query.QueryHandlerExecutionError;
import com.dummy.shared.infrastructure.spring.ApiController;
import com.dummy.shared.infrastructure.spring.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.NestedServletException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public final class MiddlewareFailuresHandler implements Filter {

    private final RequestMappingHandlerMapping mapping;
    private final Logger log;

    public MiddlewareFailuresHandler(RequestMappingHandlerMapping mapping, Logger log) {
        this.mapping = mapping;
        this.log = log;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException {

        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        HttpServletResponse httpResponse = ((HttpServletResponse) response);

        try {
            Optional<HandlerExecutionChain> preBean = Optional.ofNullable(mapping.getHandler(httpRequest));
            Object possibleController = null;

            if (preBean.isPresent())
                possibleController = ((HandlerMethod) Objects.requireNonNull(preBean.get()).getHandler()).getBean();

            try {
                chain.doFilter(request, response);
            } catch (NestedServletException exception) {
                if (possibleController instanceof ApiController)
                    handleCustomError(response, httpResponse, (ApiController) possibleController, exception);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw new ServletException(exception);
        }
    }

    private void handleCustomError(ServletResponse response, HttpServletResponse httpResponse,
                                   ApiController possibleController, NestedServletException exception) throws Exception {

        HashMap<Class<? extends DomainError>, HttpStatus> errorMapping = possibleController.errorMapping();

        Throwable error = (exception.getCause() instanceof CommandHandlerExecutionError ||
                exception.getCause() instanceof QueryHandlerExecutionError
                ? exception.getCause().getCause() : exception.getCause());

        try {
            int statusCode = statusFor(errorMapping, error);
            String errorCode = errorCodeFor(error);
            String errorMessage = error.getMessage();

            httpResponse.reset();
            httpResponse.setHeader("Content-Type", "application/json");
            httpResponse.setStatus(statusCode);
            PrintWriter writer = response.getWriter();
            writer.write(String.format(
                    "{\"error_code\": \"%s\", \"message\": \"%s\"}",
                    errorCode,
                    errorMessage
            ));
            writer.close();

            log.error(errorMessage, exception);

        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
            throw new Exception(error);
        }
    }

    private String errorCodeFor(Throwable error) {

        if (error instanceof DomainError)
            return ((DomainError) error).errorCode();

        return Utils.toSnake(error.getClass().toString());
    }

    private int statusFor(HashMap<Class<? extends DomainError>, HttpStatus> errorMapping, Throwable error) {
        return errorMapping.getOrDefault(error.getClass(), HttpStatus.INTERNAL_SERVER_ERROR).value();
    }
}
