package com.doo.aqqle.investment.service;


import com.doo.aqqle.repository.Stock;
import com.doo.aqqle.repository.StockRepository;
import com.doo.aqqle.model.stock.StockManageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockManageService {

    private final StockRepository stockRepository;

    @Transactional
    public void post(StockManageRequest request) {
        stockRepository.save(Stock.builder()
                .company(request.getCompany())
                .companyCode(request.getCompanyCode())
                .exchange(request.getExchange())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .type(request.getType())
                .use(request.getUseYn())
                .build());
    }
}
