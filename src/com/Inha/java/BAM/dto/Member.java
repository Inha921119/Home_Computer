package com.Inha.java.BAM.dto;

public class Member extends Dto {
	public int id;
	public String regDate;
	public String loginId;
	public String loginPw;
	public String name;
	public String callNum;
	public String lastLoginDate = "접속기록이 없습니다";

	public Member(int id, String regDate, String loginId, String loginPw, String name, String callNum) {
		this.id = id;
		this.regDate = regDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
		this.callNum = callNum;
	}
}