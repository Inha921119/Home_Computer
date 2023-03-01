package com.Inha.java.BAM.controller;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.Inha.java.BAM.Util.Util;
import com.Inha.java.BAM.container.Container;
import com.Inha.java.BAM.dto.Member;


//@SuppressWarnings("unused")

public class MemberController extends Controller {
	private List<Member> members;
	private Scanner sc;
	private String command;

	public MemberController(Scanner sc) {
		this.members = Container.memberDao.members;
		this.sc = sc;
	}

	@Override
	public void doAction(String command, String actionMethodName) {
		this.command = command;

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
		case "pwchange":
			doPwChange();
			break;
		case "findid":
			doFindId();
			break;
		case "findpw":
			doFindPw();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			System.out.println("도움이 필요하시면 'help'를 입력하세요");
			break;
		}
	}

	private void doJoin() {
		int id = Container.memberDao.getLastId();

		String loginId = null;
		while (true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginIdDupChk(loginId) == false) {
				System.out.printf("%s은(는) 이미 사용중인 아이디입니다\n", loginId);
				continue;
			}

			if (loginId.length() == 0) {
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
			if (loginPw.length() == 0) {
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
			if (name.length() == 0) {
				System.out.println("필수 정보입니다.");
				continue;
			}
			break;
		}

		String mobileNum = null;
		while (true) {
			System.out.printf("전화번호 : ");
			mobileNum = sc.nextLine().trim();

			if (mobileNumDupChk(mobileNum) == false) {
				System.out.println("중복된 전화번호 입니다");
				continue;
			}

			if (mobileNum.length() == 0) {
				System.out.println("필수 정보입니다.");
				continue;
			}
			break;
		}

		String regDate = Util.getNowDateTime();

		Member member = new Member(id, regDate, loginId, loginPw, name, mobileNum);
		Container.memberDao.add(member);

		System.out.printf("%s님 회원가입이 완료되었습니다.\n", name);
	}

	private void doLogin() {
		if (isLogined()) {
			System.out.println("현재 접속중입니다.");
			System.out.println("다시 로그인을 원하시면 로그아웃을 해주세요.");
			return;
		}

		int pwWrongCount = 0;

		while (true) {
			String loginId;
			while (true) {
				System.out.printf("아이디 : ");
				loginId = sc.nextLine().trim();
				if (loginId.length() == 0) {
					System.out.println("아이디를 입력해주세요");
					continue;
				}
				break;
			}
			
			String loginPw;
			while (true) {
				System.out.printf("비밀번호 : ");
				loginPw = sc.nextLine().trim();
				if (loginPw.length() == 0) {
					System.out.println("비밀번호를 입력해주세요");
					continue;
				}
				break;
			}

			Member member = getMemberByLoginId(loginId);

			if (member == null) {
				System.out.println("해당 회원은 존재하지 않습니다");
				continue;
			}

			if (pwWrongCount == 4) {
				pwWrongCount++;
				System.out.printf("비밀번호 오류 횟수 : %d\n", pwWrongCount);
				System.out.println("비밀번호 5회 오류입니다");
				System.out.println("비밀번호와 보안문자를 입력해주세요");
				continue;
			}

			if (pwWrongCount >= 5) {
				int leftLimit = 48;
				int rightLimit = 122;
				int targetStringLength = 5;
				Random random = new Random();

				String otp = random.ints(leftLimit, rightLimit + 1)
						.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
						.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

				System.out.printf("보안문자 : %s\n", otp);
				System.out.printf("입력 : ");
				String otpCheck = sc.nextLine();
				if (otp.equals(otpCheck) && member.loginPw.equals(loginPw) == true) {
					loginedMember = member;
					loginedMember.lastLoginDate = Util.getNowDateTime();
					System.out.println("로그인 되었습니다");
					pwWrongCount = 0;
					break;
				}
				System.out.println("비밀번호와 보안문자를 확인해주세요");
				pwWrongCount++;
				continue;
			}

			if (member.loginPw.equals(loginPw) == false) {
				if (pwWrongCount < 5) {
					System.out.println("비밀번호를 확인해주세요");
					pwWrongCount++;
					System.out.printf("비밀번호 오류 횟수 : %d\n", pwWrongCount);
					continue;
				}
			}

			loginedMember = member;
			loginedMember.lastLoginDate = Util.getNowDateTime();
			System.out.println("로그인 되었습니다");
			break;
		}
	}

	private void doLogout() {
		loginedMember = null;
		System.out.println("로그아웃 되었습니다");
	}

	private void doDelete() {
		if (isLogined() == false) {
			System.out.println("로그인 후 이용가능합니다");
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

	private void showProfile() {
		String searchKeyword = command.substring("member profile".length()).trim();

		if (searchKeyword.length() > 0) {
			for (Member member : members) {
				if (searchKeyword.equals(member.loginId)) {
					System.out.println("==============================");
					System.out.printf("%s의 프로필\n", member.loginId);
					System.out.printf("아이디 : %s\n", member.loginId);
					System.out.printf("이름 : %s\n", member.name);
					System.out.printf("가입날짜 : %s\n", member.regDate.substring(0, 10));
					System.out.printf("마지막 접속날짜 : %s\n", member.lastLoginDate.substring(0, 10));
					System.out.println("==============================");
				}
			}
		} else {
			System.out.println("=========== 내 정보 ==========");
			System.out.printf("아이디 : %s\n", loginedMember.loginId);
			System.out.printf("이름 : %s\n", loginedMember.name);
			System.out.printf("전화번호 : %s\n", loginedMember.mobileNum);
			System.out.printf("가입날짜 : %s\n", loginedMember.regDate.substring(0, 10));
			System.out.printf("마지막 접속날짜 : %s\n", loginedMember.lastLoginDate.substring(0, 10));
			System.out.println("==============================");
		}
		return;
	}

	private void doPwChange() {
		System.out.println("비밀번호를 변경하시겠습니까? Y / N");
		String ChangeCheck = sc.nextLine().trim();

		if (ChangeCheck.equals("Y") || ChangeCheck.equals("y")) {
			while (true) {
				System.out.println("기존 비밀번호를 입력해주세요");
				String PwCheck = sc.nextLine().trim();

				if (PwCheck.equals(loginedMember.loginPw)) {
					String loginPw = null;
					String loginPwChk = null;
					while (true) {
						System.out.printf("변경할 비밀번호 : ");
						loginPw = sc.nextLine().trim();
						if (loginPw.length() == 0) {
							System.out.println("필수 정보입니다.");
							continue;
						}
						System.out.printf("변경할 비밀번호 확인: ");
						loginPwChk = sc.nextLine().trim();

						if (loginPw.equals(loginPwChk) == false) {
							System.out.println("비밀번호를 다시 입력해주세요");
							continue;
						}
						break;
					}
					loginedMember.loginPw = loginPw;

					System.out.println("비밀번호 변경이 완료되었습니다.");
					System.out.println("초기화면으로 돌아갑니다");
					return;
				}
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
		} else if (ChangeCheck.equals("N") || ChangeCheck.equals("n")) {
			System.out.println("초기화면으로 돌아갑니다");
			return;
		} else {
			System.out.println("'Y'/'y' 또는 'N'/'n' 을 입력해주세요");
			return;
		}
	}
///// @@@@@@@@@@@@@@@@@@@@@@@구현중
	private void doFindId() {
		System.out.println("아이디를 찾습니다. 이름과 전화번호(- 제외)를 입력해주세요");
		System.out.printf("이름 : ");
		String NameChk = sc.nextLine().trim();
		System.out.printf("전화번호 : ");
		String mobileNumChk = sc.nextLine().trim();

	}

	private void doFindPw() {
		System.out.println("비밀번호를 찾습니다. 아이디와 이름, 전화번호를 입력해주세요");

	}
///// @@@@@@@@@@@@@@@@@@@@@@@구현중
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

	private boolean mobileNumDupChk(String mobileNum) {
		for (Member member : members) {
			if (member.mobileNum.equals(mobileNum)) {
				return false;
			}
		}
		return true;
	}

	public void makeTestData() {
		System.out.println("계정 테스트 데이터를 생성합니다");
		Container.memberDao.add(new Member(Container.memberDao.getLastId(), Util.getNowDateTime(), "test1", "1111",
				"반주희", "01012341234"));
		Container.memberDao.add(new Member(Container.memberDao.getLastId(), Util.getNowDateTime(), "test2", "2222",
				"권라떼", "01023452345"));
		Container.memberDao.add(new Member(Container.memberDao.getLastId(), Util.getNowDateTime(), "test3", "3333",
				"박다혜", "01034563456"));
	}
}