package com.Inha.java.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Inha.java.BAM.Util.Util;
import com.Inha.java.BAM.dto.Member;

//@SuppressWarnings("unused")

public class MemberController extends Controller {
	private List<Member> members;
	private Scanner sc;
	public static Member loginedMember = null;
	private String command;
//	private String actionMethodName;
	int lastMemberId;

	public MemberController(Scanner sc) {
		this.members = new ArrayList<>();
		this.sc = sc;
		this.lastMemberId = 3;
	}
	
	@Override
	public void doAction(String command, String actionMethodName) {
		this.command = command;
//		this.actionMethodName = actionMethodName;
		
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
		case "delete":
			doDelete();
			break;
		case "profile":
			showProfile();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			System.out.println("도움이 필요하시면 'help'를 입력하세요");
			break;
		}
	}

	public void doJoin() {
		int id = lastMemberId + 1;

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
		if (loginedMember != null) {
			System.out.printf("현재 접속중입니다.\n다시 로그인을 원하시면 로그아웃을 해주세요\n");
			return;
		}
		while (true) {
			System.out.printf("아이디 : ");
			String loginId = sc.nextLine();

			System.out.printf("비밀번호 : ");
			String loginPw = sc.nextLine();

			Member member = getMemberByLoginId(loginId);

			if (member == null) {
				System.out.println("해당 회원은 존재하지 않습니다");
				continue;
			}

			if (member.loginPw.equals(loginPw) == false) {
				System.out.println("비밀번호를 확인해주세요");
				continue;
			}

			loginedMember = member;
			loginedMember.lastLoginDate = Util.getNowDateTime();
			System.out.println("로그인 되었습니다");
			break;
		}
		return;
	}

	public void doLogout() {
		if (loginedMember == null) {
			System.out.printf("로그인 후 사용가능합니다\n");
			return;
		}
		loginedMember = null;
		System.out.println("로그아웃 되었습니다");
	}

	private void doDelete() {
		if (loginedMember == null) {
			System.out.printf("로그인 후 사용가능합니다\n");
			return;
		}
		System.out.println("탈퇴하시겠습니까? Y / N");
		String deleteCheck = sc.nextLine().trim();
		
		if (deleteCheck.equals("Y") || deleteCheck.equals("y")) {
			members.remove(members.indexOf(loginedMember));
			loginedMember = null;
			
			System.out.println("탈퇴가 완료되었습니다");
			System.out.println("초기화면으로 돌아갑니다");
			return;
		} else if (deleteCheck.equals("N") || deleteCheck.equals("n")) {
			System.out.println("초기화면으로 돌아갑니다");
			return;
		} else {
			System.out.println("'Y'/'y' 또는 'N'/'n' 을 입력해주세요");
			return;
		}

	}
	
	public void showProfile() {
		if (loginedMember == null) {
			System.out.printf("로그인 후 사용가능합니다\n");
			return;
		}

		String searchKeyword = command.substring("member profile".length()).trim();
		
		Member foundmember = null;
		foundmember = getMemberByLoginId(searchKeyword);
		
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
			if(foundmember == null) {
				System.out.println("해당 회원은 존재하지 않습니다");
			}
		} else {
			System.out.printf("아이디 : %s\n", loginedMember.loginId);
			System.out.printf("이름 : %s\n", loginedMember.name);
			System.out.printf("전화번호 : %s\n", loginedMember.callNum);
			System.out.printf("가입날짜 : %s\n", loginedMember.regDate.substring(0, 10));
			System.out.printf("마지막 접속날짜 : %s\n", loginedMember.lastLoginDate.substring(0, 10));
		}
		return;
	}

	private Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return null;
		}

		return members.get(index);
	}
	
	private int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
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