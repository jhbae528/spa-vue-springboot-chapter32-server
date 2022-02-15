package org.hdcd.controller;

import java.util.List;

import org.hdcd.domain.CodeGroup;
import org.hdcd.service.CodeGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	// 등록
	@PostMapping
	public ResponseEntity<CodeGroup> register(@Validated @RequestBody CodeGroup codeGroup) throws Exception {
		
		log.info("register");
		
		service.register(codeGroup);
		
		log.info("register codeGroup = " + codeGroup.getGroupCode());		
		
		return new ResponseEntity<CodeGroup>(codeGroup, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CodeGroup>> list() throws Exception {
		
		log.info("list");
		
		return new ResponseEntity<List<CodeGroup>>(service.list(), HttpStatus.OK);
	}
}
