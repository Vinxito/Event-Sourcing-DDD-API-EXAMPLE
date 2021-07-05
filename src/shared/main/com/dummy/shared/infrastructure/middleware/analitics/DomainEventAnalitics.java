package com.dummy.shared.infrastructure.middleware.analitics;

import com.dummy.shared.infrastructure.spring.Service;
import io.prometheus.client.Counter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import java.io.IOException;

@Service
public class DomainEventAnalitics implements Filter {

    static final Counter counter = Counter.build().name("event_counter")
            .labelNames("event").help("Counter of events.").register();
    private final RequestMappingHandlerMapping mapping;

    public DomainEventAnalitics(RequestMappingHandlerMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception exception) {
        }
    }

}
