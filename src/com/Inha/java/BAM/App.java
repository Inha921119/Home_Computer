package com.Inha.java.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Inha.java.BAM.controller.ArticleController;
import com.Inha.java.BAM.controller.Controller;
import com.Inha.java.BAM.controller.MemberController;
import com.Inha.java.BAM.dto.Article;
import com.Inha.java.BAM.dto.Member;

public class App {

	private static List<Article> articles;
	private static List<Member> members;

	static {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(members, sc);
		ArticleController articleController = new ArticleController(articles, sc);
		
		Controller controller;

		articleController.makeTestData();

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
			
			String[] cmdBits = command.split(" ");
			String controllerName = cmdBits[0];
			
			if(cmdBits.length == 1) {
				System.out.println("명령어를 확인해주세요");
				continue;
			}
			
			String actionMethodName = cmdBits[1];
			controller = null;
			
			if (controllerName.equals("article")) {
				
				controller = articleController;
				
			} else if (controllerName.equals("member")) {
				
				controller = memberController;
				
			}
			else {
				System.out.println("존재하지 않는 명령어 입니다.");
				continue;
			}
			
			controller.doAction(command, actionMethodName);
		}
		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}
}
//if(foundMember==null){System.out.println("로그인을 해주세요");continue;}
