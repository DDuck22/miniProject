package com.yedam.mall;

import lombok.Data;

@Data
public class OrderVO {
	private int buyNo;
	private int prodNo;
	private String prodName;
	private int cnt;
	private int totalPrice;
	private String date;
	private String userID;
	private String phone;
	private String addr;

	public void showHistory() {
		System.out.println("---------------------------------------");
		System.out.println(//
				"상품번호: " + this.prodNo //
						+ "   상품명: " + this.prodName //
						+ "   구매일자: " + this.date//
						+ "\n구매번호: " + this.buyNo//
						+ "   구매개수: " + this.cnt + "개"//
						+ "   총 가격: " + this.totalPrice//
		);
	}

	public void showOrderList() {
		System.out.println("---------------------------------------");
		System.out.println(//
				"구매자: " + this.userID //
						+ "   연락처: " + this.phone //
						+ "   주소: " + this.addr //
						+ "\n상품번호: " + this.prodNo //
						+ "   상품명: " + this.prodName //
						+ "   구매일자: " + this.date//

						+ "\n구매번호: " + this.buyNo//
						+ "   구매개수: " + this.cnt + "개"//
						+ "   총 가격: " + this.totalPrice//
		);
	}
}
