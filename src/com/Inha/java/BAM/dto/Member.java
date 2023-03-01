package com.Inha.java.BAM.dto;

public class Member extends Dto {
	public String loginId;
	public String loginPw;
	public String name;
	public String mobileNum;
	public String lastLoginDate;

	public Member(int id, String regDate, String loginId, String loginPw, String name, String mobileNum) {
		this.id = id;
		this.regDate = regDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
		this.mobileNum = mobileNum;
		this.lastLoginDate = "접속기록이 없습니다";
	}
}