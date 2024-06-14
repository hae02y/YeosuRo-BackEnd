package greenjangtanji.yeosuro.member.controller;

import greenjangtanji.yeosuro.member.entity.Member;
import greenjangtanji.yeosuro.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/create")
    public Member createUser(@RequestBody String username ) {
        return memberService.createUser(username);
    }
}
