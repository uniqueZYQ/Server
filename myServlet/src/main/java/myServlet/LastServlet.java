package myServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class LastServlet
 */
public class LastServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LastServlet() {
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
		String type=request.getParameter("type");
		if(type.equals("1")) {
			String user_id=request.getParameter("user_id");
			String oppo_id=request.getParameter("oppo_id");
			String last_id=request.getParameter("last_id");
			
			CommonResponse res=new CommonResponse();
	        try {
				Connection connect = DBUtil.getConnect();
				
				String sql = "update last set last_id=? where user_id=? and oppo_id=? and last_id<=?";
				PreparedStatement ps = connect.prepareStatement(sql);
				ps.setString(1, last_id);
				ps.setString(2, user_id);
				ps.setString(3, oppo_id);
				ps.setString(4, last_id);

				ps.executeUpdate();
	
				res.setCode(101);
				res.setResponse("修改成功！");	
				
			} catch (SQLException e) {
			e.printStackTrace();
			res.setCode(401);
			res.setResponse("操作失败，请稍后再试");
		}
		
		String resStr = JSONObject.fromObject(res).toString();
		 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
	        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("2")) {
			String user_id=request.getParameter("user_id");
			String oppo_id=request.getParameter("oppo_id");
			String sql="select * from last where user_id='"+user_id+"' and oppo_id='"+oppo_id+"' order by last_id desc";
			
			List<LastResponse> res=new ArrayList<LastResponse>();
	        try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				int i=0;
				result = statement.executeQuery(sql);
				if(result.next()){ // 匹配成功
					do {
						LastResponse e=new LastResponse();
						res.add(e);
						res.get(i).setCode(101);
						res.get(i).setId(result.getInt("id"));
						res.get(i).setLast_id(result.getInt("last_id"));
						res.get(i).setOppo_id(result.getInt("oppo_id"));
						res.get(i).setUser_id(result.getInt("user_id"));
						res.get(i).setResponse("获取成功");
						i++;
					}
					while(result.next());
				} else { // 不存在
					LastResponse e=new LastResponse();
					res.add(e);
					res.get(0).setCode(102);
					res.get(0).setResponse("未初始化");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				LastResponse i=new LastResponse();
				res.add(i);
				res.get(0).setCode(401);
				res.get(0).setResponse("数据请求失败，请检查网络连接");
			}
			 String resStr = JSONArray.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("3")) {
			String user_id=request.getParameter("user_id");
			String oppo_id=request.getParameter("oppo_id");
			String last_id=request.getParameter("last_id");
			
			CommonResponse res=new CommonResponse();
	        try {
				Connection connect = DBUtil.getConnect();
				
				String sql = "INSERT INTO last (user_id,oppo_id,last_id) VALUES (?,?,?)";
				PreparedStatement ps = connect.prepareStatement(sql);
				ps.setString(1, user_id);
				ps.setString(2, oppo_id);
				ps.setString(3, last_id);
				
				int row1 = ps.executeUpdate();
		
				if(row1 == 1){
					res.setCode(101);
						res.setResponse("操作成功");
				}
				else {
					res.setCode(401);
					res.setResponse("操作失败，请稍后再试");
				}
			} catch (SQLException e) {
			e.printStackTrace();
			res.setCode(402);
			res.setResponse("操作失败，请稍后再试");
		}
		
		String resStr = JSONObject.fromObject(res).toString();
		 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
	        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("4")) {
			String user_id=request.getParameter("user_id");
			String sql="select * from last where user_id='"+user_id+"' order by last_id desc";
			
			List<LastResponse> res=new ArrayList<LastResponse>();
	        try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				int i=0;
				result = statement.executeQuery(sql);
				if(result.next()){ // 匹配成功
					do {
						LastResponse e=new LastResponse();
						res.add(e);
						res.get(i).setCode(101);
						res.get(i).setId(result.getInt("id"));
						res.get(i).setLast_id(result.getInt("last_id"));
						res.get(i).setOppo_id(result.getInt("oppo_id"));
						res.get(i).setUser_id(result.getInt("user_id"));
						res.get(i).setResponse("获取成功");
						i++;
					}
					while(result.next());
				} else { // 不存在
					LastResponse e=new LastResponse();
					res.add(e);
					res.get(0).setCode(102);
					res.get(0).setResponse("未初始化");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				LastResponse i=new LastResponse();
				res.add(i);
				res.get(0).setCode(401);
				res.get(0).setResponse("数据请求失败，请检查网络连接");
			}
			 String resStr = JSONArray.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
	}

}
