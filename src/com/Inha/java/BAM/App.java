package com.Inha.java.BAM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.Inha.java.BAM.Util.Util;
import com.Inha.java.BAM.dto.Article;
import com.Inha.java.BAM.dto.Member;

public class App {

	private static int lastArticleId = 3;
	private static int lastMemberId = 0;
	private static List<Article> articles;
	private static List<Member> members;
	private static Member foundMember = null;

	static {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("== 프로그램 시작 ==");

		makeTestData();

		Scanner sc = new Scanner(System.in);

		boolean logincheck = false;

		while (true) {
			System.out.printf("명령어) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해 주세요.");
				continue;
			}
			if (command.equals("exit")) {
				break;
			}

			if (command.equals("member join")) {
				int id = lastMemberId + 1;
				lastMemberId = id;
				String loginId;
				while (true) {
					boolean idCheck = true;
					System.out.printf("아이디 : ");
					loginId = sc.nextLine().trim();
					
					for (int i = 0; i < members.size(); i++) {
						Member member = members.get(i);
						
						if (loginId.equals(member.loginId)) {
							System.out.println("중복된 아이디입니다. 다시 입력해주세요");
							idCheck = false;
							break;
						}
						
					}
					if (loginId.equals("")) {
						System.out.println("필수 정보입니다.");
						continue;
					}
					if (idCheck) {
						break;
					}
				}
				String loginPW;
				String PWCheck;
				while (true) {
					System.out.printf("비밀번호 : ");
					loginPW = sc.nextLine().trim();
					
					if (loginPW.equals("")) {
						System.out.println("필수 정보입니다.");
						continue;
					}
					
					System.out.printf("비밀번호 확인 : ");
					PWCheck = sc.nextLine().trim();
					
					if (loginPW.equals(PWCheck)) {
						System.out.println("비밀번호와 비밀번호 확인이 일치합니다.");
						break;
					} else {
						System.out.println("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
						continue;
					}
				}

				String name;
				while (true) {
					System.out.printf("이름 : ");
					name = sc.nextLine().trim();
					if (name.equals("")) {
						System.out.println("필수 정보입니다.");
						continue;
					}
					break;
				}

				String callnumber;
				while (true) {
					boolean callnumberCheck = true;
					System.out.printf("전화번호 : ");
					callnumber = sc.nextLine().trim();
					for (int i = 0; i < members.size(); i++) {
						Member member = members.get(i);
						if (callnumber.equals(member.callnumber)) {
							System.out.println("중복된 전화번호입니다. 다시 입력해주세요");
							callnumberCheck = false;
							break;
						}
					}
					if (callnumberCheck) {
						break;
					}
				}

				String regDate = Util.getNowDateTime();

				Member member = new Member(id, regDate, loginId, loginPW, name, callnumber);

				members.add(member);

				System.out.println("계정 생성이 완료되었습니다.");
				continue;
			}

			if (command.equals("login")) {
				if (logincheck == false) {
					System.out.printf("아이디 : ");
					String loginId = sc.nextLine();

					System.out.printf("비밀번호 : ");
					String loginPW = sc.nextLine();

					for (int i = 0; i < members.size(); i++) {
						Member member = members.get(i);
						if (loginId.equals(member.loginId) && loginPW.equals(member.loginPW)) {
							foundMember = member;
							logincheck = true;
							System.out.println("로그인 되었습니다");
							break;
						}
					}

					if (foundMember == null) {
						System.out.printf("로그인에 실패하였습니다.\n아이디나 비밀번호를 확인해주세요\n");
						continue;
					}
					continue;
				} else {
					System.out.printf("현재 접속중입니다.\n다시 로그인을 원하시면 로그아웃을 해주세요\n");
					continue;
				}
			}

			if (command.equals("logout")) {
				foundMember = null;
				logincheck = false;
				System.out.println("로그아웃 되었습니다");
				continue;
			}

			if (foundMember == null) {
				System.out.println("로그인을 해주세요");
				continue;
			}

			if (command.startsWith("article list")) {
				if (lastArticleId == 0) {
					System.out.println("게시글이 없습니다.");
					continue;
				}
				
				String searchKeyword = command.substring("article list".length()).trim();

				List<Article> printArticles = new ArrayList<>(articles);
				
				if (searchKeyword.length() > 0) {
					System.out.println("검색어 : " + searchKeyword);
					
					printArticles.clear();

					for (Article article : articles) {
						if (article.title.contains(searchKeyword)) {
							printArticles.add(article);
						}
					}
					if (printArticles.size() == 0) {
						System.out.println("검색결과가 없습니다");
						continue;
					}

				}
				
				System.out.println("|번호		 |제목		|날짜		|작성자		|조회수		");
				Collections.reverse(printArticles);

				for (Article article : printArticles) {
					System.out.printf("|%d		 |%s		|%s	|%s		|%d		\n", article.id, article.title,
							article.regDate.substring(0, 10), article.writer, article.viewcount);
				}
			} else if (command.equals("article write")) {
				int id = lastArticleId + 1;
				lastArticleId = id;
				System.out.printf("제목 : ");
				String title = sc.nextLine();

				System.out.printf("내용 : ");
				String body = sc.nextLine();

				String regDate = Util.getNowDateTime();

				Article article = new Article(id, regDate, title, body);

				article.writer = foundMember.loginId;

				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", id);
			} else if (command.startsWith("article detail")) {
				if (command.split(" ").length == 2) {
					System.out.println("detail 뒤에 번호를 입력해주세요");
					continue;
				} else if (command.split(" ")[2].matches("[^0-9]+")) {
					System.out.println("detail 뒤에 숫자만 입력해주세요");
					continue;
				}

				String cmdBits = command.split(" ")[2];
				int id = Integer.parseInt(cmdBits);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				if (foundMember.loginId != foundArticle.writer) {
					foundArticle.increseViewCount();
				}

				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("작성자 : %s\n", foundArticle.writer);
				System.out.printf("날짜 : %s\n", foundArticle.regDate);
				if (foundArticle.LastModifyDate != null) {
					System.out.printf("수정된 날짜 : %s\n", foundArticle.LastModifyDate);
				}
				System.out.printf("조회수 : %d회\n", foundArticle.viewcount);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);

			} else if (command.startsWith("article delete")) {
				if (command.split(" ").length == 2) {
					System.out.println("delete 뒤에 번호를 입력해주세요");
					continue;
				} else if (command.split(" ")[2].matches("[^0-9]+")) {
					System.out.println("delete 뒤에 숫자만 입력해주세요");
					continue;
				}

				String cmdBits = command.split(" ")[2];
				int id = Integer.parseInt(cmdBits);

				Article foundArticle = getArticleById(id);

				if (foundArticle != null) {
					if (foundArticle.writer == foundMember.loginId) {
						articles.remove(articles.indexOf(foundArticle));
						System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
					} else {
						System.out.println("삭제할 권한이 없습니다");
					}
					continue;
				} else {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}

			} else if (command.startsWith("article modify")) {
				if (command.split(" ").length == 2) {
					System.out.println("modify 뒤에 번호를 입력해주세요");
					continue;
				} else if (command.split(" ")[2].matches("[^0-9]+")) {
					System.out.println("modify 뒤에 숫자만 입력해주세요");
					continue;
				}

				String cmdBits = command.split(" ")[2];
				int id = Integer.parseInt(cmdBits);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}

				if (foundArticle.writer == foundMember.loginId) {
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
						continue;
					} else {
						System.out.println("'제목' 혹은 '내용'을 입력해주세요");
					}
				}
				if (foundArticle.writer != foundMember.loginId) {
					System.out.println("편집할 권한이 없습니다");
					continue;
				}
			} else {
				System.out.println("존재하지 않는 명령어 입니다.");
			}
		}
		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}

	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.id == id) {
				return article;
			}
		}
		return null;
	}

	private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다");
		articles.add(new Article(1, Util.getNowDateTime(), "Title1", "test1", 10));
		articles.add(new Article(2, Util.getNowDateTime(), "Title2", "test2", 20));
		articles.add(new Article(3, Util.getNowDateTime(), "Title3", "test3", 30));
	}
}