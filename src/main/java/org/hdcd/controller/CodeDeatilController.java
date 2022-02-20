package org.hdcd.controller;

import java.util.List;

import org.hdcd.domain.CodeDetail;
import org.hdcd.service.CodeDetailservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/codedetails")
public class CodeDeatilController {

	private final CodeDetailservice codeDetailservice;
	
	@GetMapping("/{groupCode}/{codeValue}")
	public ResponseEntity<CodeDetail> read(@PathVariable("groupCode") String groupCode, @PathVariable("codeValue") String codeValue) throws Exception {
		CodeDetail codeDetail = new CodeDetail();
		codeDetail.setGroupCode(groupCode);
		codeDetail.setCodeValue(codeValue);
		return new ResponseEntity<CodeDetail>(codeDetailservice.read(codeDetail), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CodeDetail>> list() throws Exception {
		log.info("list");
		
		return new ResponseEntity<>(codeDetailservice.list(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CodeDetail> register(@Validated @RequestBody CodeDetail codeDetail) throws Exception {
		log.info("register");
		
		codeDetailservice.register(codeDetail);
		
		log.info("codeClassNo = " + codeDetail.getGroupCode());
		log.info("codeValue = " + codeDetail.getCodeValue());
		
		return new ResponseEntity<CodeDetail>(codeDetail, HttpStatus.OK);
	}
	
	@PutMapping("/{groupCode}/{codeValue}")
	public ResponseEntity<CodeDetail> modify(@PathVariable("groupCode") String groupCode, 
			@PathVariable("codeValue") String codeValue, 
			@RequestBody CodeDetail codeDetail) throws Exception {
		codeDetail.setGroupCode(groupCode);
		codeDetail.setCodeValue(codeValue);
		
		codeDetailservice.modify(codeDetail);
		
		return new ResponseEntity<CodeDetail>(codeDetail, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{groupCode}/{codeValue}")
	public ResponseEntity<Void> remove(@PathVariable("groupCode") String groupCode, @PathVariable("codeValue") String CodeValue	) throws Exception {
		
		CodeDetail codeDetail = new CodeDetail();
		codeDetail.setGroupCode(groupCode);
		codeDetail.setCodeValue(CodeValue);
		
		codeDetailservice.remove(codeDetail);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
}
