package org.hdcd.service;

import org.hdcd.domain.CodeGroup;
import org.hdcd.repository.CodeGroupRepository;

public class CodeGroupServiceImpl implements CodeGroupService {

	private CodeGroupRepository repository;
	
	@Override
	public void register(CodeGroup codeGroup) throws Exception {
		repository.save(codeGroup);
		
	}

}
