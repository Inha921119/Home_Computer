package com.Inha.java.BAM.service;

import java.util.List;

import com.Inha.java.BAM.container.Container;
import com.Inha.java.BAM.dto.Article;


public class ArticleService {

	public void add(Article article) {
		Container.articleDao.add(article);
	}
	
	public List<Article> getPrintArticles(String searchKeyword) {
		return Container.articleDao.getPrintArticles(searchKeyword);
	}

	public String getShortTitle(String Title) {
		return Container.articleDao.getShortTitle(Title);
	}
	
	public String getWriteMemberName(int id) {
		return Container.articleDao.getWriteMemberName(id);
	}

}