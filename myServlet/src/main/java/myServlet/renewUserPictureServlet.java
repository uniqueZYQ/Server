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
			Statement statement = (Statement) connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��
			ResultSet result;
			
			String sqlQuery = "select * from " + DBUtil.TABLE_USER + " where id='" + id + "'";
			
			result = statement.executeQuery(sqlQuery); 
			if(result.next()){ // �Ѵ���
				int v=result.getInt("picture_version");
				String sql="update "+ DBUtil.TABLE_USER +" set picture_version='"+String.valueOf(result.getInt("picture_version")+1)+"', picture='"+picture+"' where id='"+id+"'";
				int row1=statement.executeUpdate(sql);
				if(row1==1) {
					res.setPicture_version(v+1);
					res.setCode(101);
					res.setResponse("ͷ���޸ĳɹ�");
				}
				else {
					res.setCode(401);
					res.setResponse("����ʧ�ܣ����Ժ�����");
				}
			} else { // ������	
				res.setCode(402);
				res.setResponse("�û�״̬�쳣������ʧ�ܣ����Ժ�����");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			res.setCode(403);
			res.setResponse("����ʧ�ܣ����Ժ�����");
		}
		
		String resStr = JSONObject.fromObject(res).toString();
		 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // ���Զ��ַ������м��ܲ�������Ӧ�ĵ��˿ͻ��˾���Ҫ����
	        response.getWriter().append(resStr).flush();
	}

}
