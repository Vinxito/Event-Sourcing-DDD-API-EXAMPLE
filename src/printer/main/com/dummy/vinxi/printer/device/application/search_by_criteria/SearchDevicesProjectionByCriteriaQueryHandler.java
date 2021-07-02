package com.dummy.vinxi.printer.device.application.search_by_criteria;

import com.dummy.vinxi.printer.device.application.DevicesQueryResponse;
import com.dummy.vinxi.shared.domain.bus.query.QueryHandler;
import com.dummy.vinxi.shared.domain.criteria.Filters;
import com.dummy.vinxi.shared.domain.criteria.Order;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

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
