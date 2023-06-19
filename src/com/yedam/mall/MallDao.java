package com.yedam.mall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.Dao;

public class MallDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	String sql = "";

	private void close() {
		try {
			conn.setAutoCommit(false);
			conn.commit();
			if (conn != null) {
				conn.close();
			} else if (psmt != null) {
				psmt.close();
			} else if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 로그인
	public UserVO login(String id, String pw) {
		sql = "select * from mall_users where user_id = ? AND user_pw = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if (rs.next()) {
				UserVO user = new UserVO();
				user.setId(rs.getString("user_id"));
				user.setName(rs.getString("user_name"));
				user.setAddr(rs.getString("user_addr"));
				user.setPhone(rs.getString("user_phone"));
				user.setType(rs.getInt("user_type"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	// 중복확인
	public boolean idc(String id) {
		sql = "select * from mall_users where user_id = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 등록
	public boolean register(String id, String pw, String name, String phone, String addr) {
		sql = "insert into mall_users (user_id, user_pw, user_name, user_phone, user_addr, user_type) "//
				+ "values (?,?,?,?,?,2)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			psmt.setString(3, name);
			psmt.setString(4, phone);
			psmt.setString(5, addr);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 관리자 메뉴
	// 상품 추가
	public boolean addProd(ProdVO prod) {
		sql = "INSERT INTO mall_product (prod_no, prod_name, prod_detail, prod_price, prod_stock) " //
				+ " VALUES (product_seq.nextval, ?, ?, ?, ?)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, prod.getName());
			psmt.setString(2, prod.getDetail());
			psmt.setInt(3, prod.getPrice());
			psmt.setInt(4, prod.getStock());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 상품 변경
	public boolean modifyProd(ProdVO prod) {
		sql = "UPDATE mall_product " //
				+ "set	 prod_name = nvl(?,prod_name), " //
				+ "		 prod_detail = nvl(?,prod_detail), " //
				+ " 	 prod_price = nvl(?,prod_price) " //
				+ "where prod_no= ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, prod.getName());
			psmt.setString(2, prod.getDetail());
			psmt.setInt(3, prod.getPrice());
			psmt.setInt(4, prod.getNo());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 목록 조회
	// 상품 제거
	public boolean removeProd(int no) {
		sql = "delete mall_product where prod_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, no);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return false;
	}

	// 재고 조회
	public List<ProdVO> stock() {
		List<ProdVO> list = new ArrayList<>();
		sql = "select * from mall_product order by prod_no";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				ProdVO prod = new ProdVO();
				prod.setNo(rs.getInt("prod_no"));
				prod.setName(rs.getString("prod_name"));
				prod.setPrice(rs.getInt("prod_price"));
				prod.setStock(rs.getInt("prod_stock"));

				list.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	// 재고관리
	public int manageStock(int no) {
		sql = "select * from mall_product where prod_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);

			rs = psmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("prod_stock");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return -1;
	}

	// 재고 더하기
	public boolean addStock(int prodNo, int stock, int addStock) {
		sql = "update mall_product set prod_stock = ? where prod_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, (stock + addStock));
			psmt.setInt(2, prodNo);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 재고 빼기
	public boolean removeStock(int prodNo, int stock, int removeStock) {
		if (removeStock > stock) {
			System.out.println("재고가 모자랍니다.");
			return false;
		}
		sql = "update mall_product set prod_stock = ? where prod_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, (stock - removeStock));
			psmt.setInt(2, prodNo);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 주문내역
	public List<OrderVO> orderList() {
		List<OrderVO> list = new ArrayList<>();
		sql = "select * from buy_history join mall_product using (prod_no) where order_check = 0 order by buy_no";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				OrderVO order = new OrderVO();
				order.setBuyNo(rs.getInt("buy_no"));
				order.setProdNo(rs.getInt("prod_no"));
				order.setProdName(rs.getString("prod_name"));
				order.setCnt(rs.getInt("count"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setDate(rs.getString("buy_date"));
				order.setUserID(rs.getString("user_id"));
				order.setPhone(rs.getString("phone"));
				order.setAddr(rs.getString("addr"));

				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	// 발송
	public void shoot(int buyNo) {
		sql = "update buy_history set order_check = 1 where order_check = 0 AND buy_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, buyNo);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	// 유저 메뉴
	// 목록 조회
	public List<ProdVO> list() {
		List<ProdVO> list = new ArrayList<>();
		sql = "select * from mall_product order by click_cnt desc";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				ProdVO prod = new ProdVO();
				prod.setNo(rs.getInt("prod_no"));
				prod.setName(rs.getString("prod_name"));
				prod.setPrice(rs.getInt("prod_price"));
				prod.setCnt(rs.getInt("click_cnt"));

				list.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	// 이름으로 조회
	public ProdVO searchName(String str) {
		sql = "select * from mall_product where prod_name = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, str);

			rs = psmt.executeQuery();
			if (rs.next()) {
				ProdVO prod = new ProdVO();

				prod.setNo(rs.getInt("prod_no"));
				prod.setName(rs.getString("prod_name"));
				prod.setPrice(rs.getInt("prod_price"));
				prod.setDetail(rs.getString("prod_detail"));
				prod.setCnt(rs.getInt("click_cnt") + 1);

				sql = "update mall_product set click_cnt = ? where prod_name = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, prod.getCnt());
				psmt.setString(2, str);
				psmt.executeUpdate();
				return prod;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	// 번호로 조회
	public ProdVO searchNo(int no) {
		sql = "select * from mall_product where prod_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);

			rs = psmt.executeQuery();
			if (rs.next()) {
				ProdVO prod = new ProdVO();

				prod.setNo(rs.getInt("prod_no"));
				prod.setName(rs.getString("prod_name"));
				prod.setPrice(rs.getInt("prod_price"));
				prod.setDetail(rs.getString("prod_detail"));
				prod.setCnt(rs.getInt("click_cnt") + 1);

				sql = "update mall_product set click_cnt = ? where prod_no = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, prod.getCnt());
				psmt.setInt(2, no);
				psmt.executeUpdate();
				return prod;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	// 상품 구매
	public boolean buy(UserVO user, ProdVO prod, int cnt) {
		sql = "insert into buy_history (buy_no, prod_no, user_id, addr, phone, count, total_price) " //
				+ "values (buy_no_seq.nextval,?,?,?,?,?,?)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, prod.getNo());
			psmt.setString(2, user.getId());
			psmt.setString(3, user.getAddr());
			psmt.setString(4, user.getPhone());
			psmt.setInt(5, cnt);
			psmt.setInt(6, cnt * prod.getPrice());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 리뷰 보기
	public List<UserVO> review(ProdVO prod) {
		List<UserVO> review = new ArrayList<>();
		sql = "select * from buy_history where prod_no = ? AND review is not null";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, prod.getNo());
			rs = psmt.executeQuery();
			while (rs.next()) {
				UserVO user = new UserVO();
				user.setId(rs.getString("user_id"));
				user.setReview(rs.getString("review"));
				user.setReviewDate(rs.getString("review_date"));

				review.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return review;
	}

	// 구매 내역
	public List<OrderVO> buyHistory(String id) {
		List<OrderVO> history = new ArrayList<>();
		sql = "select * from buy_history join mall_product using (prod_no) where user_id = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			while (rs.next()) {
				OrderVO order = new OrderVO();
				order.setBuyNo(rs.getInt("buy_no"));
				order.setProdNo(rs.getInt("prod_no"));
				order.setProdName(rs.getString("prod_name"));
				order.setCnt(rs.getInt("count"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setDate(rs.getString("buy_date"));

				history.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return history;
	}

	public boolean rc(UserVO user, int no) {
		sql = "select * from buy_history where user_id = ? and buy_no = ?";
		conn=Dao.getConnect();
		try {
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, user.getId());
			psmt.setInt(2, no);
			int r = psmt.executeUpdate();
			if(r>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	// 리뷰 작성
	public boolean writeReview(UserVO user, String str, int no) {
		sql = "update buy_history set review = ?, review_date = sysdate "//
				+ "where user_id = ? and buy_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, str);
			psmt.setString(2, user.getId());
			psmt.setInt(3, no);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 리뷰 삭제
	public boolean removeReview(UserVO user, int no) {
		sql = "update buy_history set review = null, review_date = null " //
				+ " where user_id = ? and buy_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getId());
			psmt.setInt(2, no);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 리뷰 조회
	public List<UserVO> reviewList(UserVO user) {
		List<UserVO> list = new ArrayList<>();
		sql = "select * from buy_history join mall_product using (prod_no) where user_id = ? and review is not null";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getId());

			rs = psmt.executeQuery();
			while (rs.next()) {
				UserVO temp = new UserVO();
				temp.setBuyNo(rs.getInt("buy_no"));
				temp.setName(rs.getString("prod_name"));
				temp.setReview(rs.getString("review"));
				temp.setReviewDate(rs.getString("review_date"));

				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
