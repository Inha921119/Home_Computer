package com.Inha.java.BAM.container;

import com.Inha.java.BAM.dao.ArticleDao;
import com.Inha.java.BAM.dao.MemberDao;
import com.Inha.java.BAM.service.ArticleService;
import com.Inha.java.BAM.service.MemberService;

public class Container {
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	public static ArticleService articleService;
	public static MemberService memberService;
	
	static {
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		articleService = new ArticleService();
		memberService = new MemberService();
	}
}