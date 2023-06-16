package com.yedam.mall;

import java.util.Scanner;

public class MallMain {
	public static void main(String[] args) {
		MallApp app = new MallApp();
		Scanner scn = new Scanner(System.in);
		int menu;
		boolean runUser = false;
		boolean runAdmin = false;
		UserVO user = new UserVO();

		while (true) {
			System.out.println("=======================================");
			user = app.login();
			if (user == null) {
				System.out.println("ID와 PASSWORD를 확인해 주세요");
				continue;
			}
			int userCheck = user.getType();
			if (userCheck == 1) {
				runAdmin = true;
				break;
			} else if (userCheck == 2) {
				runUser = true;
				break;
			}
		}

		while (runAdmin) {
			System.out.println("=======================================");
			System.out.println("1.등록 2.상품변경 3.상품삭제 4.상품/재고조회 5.재고관리 6.종료");
			try {
				menu = Integer.parseInt(scn.nextLine());
				switch (menu) {
				case 1:
					app.addProd();
					break;
				case 2:
					app.modifyProd();
					break;
				case 3:
					app.removeProd();
					break;
				case 4:
					app.showStock();
					break;
				case 5:
					app.manageStock();
					break;
				case 6:
					runAdmin = false;
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("다시 입력해 주세요");
			}
		}

		while (runUser) {
			System.out.println("=======================================");
			System.out.println("1.목록 2.상품조회 3.구매내역 4.종료");
			try {
				menu = Integer.parseInt(scn.nextLine());
				switch (menu) {
				case 1:
					app.list();
					break;
				case 2:
					ProdVO prod = app.search();
					app.buyMenu(prod, user);
					break;
				case 3:
					app.buyHistory(user);
					break;
				case 4:
					runUser = false;
					break;
				default:
					System.out.println("다시 입력해 주세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("다시 입력해 주세요.");
			}
		}

		System.out.println("종료합니다.");
		scn.close();
	}
}
