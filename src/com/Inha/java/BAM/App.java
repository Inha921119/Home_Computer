package com.Inha.java.BAM;

import java.util.Scanner;

import com.Inha.java.BAM.Util.Util;
import com.Inha.java.BAM.controller.ArticleController;
import com.Inha.java.BAM.controller.Controller;
import com.Inha.java.BAM.controller.MemberController;

public class App {
	public void run() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		
		Controller controller;

		articleController.makeTestData();
		memberController.makeTestData();

		while (true) {
			System.out.printf("명령어) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해 주세요.");
				continue;
			}
			
			if (command.equals("help")) {
				Util.CommandHelp();
				continue;
			}
			
			if (command.equals("exit")) {
				break;
			}
			
			String[] cmdBits = command.split(" ");
			String controllerName = cmdBits[0];
			
			if(cmdBits.length == 1) {
				System.out.println("명령어를 확인해주세요");
				System.out.println("도움이 필요하시면 'help'를 입력하세요");
				continue;
			}
			
			String actionMethodName = cmdBits[1];
			controller = null;
			
			if (controllerName.equals("article")) {
				if (MemberController.loginedMember == null) {
					System.out.printf("로그인 후 사용가능합니다\n");
					continue;
				}
				controller = articleController;
				
			} else if (controllerName.equals("member")) {
				
				controller = memberController;
				
			}
			else {
				System.out.println("존재하지 않는 명령어 입니다.");
				System.out.println("도움이 필요하시면 'help'를 입력하세요");
				continue;
			}
			
			controller.doAction(command, actionMethodName);
		}
		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}
}