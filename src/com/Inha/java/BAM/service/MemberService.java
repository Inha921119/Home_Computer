package com.Inha.java.BAM.service;

import com.Inha.java.BAM.container.Container;
import com.Inha.java.BAM.dto.Member;

public class MemberService {
	public boolean loginIdDupChk(String loginId) {
		return Container.memberDao.loginIdDupChk(loginId);
	}
	
	public boolean mobileNumDupChk(String mobileNum) {
		return Container.memberDao.mobileNumDupChk(mobileNum);
	}

	public void add(Member member) {
		Container.memberDao.add(member);
	}
}