package com.Inha.java.BAM.controller;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.Inha.java.BAM.Util.Util;
import com.Inha.java.BAM.container.Container;
import com.Inha.java.BAM.dto.Article;
import com.Inha.java.BAM.dto.Member;


//@SuppressWarnings("unused")

public class ArticleController extends Controller {
	private List<Article> articles;
	private Scanner sc;
	private String command;

	public ArticleController(Scanner sc) {
		this.articles = Container.articleDao.articles;
		this.sc = sc;
	}

	@Override
	public void doAction(String command, String actionMethodName) {
		this.command = command;

		switch (actionMethodName) {
		case "list":
			showList();
			break;
		case "write":
			doWrite();
			break;
		case "detail":
			showDetail();
			break;
		case "delete":
			doDelete();
			break;
		case "modify":
			doModify();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			System.out.println("도움이 필요하시면 'help'를 입력하세요");
			break;
		}
	}

	private void showList() {
		String searchKeyword = command.substring("article list".length()).trim();

		System.out.println("검색어 : " + searchKeyword);
		
		List<Article> printArticles = Container.articleService.getPrintArticles(searchKeyword);

		if (printArticles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		System.out.println("|번호	|제목		|날짜		|작성자		|조회수		");
		Collections.reverse(printArticles);

		for (Article article : printArticles) {
			
			String writerName = Container.articleService.getWriteMemberName(article.memberId);
			String shortTitle = Container.articleService.getShortTitle(article.title);
			
			System.out.printf("|%d	|%s		|%s	|%.5s		|%d		\n", article.id, shortTitle,
					article.regDate.substring(0, 10), writerName, article.viewcount);
		}

	}

	private void doWrite() {
		int id = Container.articleDao.getLastId();
		System.out.printf("제목 : ");
		String title = sc.nextLine();

		System.out.printf("내용 : ");
		String body = sc.nextLine();

		String regDate = Util.getNowDateTime();

		Article article = new Article(id, regDate, loginedMember.id, title, body);

		Container.articleService.add(article);

		System.out.printf("%d번 글이 생성되었습니다.\n", id);
	}

	private void showDetail() {
		int id = getEndNum(command);
		if (id == -1) {
			System.out.println("명령어 뒤에 번호를 입력해주세요");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}

		
		if (loginedMember == null) {
			foundArticle.increseViewCount();
		} else if (loginedMember != null) {
			if (loginedMember.id != foundArticle.memberId) {
				foundArticle.increseViewCount();
			}
		}

		String writerName = null;

		for (Member member : Container.memberDao.members) {
			if (foundArticle.memberId == member.id) {
				writerName = member.loginId;
				break;
			}
		}

		System.out.printf("번호 : %d\n", foundArticle.id);
		System.out.printf("작성자 : %s\n", writerName);
		System.out.printf("날짜 : %s\n", foundArticle.regDate);
		if (foundArticle.LastModifyDate != null) {
			System.out.printf("수정된 날짜 : %s\n", foundArticle.LastModifyDate);
		}
		System.out.printf("조회수 : %d회\n", foundArticle.viewcount);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);

	}

	private void doDelete() {
		int id = getEndNum(command);
		if (id == -1) {
			System.out.println("명령어 뒤에 번호를 입력해주세요");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다");
			return;
		}

		articles.remove(foundArticle);
		
		System.out.printf("%d번 게시물이 삭제되었습니다\n", id);

	}

	private void doModify() {
		int id = getEndNum(command);
		if (id == -1) {
			System.out.println("명령어 뒤에 번호를 입력해주세요");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다");
			return;
		}

		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
		System.out.printf("%d번 게시물의 '제목'과 '내용'중 무엇을 수정하시겠습니까?\n", id);
		String command_modify = sc.nextLine().trim();

		if (command_modify.equals("제목")) {
			System.out.printf("%d번 게시물의 제목을 수정합니다\n", id);
			System.out.printf("제목 : ");
			String title = sc.nextLine();

			foundArticle.title = title;

			String LastModifyDate = Util.getNowDateTime();

			foundArticle.LastModifyDate = LastModifyDate;

			System.out.printf("%d번 게시물의 제목이 수정되었습니다\n", id);

		} else if (command_modify.equals("내용")) {
			System.out.printf("%d번 게시물의 내용을 수정합니다\n", id);
			System.out.printf("내용 : ");
			String body = sc.nextLine();
			foundArticle.body = body;

			String LastModifyDate = Util.getNowDateTime();
			foundArticle.LastModifyDate = LastModifyDate;

			System.out.printf("%d번 게시물의 내용이 수정되었습니다\n", id);
			return;
		} else {
			System.out.println("'제목' 혹은 '내용'을 입력해주세요");
		}
	}

	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.id == id) {
				return article;
			}
		}
		return null;
	}

	private static int getEndNum(String command) {
		if (command.split(" ").length == 2) {
			return -1;
		} else if (command.split(" ")[2].matches("[^0-9]+")) {
			return -1;
		}

		String[] cmdBits = command.split(" ");

		int id = Integer.parseInt(cmdBits[2]);

		return id;
	}

	public void makeTestData() {
		System.out.println("게시물 테스트 데이터를 생성합니다");
		Container.articleDao.add(new Article(Container.articleDao.getLastId(), Util.getNowDateTime(), 1, "제목1", "내용1", 11));
		Container.articleDao.add(new Article(Container.articleDao.getLastId(), Util.getNowDateTime(), 2, "제목2", "내용2", 22));
		Container.articleDao.add(new Article(Container.articleDao.getLastId(), Util.getNowDateTime(), 3, "제목3", "내용3", 33));
	}
}