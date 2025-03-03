package myServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class QueryMyInfoServlet
 */
public class QueryMyInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public QueryMyInfoServlet() {
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
		
		if(type.equals("0")) {
			String id = request.getParameter("id");
			String sql = "select * from " + DBUtil.TABLE_INFO + " where owner_id= '" + id + "' order by send_date desc";
		       
	        List<InfoResponse> res=new ArrayList<InfoResponse>();
	        try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				int i=0;
				result = statement.executeQuery(sql);
				if(result.next()){ // 匹配成功
					do {
						InfoResponse e=new InfoResponse();
						res.add(e);
						res.get(i).setAnswered(result.getInt("answered"));
						res.get(i).setCode(101);
						res.get(i).setDate(result.getString("date"));
						res.get(i).setDetail(result.getString("detail"));
						res.get(i).setFd_form(result.getInt("fd_form"));
						res.get(i).setForm(result.getInt("form"));
						res.get(i).setHelp_form(result.getInt("help_form"));
						res.get(i).setId(result.getInt("id"));
						res.get(i).setLesson(result.getString("lesson"));
						res.get(i).setOwner_id(result.getInt("owner_id"));
						res.get(i).setPicture1(result.getInt("picture1"));
						res.get(i).setPicture2(result.getInt("picture2"));
						res.get(i).setPicture3(result.getInt("picture3"));
						res.get(i).setPicture4(result.getInt("picture4"));
						res.get(i).setPlace(result.getString("place"));
						res.get(i).setPlaceId(result.getString("placeId"));
						res.get(i).setPrice(result.getDouble("price"));
						res.get(i).setResponse("获取成功");
						res.get(i).setReward(result.getDouble("reward"));
						res.get(i).setScore(result.getInt("score"));
						res.get(i).setSend_date(result.getString("send_date"));
						i++;
					}
					while(result.next());
				} else { // 不存在
					InfoResponse e=new InfoResponse();
					res.add(e);
					res.get(0).setCode(102);
					res.get(0).setResponse("暂无信息");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				InfoResponse i=new InfoResponse();
				res.add(i);
				res.get(0).setCode(402);
				res.get(0).setResponse("数据请求失败，请检查网络连接");
			}
			 String resStr = JSONArray.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("1")){
			String id = request.getParameter("id");
			String sql="select * from " + DBUtil.TABLE_INFO + " where id= '" + id + "'";	  
			InfoResponse info=new InfoResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				result = statement.executeQuery(sql);
				if(result.next()){ // 匹配成功
					info.setAnswered(result.getInt("answered"));
					info.setCode(101);
					info.setDate(result.getString("date"));
					info.setDetail(result.getString("detail"));
					info.setFd_form(result.getInt("fd_form"));
					info.setForm(result.getInt("form"));
					info.setHelp_form(result.getInt("help_form"));
					info.setId(result.getInt("id"));
					info.setLesson(result.getString("lesson"));
					info.setOwner_id(result.getInt("owner_id"));
					info.setPicture1(result.getInt("picture1"));
					info.setPicture2(result.getInt("picture2"));
					info.setPicture3(result.getInt("picture3"));
					info.setPicture4(result.getInt("picture4"));
					info.setPlace(result.getString("place"));
					info.setPlaceId(result.getString("placeId"));
					info.setPrice(result.getDouble("price"));
					info.setResponse("获取成功");
					info.setReward(result.getDouble("reward"));
					info.setScore(result.getInt("score"));
					info.setSend_date(result.getString("send_date"));
				
				} else { // 不存在
					info.setCode(401);
					info.setResponse("数据状态异常，请稍后再试");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				info.setCode(402);
				info.setResponse("数据请求失败，请检查网络连接");
			}
			 String resStr = JSONObject.fromObject(info).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("2")){
			String id = request.getParameter("id");
			String sql="delete from " + DBUtil.TABLE_INFO + " where id= '" + id + "'";
			InfoResponse info=new InfoResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				statement.execute(sql);
				String new_sql="select * from " + DBUtil.TABLE_INFO + " where id= '" + id + "'";
				ResultSet result;
				result = statement.executeQuery(new_sql);
				if(result.next()){ // 不成功
					info.setCode(401);
					info.setResponse("操作异常，请稍后再试");
				} else { // 成功
					info.setCode(101);
					info.setResponse("删除成功！");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				info.setCode(402);
				info.setResponse("操作失败，请检查网络连接");
			}
			 String resStr = JSONObject.fromObject(info).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("3")){
			String kwd = request.getParameter("kwd");
			String sql = "select * from " + DBUtil.TABLE_INFO + " where detail like '%"+kwd+"%' or lesson like '%"+kwd+"%' order by send_date desc";
			getServletContext().log(sql);
	        List<InfoResponse> res=new ArrayList<InfoResponse>();
	        try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				int i=0;
				result = statement.executeQuery(sql);
				if(result.next()){ // 匹配成功
					do {
						InfoResponse e=new InfoResponse();
						res.add(e);
						res.get(i).setAnswered(result.getInt("answered"));
						res.get(i).setDate(result.getString("date"));
						res.get(i).setDetail(result.getString("detail"));
						res.get(i).setFd_form(result.getInt("fd_form"));
						res.get(i).setForm(result.getInt("form"));
						res.get(i).setHelp_form(result.getInt("help_form"));
						res.get(i).setId(result.getInt("id"));
						res.get(i).setLesson(result.getString("lesson"));
						res.get(i).setOwner_id(result.getInt("owner_id"));
						res.get(i).setPicture1(result.getInt("picture1"));
						res.get(i).setPicture2(result.getInt("picture2"));
						res.get(i).setPicture3(result.getInt("picture3"));
						res.get(i).setPicture4(result.getInt("picture4"));
						res.get(i).setPlace(result.getString("place"));
						res.get(i).setPlaceId(result.getString("placeId"));
						res.get(i).setPrice(result.getDouble("price"));
						res.get(i).setReward(result.getDouble("reward"));
						res.get(i).setScore(result.getInt("score"));
						res.get(i).setViews(result.getInt("views"));
						res.get(i).setSend_date(result.getString("send_date"));
						String owner_id=String.valueOf(result.getInt("owner_id"));
						
						String sql_1="select nickname,picture from user where id='"+owner_id+"'";
						ResultSet result1;
						Connection connect1 = DBUtil.getConnect();
						Statement statement1 = (Statement) connect1.createStatement(); 
						result1 = statement1.executeQuery(sql_1);
						if(result1.next()) {
							res.get(i).setOwner_nickname(result1.getString("nickname"));
							//res.get(i).setOwner_picture(result1.getString("picture"));
							res.get(i).setResponse("获取成功");
							res.get(i).setCode(103);
						}
						else {
							res.get(i).setResponse("用户信息获取失败");
							res.get(i).setCode(403);
						}
						i++;
					}
					while(result.next());
				} else { // 不存在
					InfoResponse e=new InfoResponse();
					res.add(e);
					res.get(0).setCode(102);
					res.get(0).setResponse("暂无信息");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				InfoResponse i=new InfoResponse();
				res.add(i);
				res.get(0).setCode(402);
				res.get(0).setResponse("数据请求失败，请检查网络连接");
			}
			 String resStr = JSONArray.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("4")) {
			String id=request.getParameter("id");
			String sql="select * from " + DBUtil.TABLE_INFO + " where id= '" + id + "'";
			CommonResponse info=new CommonResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				result=statement.executeQuery(sql);
				if(result.next()) {
					String sql_0="update info set answered='1' where id= '" + id + "'";
					Statement statement0 = (Statement) connect.createStatement(); 
					statement0.executeUpdate(sql_0);
					String sql_1="select * from info where answered='1' and id= '" + id + "'";
					Statement statement1 = (Statement) connect.createStatement(); 
					ResultSet result1=statement1.executeQuery(sql_1);
					if(result1.next()) {
						info.setCode(101);
						info.setResponse("响应成功");
					}
					else {
						info.setCode(403);
						info.setResponse("操作失败，请检查网络连接");
					}
				}
				else {
					info.setCode(401);
					info.setResponse("信息已被删除，响应失败");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				info.setCode(402);
				info.setResponse("操作失败，请检查网络连接");
			}
			 String resStr = JSONObject.fromObject(info).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("5")){
			String id = request.getParameter("id");
			String sql="select * from " + DBUtil.TABLE_INFO + " where id= '" + id + "'";	  
			InfoResponse info=new InfoResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				result = statement.executeQuery(sql);
				if(result.next()){ // 匹配成功
					info.setAnswered(result.getInt("answered"));
					info.setDate(result.getString("date"));
					info.setDetail(result.getString("detail"));
					info.setFd_form(result.getInt("fd_form"));
					info.setForm(result.getInt("form"));
					info.setHelp_form(result.getInt("help_form"));
					info.setId(result.getInt("id"));
					info.setLesson(result.getString("lesson"));
					info.setOwner_id(result.getInt("owner_id"));
					info.setPicture1(result.getInt("picture1"));
					info.setPicture2(result.getInt("picture2"));
					info.setPicture3(result.getInt("picture3"));
					info.setPicture4(result.getInt("picture4"));
					info.setPlace(result.getString("place"));
					info.setPlaceId(result.getString("placeId"));
					info.setPrice(result.getDouble("price"));
					info.setResponse("获取成功");
					info.setReward(result.getDouble("reward"));
					info.setScore(result.getInt("score"));
					info.setSend_date(result.getString("send_date"));
					int v=result.getInt("views");
					String new_sql="update info set views='"+String.valueOf(v+1)+"' where id='"+id+"'";
					Connection connect1 = DBUtil.getConnect();
					Statement statement1 = (Statement) connect1.createStatement(); 
					int n = statement1.executeUpdate(new_sql);
					if(n==1) {
						info.setCode(101);
						info.setResponse("获取成功");
					}
					else {
						info.setCode(403);
						info.setResponse("操作失败");
					}
				} else { // 不存在
					info.setCode(401);
					info.setResponse("数据状态异常，请稍后再试");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				info.setCode(402);
				info.setResponse("数据请求失败，请检查网络连接");
			}
			 String resStr = JSONObject.fromObject(info).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
	}

}
