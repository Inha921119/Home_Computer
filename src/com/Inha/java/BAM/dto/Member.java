package com.Inha.java.BAM.dto;

public class Member {
	public int idNum;
	public String signupdate;
	public String id;
	public String passwords;
	public String name;
	public String callnumber;

	public Member(int idNum, String signupdate, String id, String passwords, String name, String callnumber) {
		this.idNum = idNum;
		this.signupdate = signupdate;
		this.id = id;
		this.passwords = passwords;
		this.name = name;
		this.callnumber = callnumber;
	}
}