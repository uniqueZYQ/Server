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
 * Servlet implementation class CachedPictureServlet
 */
public class CachedPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CachedPictureServlet() {
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
		String user_code = request.getParameter("user_code"); 
		
		CommonResponse res=new CommonResponse();
		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
			ResultSet result;
			
			String sqlQuery = "select * from cached_picture where user_code='" + user_code + "'";
			
			result = statement.executeQuery(sqlQuery); 
			if(result.next()){ // 已存在
				res.setCode(101);
				res.setResponse("已存在") ;
			} else { // 不存在	
				String sql = "INSERT INTO cached_picture (user_code) VALUES (?)";
				PreparedStatement ps = connect.prepareStatement(sql);
				ps.setString(1, user_code);
				int row1 = ps.executeUpdate();
	
				if(row1 == 1){
					String sqlQueryId = "select * from cached_picture where user_code='" + user_code + "'";
					ResultSet result2 = statement.executeQuery(sqlQueryId); // 查询新增记录的id
					if(result2.next()){
						res.setCode(101);
						res.setResponse("添加成功");
					}
					else {
						res.setCode(401);
						res.setResponse("操作失败，请稍后再试");
					}	
				} else {
					res.setCode(402);
					res.setResponse("操作失败，请稍后再试");
				}
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
