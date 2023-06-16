package com.yedam.mall;

import lombok.Data;

@Data
public class ProdVO {
	private int no;
	private String name;
	private String detail;
	private int price;
	private int stock;
	private int cnt;
	private String date;
	private int buyNo;

	public void toList() {
		System.out.println("상품번호: " + this.no //
				+ "   상품명: " + this.name //
				+ "   가격: " + this.price //
				+ "   조회수:" + this.cnt);
	}

	public void showDetail() {
		System.out.println("상품번호: " + this.no //
				+ "   상품명: " + this.name //
				+ "   가격: " + this.price //
				+ "   조회수:" + this.cnt);
		System.out.println("제품내용 : " + this.detail);
	}

	public void showStock() {
		System.out.println("상품번호: " + this.no //
				+ "   상품명: " + this.name //
				+ "   가격: " + this.price //
				+ "   재고: " + this.stock //
		);
	}

	public void showHistory() {
		System.out.println("---------------------------------------");
		System.out.println(//
				"상품번호: " + this.no //
				+ "   상품명: " + this.name //
				+ "   구매일자: " + this.date//
				+ "\n구매번호: " + this.buyNo//
				+ "   구매개수: " + this.cnt+"개"//
				+ "   총 가격: " + this.price//
				);
	}
}
