package com.doo.aqqle.member.service;


import com.doo.aqqle.model.member.MemberRequest;
import com.doo.aqqle.model.stock.InvestmentRequest;
import com.doo.aqqle.repository.Investments;
import com.doo.aqqle.repository.InvestmentsRepository;
import com.doo.aqqle.repository.member.Members;
import com.doo.aqqle.repository.member.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MembersRepository membersRepository;

    @Transactional
    public void post(MemberRequest request) {
        membersRepository.save(Members.builder()
                .userId(request.getUserId())
                .name(request.getName())
                .use(request.getUseYn())
                .build());
    }
}
