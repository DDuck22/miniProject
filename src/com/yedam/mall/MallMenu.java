package com.yedam.mall;

import java.util.Scanner;

public class MallMenu {
	MallApp app = new MallApp();
	Scanner scn = new Scanner(System.in);
	int num;

	public void userMenu(UserVO user) {
		boolean runUser = true;
		while (runUser) {
			System.out.println("=======================================");
			System.out.println("1.목록 2.상품조회 3.구매내역 4.종료");
			try {
				num = Integer.parseInt(scn.nextLine());
				switch (num) {
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
				System.out.println("잘못 입력했습니다. 다시 입력해 주세요.");
			}
		}
	}

	public void adminMenu() {
		boolean runAdmin = true;
		while (runAdmin) {
			System.out.println("=======================================");
			System.out.println("1.등록 2.상품변경 3.상품삭제 4.재고관리 5.주문내역 6.종료");
			try {
				num = Integer.parseInt(scn.nextLine());
				switch (num) {
				case 1:
					app.addProd();
					break;
				case 2:
					app.showStock();
					app.modifyProd();
					break;
				case 3:
					app.showStock();
					app.removeProd();
					break;
				case 4:
					app.showStock();
					app.manageStock();
					break;
				case 5:
					app.orderList();
					break;
				case 6:
					runAdmin = false;
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("다시 입력해 주세요");
			}
		}
	}
}

