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

	public String getShortTitle(String Title) {
		if (Title.length() > 5) {
			return Title.substring(0, 3) + "...";
		} else {
			return Title;
		}
	}

	public String getWriteMemberName(int id) {
		for (Member member : Container.memberDao.members) {
				if (id == member.id) {
					return member.name;
			}
		}
		return null;
	}
}