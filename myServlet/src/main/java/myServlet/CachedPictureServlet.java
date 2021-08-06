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
			Statement statement = (Statement) connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��
			ResultSet result;
			
			String sqlQuery = "select * from cached_picture where user_code='" + user_code + "'";
			
			result = statement.executeQuery(sqlQuery); 
			if(result.next()){ // �Ѵ���
				res.setCode(101);
				res.setResponse("�Ѵ���") ;
			} else { // ������	
				String sql = "INSERT INTO cached_picture (user_code) VALUES (?)";
				PreparedStatement ps = connect.prepareStatement(sql);
				ps.setString(1, user_code);
				int row1 = ps.executeUpdate();
	
				if(row1 == 1){
					String sqlQueryId = "select * from cached_picture where user_code='" + user_code + "'";
					ResultSet result2 = statement.executeQuery(sqlQueryId); // ��ѯ������¼��id
					if(result2.next()){
						res.setCode(101);
						res.setResponse("��ӳɹ�");
					}
					else {
						res.setCode(401);
						res.setResponse("����ʧ�ܣ����Ժ�����");
					}	
				} else {
					res.setCode(402);
					res.setResponse("����ʧ�ܣ����Ժ�����");
				}
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
