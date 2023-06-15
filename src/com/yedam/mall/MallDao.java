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
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

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

	public boolean modifyProd(ProdVO prod) {
		sql = "UPDATE mall_product " //
				+ "set	 prod_name = ?, " //
				+ "		 prod_detail = ?, " //
				+ " 	 prod_price = ? " //
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

	public List<ProdVO> stock() {
		List<ProdVO> list = new ArrayList<>();
		sql = "select * from mall_product order by prod_stock";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				ProdVO prod = new ProdVO();
				prod.setNo(rs.getInt("prod_no"));
				prod.setName(rs.getString("prod_name"));
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
}
