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
				res.setResponse("�޸ĳɹ���");	
				
			} catch (SQLException e) {
			e.printStackTrace();
			res.setCode(401);
			res.setResponse("����ʧ�ܣ����Ժ�����");
		}
		
		String resStr = JSONObject.fromObject(res).toString();
		 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // ���Զ��ַ������м��ܲ�������Ӧ�ĵ��˿ͻ��˾���Ҫ����
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
				if(result.next()){ // ƥ��ɹ�
					do {
						LastResponse e=new LastResponse();
						res.add(e);
						res.get(i).setCode(101);
						res.get(i).setId(result.getInt("id"));
						res.get(i).setLast_id(result.getInt("last_id"));
						res.get(i).setOppo_id(result.getInt("oppo_id"));
						res.get(i).setUser_id(result.getInt("user_id"));
						res.get(i).setResponse("��ȡ�ɹ�");
						i++;
					}
					while(result.next());
				} else { // ������
					LastResponse e=new LastResponse();
					res.add(e);
					res.get(0).setCode(102);
					res.get(0).setResponse("δ��ʼ��");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				LastResponse i=new LastResponse();
				res.add(i);
				res.get(0).setCode(401);
				res.get(0).setResponse("��������ʧ�ܣ�������������");
			}
			 String resStr = JSONArray.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // ���Զ��ַ������м��ܲ�������Ӧ�ĵ��˿ͻ��˾���Ҫ����
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
						res.setResponse("�����ɹ�");
				}
				else {
					res.setCode(401);
					res.setResponse("����ʧ�ܣ����Ժ�����");
				}
			} catch (SQLException e) {
			e.printStackTrace();
			res.setCode(402);
			res.setResponse("����ʧ�ܣ����Ժ�����");
		}
		
		String resStr = JSONObject.fromObject(res).toString();
		 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // ���Զ��ַ������м��ܲ�������Ӧ�ĵ��˿ͻ��˾���Ҫ����
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
				if(result.next()){ // ƥ��ɹ�
					do {
						LastResponse e=new LastResponse();
						res.add(e);
						res.get(i).setCode(101);
						res.get(i).setId(result.getInt("id"));
						res.get(i).setLast_id(result.getInt("last_id"));
						res.get(i).setOppo_id(result.getInt("oppo_id"));
						res.get(i).setUser_id(result.getInt("user_id"));
						res.get(i).setResponse("��ȡ�ɹ�");
						i++;
					}
					while(result.next());
				} else { // ������
					LastResponse e=new LastResponse();
					res.add(e);
					res.get(0).setCode(102);
					res.get(0).setResponse("δ��ʼ��");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				LastResponse i=new LastResponse();
				res.add(i);
				res.get(0).setCode(401);
				res.get(0).setResponse("��������ʧ�ܣ�������������");
			}
			 String resStr = JSONArray.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // ���Զ��ַ������м��ܲ�������Ӧ�ĵ��˿ͻ��˾���Ҫ����
		        response.getWriter().append(resStr).flush();
		}
	}

}
