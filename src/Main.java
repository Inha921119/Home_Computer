import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		int lastArticleId = 0;

		List<Article> articles = new ArrayList<>();

		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();

			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해 주세요.");
				continue;
			}
			if (cmd.equals("exit")) {
				break;
			}
			if (cmd.equals("article list")) {
				if (lastArticleId == 0) {
					System.out.println("게시글이 없습니다.");
					continue;
				}
				System.out.println("번호		|제목		|날짜		");
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("%d		|%s		|%s		\n", article.id, article.title, article.NowDate);
				}
			} else if (cmd.equals("article write")) {
				int id = lastArticleId + 1;
				lastArticleId = id;
				System.out.printf("제목 : ");
				String title = sc.nextLine();

				System.out.printf("내용 : ");
				String body = sc.nextLine();

				LocalDate nowdate = LocalDate.now();
				String NowDate = nowdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				LocalTime nowtime = LocalTime.now();
				String NowTime = nowtime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

				Article article = new Article(id, title, body, NowDate, NowTime);
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", id);
			} else if (cmd.startsWith("article detail")) {
				if (cmd.split(" ").length != 3) {
					System.out.println("detail 번호를 입력해주세요");
					continue;
				}

				String cmdBits = cmd.split(" ")[2];
				int id = Integer.parseInt(cmdBits);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				System.out.println("번호 : " + foundArticle.id);
				System.out.println("날짜 : " + foundArticle.NowDate + " " + foundArticle.NowTime);
				System.out.println("제목 : " + foundArticle.title);
				System.out.println("내용 : " + foundArticle.body);

			} else if (cmd.startsWith("article delete")) {
				if (cmd.split(" ").length != 3) {
					System.out.println("delete 번호를 입력해주세요");
					continue;
				}
				String cmdBits = cmd.split(" ")[2];
				int id = Integer.parseInt(cmdBits);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				articles.remove(id - 1);
				
				System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
			} else if (cmd.startsWith("article edit")) {
				if (cmd.split(" ").length != 3) {
					System.out.println("edit 번호를 입력해주세요");
					continue;
				}
				String cmdBits = cmd.split(" ")[2];
				int id = Integer.parseInt(cmdBits);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				System.out.printf("%d번 게시물의 내용을 수정합니다\n", id);
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				foundArticle.body = body;
				
				System.out.printf("%d번 게시물의 내용이 수정되었습니다\n", id);
			} else {
				System.out.println("존재하지 않는 명령어 입니다.");
			}
		}
		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}
}

class Article {
	int id;
	String title;
	String body;
	String NowDate;
	String NowTime;

	Article(int id, String title, String body, String NowDate, String NowTime) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.NowDate = NowDate;
		this.NowTime = NowTime;
	}
}