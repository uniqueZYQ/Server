package myServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class updateMyInfoServlet
 */
public class updateMyInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public updateMyInfoServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String send_date = request.getParameter("send_date");
		if(type.equals("1")) {
			String fd_form = request.getParameter("fd_form");
			String reward = request.getParameter("reward");
			String detail = request.getParameter("detail");
			
			CommonResponse res=new CommonResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
				ResultSet result;
				
				String sqlQuery = "select id from " + DBUtil.TABLE_INFO + " where id='" + id + "'";
				
				result = statement.executeQuery(sqlQuery); 
				if(result.next()){ // 已存在
					String sql = "update info set fd_form=?,reward=?,detail=?,send_date=? where id=?";
					PreparedStatement ps = connect.prepareStatement(sql);
					ps.setString(1, fd_form);
					ps.setString(2, reward);
					ps.setString(3, detail);
					ps.setString(4, send_date);
					ps.setString(5, id);
					int row1 = ps.executeUpdate();
		
					if(row1 == 1){
						res.setCode(101);
						res.setResponse("修改成功！");	
					} else {
						res.setCode(401);
						res.setResponse("操作失败，请稍后再试");
					}
				} else { // 不存在	
					res.setCode(402);
					res.setResponse("操作失败，请稍后再试") ;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				res.setCode(403);
				res.setResponse("操作失败，请稍后再试");
			}
			
			String resStr = JSONObject.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("2")){
			String help_form = request.getParameter("help_form");
			String reward = request.getParameter("reward");
			String detail = request.getParameter("detail");
			
			CommonResponse res=new CommonResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
				ResultSet result;
				
				String sqlQuery = "select id from " + DBUtil.TABLE_INFO + " where id='" + id + "'";
				
				result = statement.executeQuery(sqlQuery); 
				if(result.next()){ // 已存在
					String sql = "update info set help_form=?,reward=?,detail=?,send_date=? where id=?";
					PreparedStatement ps = connect.prepareStatement(sql);
					ps.setString(1, help_form);
					ps.setString(2, reward);
					ps.setString(3, detail);
					ps.setString(4, send_date);
					ps.setString(5, id);
					int row1 = ps.executeUpdate();
		
					if(row1 == 1){
						res.setCode(101);
						res.setResponse("修改成功！");	
					} else {
						res.setCode(401);
						res.setResponse("操作失败，请稍后再试");
					}
				} else { // 不存在	
					res.setCode(402);
					res.setResponse("操作失败，请稍后再试") ;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				res.setCode(403);
				res.setResponse("操作失败，请稍后再试");
			}
			
			String resStr = JSONObject.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("3")){
			String price = request.getParameter("price");
			String detail = request.getParameter("detail");
			
			CommonResponse res=new CommonResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
				ResultSet result;
				
				String sqlQuery = "select id from " + DBUtil.TABLE_INFO + " where id='" + id + "'";
				
				result = statement.executeQuery(sqlQuery); 
				if(result.next()){ // 已存在
					String sql = "update info set price=?,detail=?,send_date=? where id=?";
					PreparedStatement ps = connect.prepareStatement(sql);
					ps.setString(1, price);
					ps.setString(2, detail);
					ps.setString(3, send_date);
					ps.setString(4, id);
					int row1 = ps.executeUpdate();
		
					if(row1 == 1){
						res.setCode(101);
						res.setResponse("修改成功！");	
					} else {
						res.setCode(401);
						res.setResponse("操作失败，请稍后再试");
					}
				} else { // 不存在	
					res.setCode(402);
					res.setResponse("操作失败，请稍后再试") ;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				res.setCode(403);
				res.setResponse("操作失败，请稍后再试");
			}
			
			String resStr = JSONObject.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("4")){
			String reward = request.getParameter("reward");
			String place = request.getParameter("place");
			String placeId = request.getParameter("placeId");
			String date = request.getParameter("date");
			String detail = request.getParameter("detail");
			
			CommonResponse res=new CommonResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
				ResultSet result;
				
				String sqlQuery = "select id from " + DBUtil.TABLE_INFO + " where id='" + id + "'";
				
				result = statement.executeQuery(sqlQuery); 
				if(result.next()){ // 已存在
					String sql = "update info set reward=?,detail=?,send_date=?,date=?,place=?,placeId=? where id=?";
					PreparedStatement ps = connect.prepareStatement(sql);
					ps.setString(1, reward);
					ps.setString(2, detail);
					ps.setString(3, send_date);
					ps.setString(4, date);
					ps.setString(5, place);
					ps.setString(6, placeId);
					ps.setString(7, id);
					int row1 = ps.executeUpdate();
		
					if(row1 == 1){
						res.setCode(101);
						res.setResponse("修改成功！");	
					} else {
						res.setCode(401);
						res.setResponse("操作失败，请稍后再试");
					}
				} else { // 不存在	
					res.setCode(402);
					res.setResponse("操作失败，请稍后再试") ;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				res.setCode(403);
				res.setResponse("操作失败，请稍后再试");
			}
			
			String resStr = JSONObject.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("5")){
			String score = request.getParameter("score");
			String lesson = request.getParameter("lesson");
			String detail = request.getParameter("detail");
			
			CommonResponse res=new CommonResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
				ResultSet result;
				
				String sqlQuery = "select id from " + DBUtil.TABLE_INFO + " where id='" + id + "'";
				
				result = statement.executeQuery(sqlQuery); 
				if(result.next()){ // 已存在
					String sql = "update info set score=?,detail=?,send_date=?,lesson=? where id=?";
					PreparedStatement ps = connect.prepareStatement(sql);
					ps.setString(1, score);
					ps.setString(2, detail);
					ps.setString(3, send_date);
					ps.setString(4, lesson);
					ps.setString(5, id);
					int row1 = ps.executeUpdate();
		
					if(row1 == 1){
						res.setCode(101);
						res.setResponse("修改成功！");	
					} else {
						res.setCode(401);
						res.setResponse("操作失败，请稍后再试");
					}
				} else { // 不存在	
					res.setCode(402);
					res.setResponse("操作失败，请稍后再试") ;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				res.setCode(403);
				res.setResponse("操作失败，请稍后再试");
			}
			
			String resStr = JSONObject.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
	}

}
