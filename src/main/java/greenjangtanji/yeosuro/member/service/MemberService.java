package greenjangtanji.yeosuro.member.service;

import greenjangtanji.yeosuro.member.entity.Member;
import greenjangtanji.yeosuro.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member createUser(String username) {
        Member user = new Member();
        user.setUsername(username);
        return memberRepository.save(user);
    }
}
