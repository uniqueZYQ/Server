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
 * Servlet implementation class UploadPictureServlet
 */
public class UploadPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UploadPictureServlet() {
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
		String picture = request.getParameter("picture");
		getServletContext().log(picture);
		
		CommonResponse res=new CommonResponse();
		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
			ResultSet result;
			
			String sqlQuery = "select id from " + DBUtil.TABLE_PICTURE + " where picture='" + picture + "'";
			
			result = statement.executeQuery(sqlQuery); 
			if(result.next()){ // 已存在
				res.setCode(101);
				res.setResponse("上传成功!") ;
				res.setId(result.getInt("id"));
			} else { // 不存在	
				String sql = "INSERT INTO " + DBUtil.TABLE_PICTURE + " (picture) VALUES (?)";
				PreparedStatement ps = connect.prepareStatement(sql);
				ps.setString(1, picture);
				int row1 = ps.executeUpdate();
	
				if(row1 == 1){
					String sqlQueryId = "select id from " + DBUtil.TABLE_PICTURE + " where picture='" + picture + "'";
					ResultSet result2 = statement.executeQuery(sqlQueryId); // 查询新增记录的id
					if(result2.next()){
						res.setId(result2.getInt("id"));
						res.setCode(101);
						res.setResponse("上传成功!");
					}
					else {
						res.setId(0);
						res.setCode(401);
						res.setResponse("操作失败，请稍后再试");
					}	
				} else {
					res.setId(0);
					res.setCode(402);
					res.setResponse("操作失败，请稍后再试");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			res.setId(0);
			res.setCode(403);
			res.setResponse("操作失败，请稍后再试");
		}
		
		String resStr = JSONObject.fromObject(res).toString();
		 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
	        response.getWriter().append(resStr).flush();

	}
}
