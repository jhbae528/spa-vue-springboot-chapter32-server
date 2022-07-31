package org.hdcd.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hdcd.domain.CodeGroup;
import org.hdcd.service.CodeGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/codegroups")
public class CodeGroupController {

    private final CodeGroupService service;

    // 목록
    @GetMapping
    public ResponseEntity<List<CodeGroup>> list() throws Exception {
        log.info("list");
        return new ResponseEntity<List<CodeGroup>>(service.list(), HttpStatus.OK);
    }

    // 상세 조회
    @GetMapping("/{groupCode}")
    public ResponseEntity<CodeGroup> read(@PathVariable("groupCode") String groupCode) throws Exception {
        CodeGroup codeGroup = service.read(groupCode);
        return new ResponseEntity<CodeGroup>(codeGroup, HttpStatus.OK);
    }

    // 등록
    @PostMapping
    public ResponseEntity<CodeGroup> register(@Validated @RequestBody CodeGroup codeGroup) throws Exception {
        log.info("register");
        service.register(codeGroup);
        log.info("register codeGroup = " + codeGroup.getGroupCode());
        return new ResponseEntity<CodeGroup>(codeGroup, HttpStatus.OK);
    }

    // 수정
    @PutMapping("/{groupCode}")
    public ResponseEntity<CodeGroup> modify(@PathVariable("groupCode") String groupCode, @Validated @RequestBody CodeGroup codeGroup) throws Exception {
        codeGroup.setGroupCode(groupCode);
        service.modify(codeGroup);
        return new ResponseEntity<CodeGroup>(codeGroup, HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping("/{groupCode}")
    public ResponseEntity<Void> remove(@PathVariable("groupCode") String groupCode) throws Exception {
        service.remove(groupCode);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
