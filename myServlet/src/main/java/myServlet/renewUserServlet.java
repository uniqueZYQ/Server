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
 * Servlet implementation class renewUserServlet
 */
public class renewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public renewUserServlet() {
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
		String pwd = request.getParameter("pwd"); 
		String nickname = request.getParameter("nickname"); 
        
        String sql = "select * from " + DBUtil.TABLE_USER + " where id= '" + id + "'";
        getServletContext().log(sql);
       
        CommonResponse res=new CommonResponse();
        try {
			Connection connect = DBUtil.getConnect();
			Statement statement = (Statement) connect.createStatement(); 
			ResultSet result;
			
			result = statement.executeQuery(sql);
			if(result.next()){ // 匹配成功
				String new_sql="update "+DBUtil.TABLE_USER+" set pwd='"+pwd+"' , nickname='"+nickname+"' where id='"+id+"'";
			
				int row1=statement.executeUpdate(new_sql);
				if(row1==1) {
					String sqlQueryId = "select id from " + DBUtil.TABLE_USER + " where id='" + id + "' and pwd='"+pwd+"' and nickname='"+nickname+"'";
					ResultSet result2 = statement.executeQuery(sqlQueryId); // 查询新增记录的id
					if(result2.next()){
						res.setCode(101);
						res.setResponse("修改成功，请重新登录");
					}
					else {
						res.setId(0);
						res.setCode(401);
						res.setResponse("操作失败，请稍后再试");
					}	
				}
				
			} else { // 不存在
				res.setCode(402);
				res.setResponse("用户状态异常，修改失败，请稍后再试");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			res.setCode(403);
			res.setResponse("服务器连接失败，请稍后再试");
		}
		 String resStr = JSONObject.fromObject(res).toString();
		 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
	        response.getWriter().append(resStr).flush();
	}

}
