package com.Inha.java.BAM.dto;

public class Article {
	public int id;
	public String title;
	public String body;
	public String regDate;
	public String regTime;
	public String LastModifyDate;
	public int viewcount;
	public String writer;

	public Article(int id, String regDate, String title, String body) {
		this(id, regDate, title, body, 0);
	}
	
	public Article(int id, String regDate, String title, String body, int viewcount) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.viewcount = viewcount;
	}

	public void increseViewCount() {
		viewcount++;
	}
}
