package org.hdcd.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hdcd.common.security.domain.CustomUser;
import org.hdcd.domain.Member;
import org.hdcd.service.MemberService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    private final MessageSource messageSource;

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

    @DeleteMapping("/{userNo}")
    public ResponseEntity<Void> remove(@PathVariable("userNo") Long userNo) throws Exception {
        log.info("remove");
        memberService.remove(userNo);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "/setup", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> setupAdmin(@Validated @RequestBody Member member) throws Exception {
        log.info("setup admin : member.getUserName() = " + member.getUserName());
        log.info("setup admin : service.countAll() = " + memberService.countAll());

        if (memberService.countAll() == 0) {
            String inputPassword = member.getUserPw();
            member.setUserPw(passwordEncoder.encode(inputPassword));
            member.setJob("00");
            memberService.setupAdmin(member);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } else {
            String message = messageSource.getMessage("common.cannotSetupAdmin", null, Locale.KOREAN);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 정보를 가져온다
    @GetMapping("/myinfo")
    public ResponseEntity<Member> getMyInfo(@AuthenticationPrincipal CustomUser customUser) throws Exception {
        Long userNo = customUser.getUserNo();
        log.info("Register userNo = " + userNo);

        Member member = memberService.read(userNo);

        member.setUserPw("");
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
}
