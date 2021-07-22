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

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }
 
	/**
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickname = request.getParameter("nickname"); // 从 request 中获取名为 account 的参数的值
		String pwd = request.getParameter("pwd"); // 从 request 中获取名为 password 的参数的值
		String realname = request.getParameter("realname");
		String stdid = request.getParameter("stdid");

		CommonResponse res=new CommonResponse();
		
		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
			ResultSet result;
			
			String sqlQuery = "select * from " + DBUtil.TABLE_USER + " where stdid='" + stdid + "'";
			
			// 查询类操作返回一个ResultSet集合，没有查到结果时ResultSet的长度为0
			result = statement.executeQuery(sqlQuery); // 先查询同样的账号（比如手机号）是否存在
			if(result.next()){ // 已存在
				res.setCode(402);
				res.setResponse("该学号已被注册，请直接登录") ;
				res.setId(result.getInt("id"));
			} else { // 不存在
				String sqlInsertPass = "insert into " + DBUtil.TABLE_USER + "(stdid,nickname,pwd,realname) values('"+stdid+"','"+nickname+"','"+pwd+"','"+realname+"')";
				// 更新类操作返回一个int类型的值，代表该操作影响到的行数
				int row1 = statement.executeUpdate(sqlInsertPass); // 插入帐号密码
				if(row1 == 1){
					String sqlQueryId = "select stdid,id from " + DBUtil.TABLE_USER + " where stdid='" + stdid + "'";
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
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String resStr = JSONObject.fromObject(res).toString();
		 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
	        response.getWriter().append(resStr).flush();

	}
 
}
