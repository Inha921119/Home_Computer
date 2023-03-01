package com.Inha.java.BAM.dto;

public class Article extends Dto {
	public String title;
	public String body;
	public int viewcount;
	public int memberId;
	public String LastModifyDate;
	
	public Article(int id, String regDate, int memberId, String title, String body){
		this(id, regDate, memberId, title, body, 0);
	}
	
	public Article(int id, String regDate, int memberId, String title, String body, int viewcount) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.viewcount = viewcount;
		this.memberId = memberId;
	}

	public void increseViewCount() {
		viewcount++;
	}
}