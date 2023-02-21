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
		case "profile":
			showProfile(command);
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			break;
		}
	}

	public MemberController(List<Member> members, Scanner sc) {
		this.members = members;
		this.sc = sc;
		this.lastMemberId = 3;
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

			for (Member member : members) {
				if (loginId.equals(member.loginId) && loginPW.equals(member.loginPW)) {
					foundMember = member;
					loginCheck = true;
					foundMember.lastLoginDate = Util.getNowDateTime();
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
		if (foundMember == null) {
			System.out.printf("로그인 후 사용가능합니다\n");
			return;
		}
		foundMember = null;
		loginCheck = false;
		System.out.println("로그아웃 되었습니다");
		return;
	}

	public void showProfile(String command) {
		if (foundMember == null) {
			System.out.printf("로그인 후 사용가능합니다\n");
			return;
		}

		String searchKeyword = command.substring("member profile".length()).trim();

		if (searchKeyword.length() > 0) {
			for (Member member : members) {
				if (searchKeyword.equals(member.loginId)) {
					System.out.printf("%s의 프로필\n", member.loginId);
					System.out.printf("아이디 : %s\n", member.loginId);
					System.out.printf("이름 : %s\n", member.name);
					System.out.printf("전화번호 : %s\n", member.callNum);
					System.out.printf("가입날짜 : %s\n", member.regDate.substring(0, 10));
					System.out.printf("마지막 접속날짜 : %s\n", member.lastLoginDate.substring(0, 10));
				}
			}
		} else {
			System.out.printf("아이디 : %s\n", foundMember.loginId);
			System.out.printf("이름 : %s\n", foundMember.name);
			System.out.printf("전화번호 : %s\n", foundMember.callNum);
			System.out.printf("가입날짜 : %s\n", foundMember.regDate.substring(0, 10));
			System.out.printf("마지막 접속날짜 : %s\n", foundMember.lastLoginDate.substring(0, 10));
		}

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

	public void makeTestData() {
		System.out.println("계정 테스트 데이터를 생성합니다");
		members.add(new Member(1, Util.getNowDateTime(), "test1", "1111", "테스트1", "01012345678"));
		members.add(new Member(2, Util.getNowDateTime(), "test2", "2222", "테스트2", "01023456789"));
		members.add(new Member(3, Util.getNowDateTime(), "test3", "3333", "테스트3", "01034567890"));
	}
}