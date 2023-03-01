package com.Inha.java.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.Inha.java.BAM.dto.Member;


public class MemberDao extends Dao {
	public List<Member> members;

	public MemberDao() {
		this.members = new ArrayList<>();
	}
	public void add(Member member) {
		members.add(member);
		lastId++;
	}
	
	public boolean loginIdDupChk(String loginId) {
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return false;
			}
		}
		return true;
	}

	public boolean mobileNumDupChk(String mobileNum) {
		for (Member member : members) {
			if (member.mobileNum.equals(mobileNum)) {
				return false;
			}
		}
		return true;
	}
}
