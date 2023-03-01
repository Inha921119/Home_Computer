package com.Inha.java.BAM.service;

import java.util.List;

import com.Inha.java.BAM.container.Container;
import com.Inha.java.BAM.dto.Member;


public class MemberService {

	public List<Member> getWriteMember() {
		return Container.memberDao.getWriteMember();
	}

}