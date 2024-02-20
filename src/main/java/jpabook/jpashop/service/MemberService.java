package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.MemberListReponseDTO;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * 기본적으로 @Transactional 은 false
     */
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<MemberListReponseDTO> findMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(member ->new MemberListReponseDTO(member.getName(),member.getAddress(),member.getOrders()))
                .collect(Collectors.toList());

    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
