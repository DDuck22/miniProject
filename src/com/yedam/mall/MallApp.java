package com.yedam.mall;

import java.util.List;
import java.util.Scanner;

public class MallApp {
	MallDao dao = new MallDao();
	Scanner scn = new Scanner(System.in);

	// 로그인
	public UserVO login() {
		System.out.println("ID와 PASSWORD를 입력하세요.");
		System.out.print("ID> ");
		String id = scn.nextLine();
		System.out.print("PW> ");
		String pw = scn.nextLine();

		return dao.login(id, pw);
	}

	// 상품추가
	public void addProd() {
		System.out.print("상품명> ");
		String name = scn.nextLine();
		System.out.print("상품내용> ");
		String detail = scn.nextLine();
		System.out.print("가격> ");
		int price = Integer.parseInt(scn.nextLine());
		System.out.print("재고> ");
		int stock = Integer.parseInt(scn.nextLine());

		ProdVO prod = new ProdVO();
		prod.setName(name);
		prod.setDetail(detail);
		prod.setPrice(price);
		prod.setStock(stock);

		if (dao.addProd(prod)) {
			System.out.println("상품이 등록되었습니다.");
		} else {
			System.out.println("상품등록에 실패했습니다.");
		}
	}

	// 상품변경
	public void modifyProd() {
		System.out.println("변경할 상품번호를 입력하세요.");
		System.out.print("번호> ");
		int no = Integer.parseInt(scn.nextLine());
		if (dao.manageStock(no) == -1) {
			System.out.println("상품번호를 확인하세요.");
		}
		System.out.print("상품명 변경> ");
		String name = scn.nextLine();
		System.out.print("상품내용 변경> ");
		String detail = scn.nextLine();
		System.out.print("상품가격 변경> ");
		int price = Integer.parseInt(scn.nextLine());
		
		ProdVO prod = new ProdVO();
		prod.setNo(no);
		prod.setName(name);
		prod.setDetail(detail);
		prod.setPrice(price);
		
		if(dao.modifyProd(prod)) {
			System.out.println("상품이 변경되었습니다.");
		} else {
			System.out.println("다시 입력해 주세요.");
		}
	}

	// 목록조회
	public void list() {
		List<ProdVO> list = dao.list();
		if (list.size() == 0) {
			System.out.println("조회결과 없음");
		} else {
			for (ProdVO prod : list) {
				prod.toList();
			}
		}
	}

	// 상품조회
	public void search() {
		System.out.println("1.번호로 조회 2.상품명으로 조회");
		int no = Integer.parseInt(scn.nextLine());
		try {
			if (no == 1) {
				System.out.print("조회할 번호> ");
				no = Integer.parseInt(scn.nextLine());
				dao.searchNo(no).showDetail();
			} else if (no == 2) {
				System.out.print("조회할 상품명> ");
				String str = scn.nextLine();
				dao.searchName(str).showDetail();
			} else {
				System.out.println("다시 입력해 주세요.");
			}
		} catch (NullPointerException e) {
			System.out.println("조회된 상품이 없습니다.");
		}
	}

	// 재고조회
	public void showStock() {
		List<ProdVO> list = dao.stock();
		if (list.size() == 0) {
			System.out.println("조회결과 없음");
		} else {
			for (ProdVO prod : list) {
				prod.showStock();
			}
		}
	}

	// 재고관리
	public void manageStock() {
		System.out.println("1.입고 2.출고");
		int no = Integer.parseInt(scn.nextLine());
		if (no == 1) {
			System.out.println("입고할 상품의 번호를 입력해주세요.");
			int prodNo = Integer.parseInt(scn.nextLine());
			int stock = dao.manageStock(prodNo);

			if (stock == -1) {
				System.out.println("번호를 확인해주세요.");
				return;
			}
			System.out.println("입고할 개수를 입력해주세요.");

			int addStock = Integer.parseInt(scn.nextLine());
			if (dao.addStock(prodNo, stock, addStock)) {
				System.out.printf("%d개 입고되어 %d개 남았습니다\n", addStock, (stock + addStock));
			} else {
				System.out.println("값을 확인해 주세요.");
			}
		} else if (no == 2) {
			System.out.println("출고할 상품의 번호를 입력해주세요.");
			int prodNo = Integer.parseInt(scn.nextLine());
			int stock = dao.manageStock(prodNo);

			if (stock == -1) {
				System.out.println("번호를 확인해주세요.");
				return;
			}

			System.out.println("출고할 개수를 입력해주세요.");
			int removeStock = Integer.parseInt(scn.nextLine());

			if (dao.removeStock(prodNo, stock, removeStock)) {
				System.out.printf("%d개 출고되어 %d개 남았습니다\n", removeStock, (stock - removeStock));
			} else {
				System.out.println("값을 확인해 주세요.");
			}

		} else {
			System.out.println("잘못 입력했습니다.");
		}
	}
}
