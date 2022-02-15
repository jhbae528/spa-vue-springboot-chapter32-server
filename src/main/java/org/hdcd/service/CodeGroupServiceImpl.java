package org.hdcd.service;

import java.util.List;

import org.hdcd.domain.CodeGroup;
import org.hdcd.repository.CodeGroupRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CodeGroupServiceImpl implements CodeGroupService {

	private CodeGroupRepository repository;
	
	@Override
	public void register(CodeGroup codeGroup) throws Exception {
		repository.save(codeGroup);
		
	}

	@Override
	public List<CodeGroup> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "groupCode"));
	}

}
