package com.yedam.mall;

import java.util.Scanner;

public class MallMain {
	public static void main(String[] args) {
		MallMenu menu = new MallMenu();
		MallApp app = new MallApp();
		Scanner scn = new Scanner(System.in);
		int no;
		boolean runMain = true;
		UserVO user = new UserVO();

		while (runMain) {
			System.out.println("=======================================");
			System.out.println("1.로그인 2.회원가입 3.종료");
			try {
				no = Integer.parseInt(scn.nextLine());
				switch (no) {
				case 1:
					System.out.println("=======================================");
					user = app.login();
					if (user == null) {
						System.out.println("ID와 PASSWORD를 확인해 주세요");
						continue;
					}
					int userCheck = user.getType();
					if (userCheck == 1) {
						menu.adminMenu();
						break;
					} else if (userCheck == 2) {
						menu.userMenu(user);
						break;
					} else {
						System.out.println("오류발생");
						break;
					}
				case 2:
					app.register();
					break;
				case 3:
					runMain = false;
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("다시 입력해주세요.");
			}
			

		}

		System.out.println("종료합니다.");
		scn.close();
	}
}
