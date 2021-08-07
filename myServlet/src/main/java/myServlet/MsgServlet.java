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

/**
 * Servlet implementation class MsgServlet
 */
public class MsgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MsgServlet() {
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
			String sub_id=request.getParameter("sub_id");
			String obj_id=request.getParameter("obj_id");
			String sql = "select * from msg where sub_id='"+sub_id+"' and obj_id='"+obj_id+"' or sub_id='"+obj_id+"' and obj_id='"+sub_id+"'";  
	        List<MsgResponse> res=new ArrayList<MsgResponse>();
	        try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				int i=0;
				result = statement.executeQuery(sql);
				if(result.next()){ // 匹配成功
					do {
						MsgResponse e=new MsgResponse();
						res.add(e);
						res.get(i).setCode(101);
						res.get(i).setContent(result.getString("content"));
						res.get(i).setId(result.getInt("id"));
						res.get(i).setObj_id(result.getInt("obj_id"));
						res.get(i).setPicture(result.getInt("picture"));
						res.get(i).setRecalled(result.getInt("recalled"));
						res.get(i).setResponse("获取成功");
						res.get(i).setSub_id(result.getInt("sub_id"));
						res.get(i).setTime(result.getString("time"));
						i++;
					}
					while(result.next());
				} else { // 不存在
					MsgResponse e=new MsgResponse();
					res.add(e);
					res.get(0).setCode(102);
					res.get(0).setResponse("暂无信息");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				MsgResponse i=new MsgResponse();
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
