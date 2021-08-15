package myServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class renewUserPictureServlet
 */
public class renewUserPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public renewUserPictureServlet() {
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
		String id = request.getParameter("id");
		String picture = request.getParameter("picture");
		
		CommonResponse res=new CommonResponse();
		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
			ResultSet result;
			
			String sqlQuery = "select * from " + DBUtil.TABLE_USER + " where id='" + id + "'";
			
			result = statement.executeQuery(sqlQuery); 
			if(result.next()){ // 已存在
				int v=result.getInt("picture_version");
				String sql="update "+ DBUtil.TABLE_USER +" set picture_version='"+String.valueOf(result.getInt("picture_version")+1)+"', picture='"+picture+"' where id='"+id+"'";
				int row1=statement.executeUpdate(sql);
				if(row1==1) {
					res.setPicture_version(v+1);
					res.setCode(101);
					res.setResponse("头像修改成功");
				}
				else {
					res.setCode(401);
					res.setResponse("操作失败，请稍后再试");
				}
			} else { // 不存在	
				res.setCode(402);
				res.setResponse("用户状态异常，操作失败，请稍后再试");
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
