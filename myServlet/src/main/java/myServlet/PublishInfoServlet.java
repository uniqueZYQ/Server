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
 * Servlet implementation class PublishInfoServlet
 */
public class PublishInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PublishInfoServlet() {
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
		String owner_id = request.getParameter("owner_id"); 
		String send_date = request.getParameter("send_date"); 
		String answered = request.getParameter("answered"); 
		String form = request.getParameter("form"); 
		String fd_form = request.getParameter("fd_form"); 
		String help_form = request.getParameter("help_form"); 
		String price = request.getParameter("price"); 
		String date = request.getParameter("date"); 
		String place = request.getParameter("place"); 
		String lesson = request.getParameter("lesson"); 
		String score = request.getParameter("score"); 
		String detail = request.getParameter("detail"); 
		String reward = request.getParameter("reward"); 
		String picture1 = request.getParameter("picture1"); 
		String picture2 = request.getParameter("picture2"); 
		String picture3 = request.getParameter("picture3"); 
		String picture4 = request.getParameter("picture4"); 
		String placeId = request.getParameter("placeId"); 
		
		CommonResponse res=new CommonResponse();
		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
			
			String sql = "INSERT INTO " + DBUtil.TABLE_INFO + " (owner_id,send_date,answered,form,fd_form,help_form,price,date,place,lesson,"
					+ "score,detail,reward,picture1,picture2,picture3,picture4,placeId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setString(1, owner_id);
			ps.setString(2, send_date);
			ps.setString(3, answered);
			ps.setString(4, form);
			ps.setString(5, fd_form);
			ps.setString(6, help_form);
			ps.setString(7, price);
			ps.setString(8, date);
			ps.setString(9, place);
			ps.setString(10, lesson);
			ps.setString(11, score);
			ps.setString(12, detail);
			ps.setString(13, reward);
			ps.setString(14, picture1);
			ps.setString(15, picture2);
			ps.setString(16, picture3);
			ps.setString(17, picture4);
			ps.setString(18, placeId);
			
			int row1 = ps.executeUpdate();
	
			if(row1 == 1){
				String sqlQueryId = "select id from " + DBUtil.TABLE_INFO + " where owner_id='" + owner_id + "' and send_date='"+send_date+"' and detail='"+detail+"'";
				ResultSet result2 = statement.executeQuery(sqlQueryId); // 查询新增记录的id
				if(result2.next()){
					res.setId(result2.getInt("id"));
					res.setCode(101);
					res.setResponse("注册成功");
				}
				else {
					res.setId(0);
					res.setCode(401);
					res.setResponse("操作失败，请稍后再试");
				}	
			} else {
				res.setId(0);
				res.setCode(403);
				res.setResponse("操作失败，请稍后再试");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			res.setId(0);
			res.setCode(404);
			res.setResponse("操作失败，请稍后再试");
		}
		
		String resStr = JSONObject.fromObject(res).toString();
		 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
	        response.getWriter().append(resStr).flush();

	}
}
