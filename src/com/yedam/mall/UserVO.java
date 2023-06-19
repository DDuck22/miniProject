package com.yedam.mall;

import lombok.Data;

@Data
public class UserVO {
	private String id;
	private String name;
	private String addr;
	private String phone;
	private int type;
	private String review;
	private String reviewDate;
	private int buyNo;

	public void showReview() {
		System.out.println("---------------------------------------");
		System.out.println("작성자: " + this.id + "  작성일자: " + this.reviewDate);
		System.out.println("내용: " + this.review);
	}

	public void reviewList() {
		System.out.println("---------------------------------------");
		System.out.println("구매번호: " + this.buyNo //
				+ "   구매한 상품: " + this.name //
				+ "   작성일자: " + this.reviewDate //
				+ "\n작성내용: " + this.review);//

	}

}
