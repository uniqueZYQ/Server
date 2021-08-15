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
 * Servlet implementation class QueryUserServlet
 */
public class QueryUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public QueryUserServlet() {
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
		String need_picture = request.getParameter("need_picture");
		
		if(need_picture.equals("2")) {
			CommonResponse res=new CommonResponse();
			String picture_version=request.getParameter("picture_version");
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��
				ResultSet result;
			
				String sqlQuery = "select * from " + DBUtil.TABLE_USER + " where id='" + id + "'";
			
				result = statement.executeQuery(sqlQuery);
				if(result.next()){ // �Ѵ���
					res.setId(result.getInt("id"));
					//res.setPicture(result.getBytes("picture"));
					res.setNickname(result.getString("nickname"));
					//res.setRealPicture(FileBuf);
					res.setRealname(result.getString("realname"));
					res.setStdid(result.getString("stdid"));
					res.setPicture_version(result.getInt("picture_version"));
					if(result.getInt("picture_version")==Integer.valueOf(picture_version)) {
						res.setCode(102);
						res.setResponse("�������");
					}else {
						res.setCode(101);
						res.setResponse("��ȡ�ɹ�");
						res.setPicture(result.getString("picture"));
					}
				} else { // ������	
					res.setCode(401);
					res.setResponse("�û���Ϣ��ȡʧ�ܣ����Ժ�����");
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
		else{
			CommonResponse res=new CommonResponse();
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��
				ResultSet result;
			
				String sqlQuery = "select * from " + DBUtil.TABLE_USER + " where id='" + id + "'";
			
				// ��ѯ���������һ��ResultSet���ϣ�û�в鵽���ʱResultSet�ĳ���Ϊ0
				result = statement.executeQuery(sqlQuery); // �Ȳ�ѯͬ�����˺ţ������ֻ��ţ��Ƿ����
				if(result.next()){ // �Ѵ���
					res.setCode(101);
					res.setResponse("�û���Ϣ��ȡ�ɹ�");
					res.setId(result.getInt("id"));
					//res.setPicture(result.getBytes("picture"));
					res.setNickname(result.getString("nickname"));
					//res.setRealPicture(FileBuf);
					res.setRealname(result.getString("realname"));
					res.setStdid(result.getString("stdid"));
					res.setPicture_version(result.getInt("picture_version"));
				
					if(need_picture.equals("1"))
						res.setPicture(result.getString("picture"));
				} else { // ������	
					res.setCode(401);
					res.setResponse("�û���Ϣ��ȡʧ�ܣ����Ժ�����");
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
	}

}
