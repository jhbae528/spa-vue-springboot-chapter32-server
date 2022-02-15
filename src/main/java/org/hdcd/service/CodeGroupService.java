package org.hdcd.service;

import java.util.List;

import org.hdcd.domain.CodeGroup;

public interface CodeGroupService {

	public void register(CodeGroup codeGroup) throws Exception;
	
	public List<CodeGroup> list() throws Exception;
}
