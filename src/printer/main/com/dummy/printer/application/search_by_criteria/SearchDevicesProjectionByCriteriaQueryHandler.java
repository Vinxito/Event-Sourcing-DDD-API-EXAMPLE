package com.dummy.printer.application.search_by_criteria;

import com.dummy.printer.application.DevicesQueryResponse;
import com.dummy.shared.domain.bus.query.QueryHandler;
import com.dummy.shared.domain.criteria.Filters;
import com.dummy.shared.domain.criteria.Order;
import com.dummy.shared.infrastructure.spring.Service;

@Service
public final class SearchDevicesProjectionByCriteriaQueryHandler implements QueryHandler<SearchDevicesByCriteriaQuery, DevicesQueryResponse> {

    private final DevicesProjectionByCriteriaSearcher searcher;

    public SearchDevicesProjectionByCriteriaQueryHandler(DevicesProjectionByCriteriaSearcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public DevicesQueryResponse handle(SearchDevicesByCriteriaQuery query) {

        Filters filters = Filters.fromValues(query.filters());
        Order order = Order.fromValues(query.orderBy(), query.orderType());

        return searcher.search(filters, order, query.limit(), query.offset());
    }
}
