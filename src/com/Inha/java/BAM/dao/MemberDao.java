package com.Inha.java.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.Inha.java.BAM.container.Container;
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
	public List<Member> getWriteMember() {
		String writerName;
		
		for (Member member : members) {
			if (Container.articleDao.articles.memberId == member.id) {
				Container.articleDao.articles.writerName = member.name;
				return writerName;
			}
		}
		return writerName;
	}
}
