package com.doo.aqqle.investment.service;


import com.doo.aqqle.repository.Investments;
import com.doo.aqqle.repository.InvestmentsRepository;
import com.doo.aqqle.model.stock.InvestmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentsRepository investmentsRepository;

    @Transactional
    public void post(InvestmentRequest request) {
        investmentsRepository.save(Investments.builder()
                .company(request.getCompany())
                .exchange(request.getExchange())
                .type(request.getType())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .use(request.getUseYn())
                .build());
    }
}
