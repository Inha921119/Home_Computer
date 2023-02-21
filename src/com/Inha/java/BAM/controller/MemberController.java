package com.Inha.java.BAM.controller;

import java.util.List;
import java.util.Scanner;

import com.Inha.java.BAM.Util.Util;
import com.Inha.java.BAM.dto.Member;

public class MemberController extends Controller {
	List<Member> members;
	Scanner sc;
	int lastMemberId;
	public static Member foundMember = null;
	public static boolean loginCheck = false;

	public void doAction(String command, String actionMethodName) {
		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		}
	}

	public MemberController(List<Member> members, Scanner sc) {
		this.members = members;
		this.sc = sc;
		this.lastMemberId = 0;
	}

	public void doJoin() {
		int id = lastMemberId + 1;
		lastMemberId = id;

		String loginId = null;
		while (true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginIdDupChk(loginId) == false) {
				System.out.printf("%s은(는) 이미 사용중인 아이디입니다\n", loginId);
				continue;
			}

			if (loginId.equals("")) {
				System.out.println("필수 정보입니다.");
				continue;
			}
			System.out.printf("%s은(는) 사용가능한 아이디입니다\n", loginId);
			break;
		}

		String loginPw = null;
		String loginPwChk = null;
		while (true) {
			System.out.printf("로그인 비밀번호 : ");
			loginPw = sc.nextLine().trim();
			if (loginPw.equals("")) {
				System.out.println("필수 정보입니다.");
				continue;
			}
			System.out.printf("로그인 비밀번호 확인: ");
			loginPwChk = sc.nextLine().trim();

			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호를 다시 입력해주세요");
				continue;
			}
			break;
		}

		String name = null;
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			if (name.equals("")) {
				System.out.println("필수 정보입니다.");
				continue;
			}
			break;
		}

		String callNum;
		while (true) {
			System.out.printf("전화번호 : ");
			callNum = sc.nextLine().trim();
			if (CallNumDupChk(callNum) == false) {
				System.out.println("중복된 전화번호입니다. 다시 입력해주세요");
				continue;
			}
			break;
		}

		String regDate = Util.getNowDateTime();

		Member member = new Member(id, regDate, loginId, loginPw, name, callNum);
		members.add(member);

		System.out.printf("%s님 회원가입이 완료되었습니다.\n", loginId);
		lastMemberId++;
	}
	
	public void doLogin() {
		if (loginCheck == false) {
			System.out.printf("아이디 : ");
			String loginId = sc.nextLine();

			System.out.printf("비밀번호 : ");
			String loginPW = sc.nextLine();

			for (int i = 0; i < members.size(); i++) {
				Member member = members.get(i);
				if (loginId.equals(member.loginId) && loginPW.equals(member.loginPW)) {
					foundMember = member;
					loginCheck = true;
					System.out.println("로그인 되었습니다");
					break;
				}
			}

			if (foundMember == null) {
				System.out.printf("로그인에 실패하였습니다.\n아이디나 비밀번호를 확인해주세요\n");
				return;
			}
			return;
		} else {
			System.out.printf("현재 접속중입니다.\n다시 로그인을 원하시면 로그아웃을 해주세요\n");
			return;
		}
	}
	
	public void doLogout() {
		foundMember = null;
		loginCheck = false;
		System.out.println("로그아웃 되었습니다");
		return;
	}

	private boolean loginIdDupChk(String loginId) {
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return false;
			}
		}
		return true;
	}

	private boolean CallNumDupChk(String CallNum) {
		for (Member member : members) {
			if (member.callNum.equals(CallNum)) {
				return false;
			}
		}
		return true;
	}
}