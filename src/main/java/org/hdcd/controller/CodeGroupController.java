package org.hdcd.controller;

import java.util.List;

import org.hdcd.domain.CodeGroup;
import org.hdcd.service.CodeGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

		if(StringUtils.hasText(codeGroup.getGroupCode()) != true){
			throw new Exception("Empty GroupCode");
		}

		service.register(codeGroup);
		
		log.info("register codeGroup = " + codeGroup.getGroupCode());		
		
		return new ResponseEntity<CodeGroup>(codeGroup, HttpStatus.OK);
	}

	// 수정
	@PutMapping("/{groupCode}")
	public ResponseEntity<CodeGroup> modify(@PathVariable("groupCode") String groupCode, @Validated @RequestBody CodeGroup codeGroup) throws Exception {
		codeGroup.setGroupCode("a1");
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
