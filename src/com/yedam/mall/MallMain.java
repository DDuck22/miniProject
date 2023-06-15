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
			int userCheck = app.login().getUser_type();
			if (userCheck == 1) {
				runAdmin = true;
				break;
			} else if (userCheck == 2) {
				runUser = true;
				break;
			}
			System.out.println("ID와 PASSWORD를 확인해 주세요");
		}

		while (runUser) {
			System.out.println("=======================================");
			System.out.println("1.목록  2.상품조회  3.종료");
			try {
				menu = Integer.parseInt(scn.nextLine());
				switch (menu) {
				case 1:
					app.list();
					break;
				case 2:
					app.search();
					break;
				case 3:
					runUser = false;
					break;
				default:
					System.out.println("다시 입력해 주세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("다시 입력해 주세요.");
			}
		}

		while (runAdmin) {
			System.out.println("=======================================");
			System.out.println("1.등록  2.상품변경  3.재고조회  4.재고관리  5.종료");
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
					app.showStock();
					break;
				case 4:
					app.manageStock();
					break;
				case 5:
					runAdmin = false;
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("다시 입력해 주세요");
			}
		}
		System.out.println("종료합니다.");
		scn.close();
	}
}
