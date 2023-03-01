package com.Inha.java.BAM.service;

import java.util.List;

import com.Inha.java.BAM.container.Container;
import com.Inha.java.BAM.dto.Article;


public class ArticleService {

	public List<Article> getPrintArticles(String searchKeyword) {
		return Container.articleDao.getPrintArticles(searchKeyword);
	}

}