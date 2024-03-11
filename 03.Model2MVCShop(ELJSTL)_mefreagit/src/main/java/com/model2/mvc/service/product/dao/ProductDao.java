package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;


public class ProductDao {
	
	public ProductDao(){
	}

	public void insertProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into PRODUCT values (seq_product_prod_no.nextval,?,?,?,?,?, sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManufactureDay());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getImageFile());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	}

	public Product findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product product = null;
		
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManufactureDay(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setImageFile(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			
		}
		
		rs.close();
		stmt.close();
		con.close();

		return	 product;
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT";
		
		if (search.getSearchCondition() != null ) {
			if (search.getSearchCondition().equals("0")) {
				sql += " where PROD_NO LIKE'%" + search.getSearchKeyword()
						+ "%'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " where PROD_NAME LIKE'%" + search.getSearchKeyword()
						+ "%'";
			}
		}
		sql += " order by PROD_NO";
		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
				int totalCount = this.getTotalCount(sql);
				System.out.println("UserDAO :: totalCount  :: " + totalCount);
				
				//==> CurrentPage 寃뚯떆臾쇰쭔 諛쏅룄濡� Query �떎�떆援ъ꽦
				sql = makeCurrentPageSql(sql, search);
				PreparedStatement pStmt = con.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();
			
				System.out.println(search);

				List<Product> list = new ArrayList<Product>();
				
				while(rs.next()){
					Product product = new Product();
					product.setProdNo(rs.getInt("PROD_NO"));
					product.setProdName(rs.getString("PROD_NAME"));
					product.setProdDetail(rs.getString("PROD_DETAIL"));
					product.setManufactureDay(rs.getString("MANUFACTURE_DAY"));
					product.setPrice(rs.getInt("PRICE"));
					product.setImageFile(rs.getString("IMAGE_FILE"));
					product.setRegDate(rs.getDate("REG_DATE"));
					list.add(product);
				}
				System.out.println("productdao list1"+list);
				//==> totalCount �젙蹂� ���옣
				map.put("totalCount", new Integer(totalCount));
				//==> currentPage �쓽 寃뚯떆臾� �젙蹂� 媛뽯뒗 List ���옣
				map.put("list", list);
				System.out.println("productdao list2"+list);
				rs.close();
				pStmt.close();
				con.close();

				return map;
			}
	
//		PreparedStatement stmt = 
//			con.prepareStatement(	sql,
//														ResultSet.TYPE_SCROLL_INSENSITIVE,
//														ResultSet.CONCUR_UPDATABLE);
//		ResultSet rs = stmt.executeQuery();
//
//		rs.last();
//		int total = rs.getRow();
//		System.out.println("濡쒖슦�쓽 �닔:" + total);
//
//		Map<String,Object> map2 = new HashMap<String,Object>();
//		map.put("count", new Integer(total));
//
//		rs.absolute(search.getCurrentPage() * search.getPageSize() - search.getPageSize()+1);
//		System.out.println("search.getCurrentPage():" + search.getCurrentPage());
//		System.out.println("search.getPageSize():" + search.getPageSize());
//
//		ArrayList<Product> list = new ArrayList<Product>();
//		if (total > 0) {
//			for (int i = 0; i < search.getPageSize(); i++) {
//				Product vo = new Product();
//					vo.setProdNo(rs.getInt("PROD_NO"));
//					vo.setProdName(rs.getString("PROD_NAME"));
//					vo.setProdDetail(rs.getString("PROD_DETAIL"));
//					vo.setManufactureDay(rs.getString("MANUFACTURE_DAY"));
//					vo.setPrice(rs.getInt("PRICE"));
//					vo.setImageFile(rs.getString("IMAGE_FILE"));
//					vo.setRegDate(rs.getDate("REG_DATE"));
//					System.out.println("list test :"+ list);
//					list.add(vo);
//					if (!rs.next())
//						break;
// 			}
//		}
//		System.out.println("list.size() : "+ list.size());
//		map.put("list", list);
//		System.out.println("map().size() : "+ map.size());
//           
//		con.close();
//			
//		return map;
//	}

	public void updateProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set PROD_NAME=?, PROD_DETAIL=?,MANUFACTURE_DAY=?, PRICE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManufactureDay());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getImageFile());
		stmt.executeUpdate();
		
		con.close();
	}
	
	// 寃뚯떆�뙋 Page 泥섎━瑜� �쐞�븳 �쟾泥� Row(totalCount)  return
		private int getTotalCount(String sql) throws Exception {
			
			sql = "SELECT COUNT(*) "+
			          "FROM ( " +sql+ ") countTable";
			
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			int totalCount = 0;
			if( rs.next() ){
				totalCount = rs.getInt(1);
			}
			
			pStmt.close();
			con.close();
			rs.close();
			
			return totalCount;
		}
		
		// 寃뚯떆�뙋 currentPage Row 留�  return 
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
			System.out.println("UserDAO :: make SQL :: "+ sql);	
			
			return sql;
		}
	}
