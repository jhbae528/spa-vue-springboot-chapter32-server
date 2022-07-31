package org.hdcd.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hdcd.domain.Member;
import org.hdcd.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    // 비밀번호 암호처리기
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<Member>> list() throws Exception {
        log.info("list");
        List<Member> response = memberService.list();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userNo}")
    public ResponseEntity<Member> read(@PathVariable("userNo") Long userNo) throws Exception {
        log.info("read");
        Member response = memberService.read(userNo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Member> register(@Validated @RequestBody Member member) throws Exception {
        log.info("register member.getUserName() = " + member.getUserName());

        String inputPassword = member.getUserPw();
        member.setUserPw(passwordEncoder.encode(inputPassword));

        memberService.register(member);

        log.info("register member.getUserNo() = " + member.getUserNo());
        return new ResponseEntity<Member>(member, HttpStatus.OK);
    }

    @PutMapping("/{userNo}")
    public ResponseEntity<Member> modify(@PathVariable("userNo") Long userNo, @RequestBody Member member) throws Exception {
        log.info("modify : member.getUserName() = " + member.getUserName());
        log.info("modify : userNo = " + userNo);
        member.setUserNo(userNo);
        memberService.modify(member);
        return new ResponseEntity<Member>(member, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(@PathVariable("userNo") Long userNo) throws Exception {
        log.info("remove");
        memberService.remove(userNo);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
