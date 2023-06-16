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

	// 관리자메뉴
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
		dao.searchNo(no).toList();
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

		if (dao.modifyProd(prod)) {
			System.out.println("상품이 변경되었습니다.");
		} else {
			System.out.println("다시 입력해 주세요.");
		}
	}

	// 상품삭제
	public void removeProd() {
		System.out.println("제거할 상품번호 입력.");
		System.out.print("번호> ");
		int no = Integer.parseInt(scn.nextLine());
		if (dao.removeProd(no)) {
			System.out.println("해당 상품이 삭제되었습니다.");
		} else {
			System.out.println("해당 상품이 없습니다.");
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

	// 유저메뉴
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
	public ProdVO search() {
		System.out.println("1.번호로 조회 2.상품명으로 조회");
		int no = Integer.parseInt(scn.nextLine());
		try {
			if (no == 1) {
				System.out.print("조회할 번호> ");
				int prodNo = Integer.parseInt(scn.nextLine());
				ProdVO prod = dao.searchNo(prodNo);

				return prod;
			} else if (no == 2) {
				System.out.print("조회할 상품명> ");
				String prodName = scn.nextLine();
				ProdVO prod = dao.searchName(prodName);

				return prod;
			} else {
				System.out.println("다시 입력해 주세요.");
			}
		} catch (NullPointerException e) {
			System.out.println("조회된 상품이 없습니다.");
		}
		return null;
	}

	// 구매메뉴
	public void buyMenu(ProdVO prod, UserVO user) {
		boolean run = true;
		if (prod != null) {
			prod.showDetail();
			while (run) {
				System.out.println("=======================================");
				System.out.println("1.구매하기 2.상품보기 3.리뷰보기 4.돌아가기");
				System.out.print("> ");
				int menu = Integer.parseInt(scn.nextLine());

				switch (menu) {
				case 1:
					buy(user, prod);
					run = false;
					break;
				case 2:
					prod.showDetail();
					break;
				case 3:
					showReview(prod);
					break;
				case 4:
					run = false;
					break;
				}
			}
		}
	}

	// 상품구매
	public void buy(UserVO user, ProdVO prod) {
		System.out.println("구매하려는 개수를 입력해 주세요");
		System.out.print("> ");
		int cnt = Integer.parseInt(scn.nextLine());
		System.out.printf("%s %d개 구매시 %d원입니다.\n", prod.getName(), cnt, (cnt * prod.getPrice()));
		System.out.println("1.구매하기 2.돌아가기");
		System.out.print("> ");
		int no = scn.nextInt();
		scn.nextLine();
		if (no == 1) {
			if (dao.buy(user, prod, cnt)) {
				System.out.println("구매해 주셔서 감사합니다.");
			}
		}
	}

	// 리뷰조회
	public void showReview(ProdVO prod) {
		List<UserVO> list = dao.review(prod);
		if (list.size() == 0) {
			System.out.println("리뷰 없음.");
		} else {
			for (UserVO user : list) {
				user.showReview();
			}
		}

	}

	// 구매내역
	public void buyHistory(UserVO user) {
		List<ProdVO> list = dao.buyHistory(user);
		if (list.size() == 0) {
			System.out.println("구매내역 없음.");
		} else {
			for (ProdVO prod : list) {
				prod.showHistory();
			}
			System.out.println("=======================================");
			System.out.println("1.리뷰작성 2.리뷰조회 및 삭제 3.돌아가기");
			System.out.print("> ");
			int no = Integer.parseInt(scn.nextLine());
			switch (no) {
			case 1:
				writeReview(user);
				break;
			case 2:
				removeReview(user);
				break;
			}
		}
	}

	// 리뷰작성
	private void writeReview(UserVO user) {
		System.out.println("리뷰를 작성할 구매번호를 입력해주세요.");
		int no = Integer.parseInt(scn.nextLine());
		System.out.print("> ");
		String str = scn.nextLine();
		if (dao.writeReview(user, str, no)) {
			System.out.println("리뷰 작성이 완료되었습니다.");
		} else {
			System.out.println("해당 구매내역이 없습니다.");
		}
	}

	// 리뷰삭제
	private void removeReview(UserVO user) {
		List<UserVO> list = dao.reviewList(user);
		if(list.size()==0) {
			System.out.println("작성한 리뷰가 없습니다.");
			return;
		} else {
			for(UserVO us:list) {
				us.reviewList();
			}
		}
		System.out.println("리뷰를 삭제할 구매번호를 입력해주세요.");
		System.out.print("> ");
		int no = Integer.parseInt(scn.nextLine());
		if (dao.removeReview(user, no)) {
			System.out.println("리뷰 삭제가 완료되었습니다.");
		} else {
			System.out.println("해당 리뷰가 없습니다.");
		}

	}
}
