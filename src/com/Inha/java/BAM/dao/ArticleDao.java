package com.Inha.java.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.Inha.java.BAM.container.Container;
import com.Inha.java.BAM.dto.Article;
import com.Inha.java.BAM.dto.Member;


public class ArticleDao extends Dao {
	public List<Article> articles;

	public ArticleDao() {
		this.articles = new ArrayList<>();
	}
	
	public void add(Article article) {
		articles.add(article);
		lastId++;
	}

	public List<Article> getPrintArticles(String searchKeyword) {
		
		if (searchKeyword != null) {

			List<Article> printArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					printArticles.add(article);
				}
			}
			return printArticles;
		}
		return articles;
	}
	public List<Member> getWriteMember() {
		String shortTitle = null;
		
		
		if (Container.articleDao.length() > 5) {
			shortTitle = article.title.substring(0, 3) + "...";
		} else {
			shortTitle = article.title;
		}

		return null;
	}
}