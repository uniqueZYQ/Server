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
		else if(type.equals("2")) {
			String id=request.getParameter("id");
			MsgResponse res = new MsgResponse();
			
			try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				String sql="select * from msg where id='"+id+"'";
				result = statement.executeQuery(sql);
				if(result.next()){ // 匹配成功
					res.setCode(101);
					res.setResponse("操作成功");
					res.setObj_id(result.getInt("obj_id"));
					res.setSub_id(result.getInt("sub_id"));
					res.setContent(result.getString("content"));
					res.setId(result.getInt("id"));
					res.setPicture(result.getInt("picture"));
					res.setRecalled(result.getInt("recalled"));
					res.setTime(result.getString("time"));
				} else { // 不存在
					res.setCode(401);
					res.setResponse("操作失败，请稍后再试");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				res.setCode(402);
				res.setResponse("操作失败，请稍后再试");
			}
			 String resStr = JSONObject.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("3")) {
			String sub_id=request.getParameter("sub_id");
			String obj_id=request.getParameter("obj_id");
			String sql="select * from msg where sub_id='"+sub_id+"' and obj_id='"+obj_id+"' order by id desc";
			
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
		else if(type.equals("4")) {
			String id=request.getParameter("id");
			String sql="select * from msg where sub_id='"+id+"' or obj_id='"+id+"' order by id desc";
			List<Integer> oppo_hist_id=new ArrayList<Integer>();
			List<MyMsgResponse> res=new ArrayList<MyMsgResponse>();
	        try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				ResultSet result;
				result = statement.executeQuery(sql);
				if(result.next()){ // 匹配成功
					do {
						String oppo_id;
						boolean myself;
						if(id.equals(result.getString("sub_id"))) {
							oppo_id=result.getString("obj_id");
							myself=true;
						}
						else {
							oppo_id=result.getString("sub_id");
							myself=false;
						}
						if(!oppo_hist_id.contains(Integer.valueOf(oppo_id).intValue())) {
							oppo_hist_id.add(Integer.valueOf(oppo_id).intValue());
							MyMsgResponse e=new MyMsgResponse();
							e.setId(result.getInt("id"));
							e.setOppo_id(Integer.valueOf(oppo_id).intValue());
							e.setLast_time(result.getString("time"));
							if(result.getInt("recalled")==1) {
								if(myself)
									e.setLast_detail("你撤回了一条消息");
								else
									e.setLast_detail("对方撤回了一条消息");
							}
							else if(result.getInt("picture")!=0) {
								e.setLast_detail("[图片]");
							}
							else
								e.setLast_detail(result.getString("content"));
							String new_sql="select nickname from user where id='"+oppo_id+"'";
							Connection connect1 = DBUtil.getConnect();
							Statement statement1 = (Statement) connect1.createStatement(); 
							ResultSet result1;
							result1 = statement1.executeQuery(new_sql);
							if(result1.next()) {
								e.setCode(101);
								e.setResponse("获取成功");
								e.setOppo_name(result1.getString("nickname"));
								String lasttext;
								//List<Msg> msg1=DataSupport.where("obj_id=? and sub_id=?",String.valueOf(myid),String.valueOf(oppo_id)).order("id desc").find(Msg.class);
								String new_2_sql="select * from msg where obj_id='"+id+"' and sub_id='"+oppo_id+"' order by time desc";
								Connection connect2 = DBUtil.getConnect();
								Statement statement2 = (Statement) connect2.createStatement(); 
								ResultSet result2;
								result2 = statement2.executeQuery(new_2_sql);
								List<Msg> msg1=new ArrayList<Msg>();
								while(result2.next()) {
									Msg m=new Msg();
									m.setContent(result2.getString("content"));
									m.setId(result2.getInt("id"));
									m.setObj_id(result2.getInt("obj_id"));
									m.setPicture(result2.getInt("picture"));
									m.setRecalled(result2.getInt("recalled"));
									m.setSub_id(result2.getInt("sub_id"));
									m.setTime(result2.getString("time"));
									msg1.add(m);
								}
								if(msg1.size()!=0) {
				                    int p1 = msg1.get(0).getId();
				                    //List<Last> last = DataSupport.where("user_id=? and oppo_id=?", String.valueOf(myid),String.valueOf(oppo_id)).order("last_id desc").find(Last.class);
				                    String new_3_sql="select * from last where user_id='"+id+"' and oppo_id='"+oppo_id+"' order by last_id desc";
									Connection connect3 = DBUtil.getConnect();
									Statement statement3 = (Statement) connect3.createStatement(); 
									ResultSet result3;
									result3 = statement3.executeQuery(new_3_sql);
									List<Last> last=new ArrayList<Last>();
									while(result3.next()) {
										Last l=new Last();
										l.setId(result3.getInt("id"));
										l.setLast_id(result3.getInt("last_id"));
										l.setOppo_id(result3.getInt("oppo_id"));
										l.setUser_id(result3.getInt("user_id"));
										last.add(l);
									}
				                    if(last.size()==0){
				                        /*Connector.getDatabase();
				                        Last lastshow=new Last();
				                        lastshow.setUser_id(myid);
				                        lastshow.setOppo_id(oppo_id);
				                        lastshow.setLast_id(0);
				                        lastshow.save();*/
				                    	String s = "INSERT INTO last (user_id,oppo_id,last_id) VALUES (?,?,?)";
				        				PreparedStatement ps = connect.prepareStatement(s);
				        				ps.setString(1, id);
				        				ps.setString(2, oppo_id);
				        				ps.setString(3, "0");
				        				ps.executeUpdate();
				                        //List<Last> last1 = DataSupport.where("user_id=? and oppo_id=?", String.valueOf(myid),String.valueOf(oppo_id)).order("last_id desc").find(Last.class);
				                    	String new_4_sql="select * from last where user_id='"+id+"' and oppo_id='"+oppo_id+"' order by last_id desc";
										Connection connect4 = DBUtil.getConnect();
										Statement statement4 = (Statement) connect4.createStatement(); 
										ResultSet result4;
										result4 = statement4.executeQuery(new_4_sql);
										List<Last> last1=new ArrayList<Last>();
										while(result4.next()) {
											Last l=new Last();
											l.setId(result4.getInt("id"));
											l.setLast_id(result4.getInt("last_id"));
											l.setOppo_id(result4.getInt("oppo_id"));
											l.setUser_id(result4.getInt("user_id"));
											last1.add(l);
										}
				                    	int p2 = last1.get(0).getLast_id();
				                        int size = 0;
				                        if (p1 > p2) {
				                            for (int ii = 0; ii < msg1.size(); ii++) {
				                                if (msg1.get(ii).getId() > p2) size++;
				                            }
				                        }
				                        if(size!=0){
				                            if(size>9){
				                                lasttext="[9+未读]";
				                            }
				                            else
				                                lasttext="["+String.valueOf(size) + "未读]";
				                        }
				                        else lasttext="";
				                    }
				                    else {
				                        int p2 = last.get(0).getLast_id();
				                        int size = 0;
				                        if (p1 > p2) {
				                            for (int ii = 0; ii < msg1.size(); ii++) {
				                                if (msg1.get(ii).getId() > p2) size++;
				                            }
				                        }
				                        if (size != 0) {
				                            if(size>9){
				                                lasttext="[9+未读]";
				                            }
				                            else
				                                lasttext = "[" + String.valueOf(size) + "未读]";
				                        } else lasttext = "";
				                    }
				                    e.setLast(lasttext);
				                }
							}
							else {
								e.setCode(402);
								e.setResponse("昵称获取失败");
							}
							res.add(e);
						}
					}
					while(result.next());
				} else { // 不存在
					MyMsgResponse e=new MyMsgResponse();
					e.setCode(102);
					e.setResponse("暂无信息");
					res.add(e);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				MyMsgResponse i=new MyMsgResponse();
				i.setCode(401);
				i.setResponse("数据请求失败，请检查网络连接");
				res.add(i);
			
			}
			 String resStr = JSONArray.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("5")) {
			String obj_id=request.getParameter("obj_id");
			String sql="select * from msg where obj_id='"+obj_id+"' order by id desc";
			
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
		else if(type.equals("6")) {
			String id=request.getParameter("id");
			String sql="update msg set recalled='1' where id='"+id+"'";
			
			CommonResponse res=new CommonResponse();
	        try {
				Connection connect = DBUtil.getConnect();
				Statement statement = (Statement) connect.createStatement(); 
				int n = statement.executeUpdate(sql);
				if(n==1){ // 成功
					res.setCode(101);
					res.setResponse("操作成功");
				} else { // 失败
					res.setCode(402);
					res.setResponse("撤回失败");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				res.setCode(401);
				res.setResponse("撤回失败");
			}
			 String resStr = JSONObject.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("7")) {
			String sub_id=request.getParameter("sub_id");
			String obj_id=request.getParameter("obj_id");
			String picture=request.getParameter("picture");
			String time=request.getParameter("time");
			
			CommonResponse res=new CommonResponse();
	        try {
				Connection connect = DBUtil.getConnect();
				String sql = "INSERT INTO msg (sub_id,obj_id,time,picture,recalled,content) VALUES (?,?,?,?,?,?)";
				PreparedStatement ps = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, sub_id);
				ps.setString(2, obj_id);
				ps.setString(3, time);
				ps.setString(4, picture);
				ps.setString(5, "0");
				ps.setString(6, "");
				
				int row1 = ps.executeUpdate();
				ResultSet n=ps.getGeneratedKeys();
				if(row1==1&&n.next()){ // 成功
					res.setCode(101);
					res.setId(n.getInt(1)) ;
					res.setResponse("操作成功");
				} else { // 失败
					res.setCode(402);
					res.setResponse("发送失败");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				res.setCode(401);
				res.setResponse("发送失败");
			}
			 String resStr = JSONObject.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
		else if(type.equals("8")) {
			String sub_id=request.getParameter("sub_id");
			String obj_id=request.getParameter("obj_id");
			String content=request.getParameter("content");
			String time=request.getParameter("time");
			
			CommonResponse res=new CommonResponse();
	        try {
				Connection connect = DBUtil.getConnect();
				String sql = "INSERT INTO msg (sub_id,obj_id,time,picture,recalled,content) VALUES (?,?,?,?,?,?)";
				PreparedStatement ps = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, sub_id);
				ps.setString(2, obj_id);
				ps.setString(3, time);
				ps.setString(4, "0");
				ps.setString(5, "0");
				ps.setString(6, content);
				
				int row1 = ps.executeUpdate();
				ResultSet n=ps.getGeneratedKeys();
				if(row1==1&&n.next()){ // 成功
					res.setId(n.getInt(1)) ;
					res.setCode(101);
					res.setResponse("操作成功");
				} else { // 失败
					res.setCode(402);
					res.setResponse("发送失败");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				res.setCode(401);
				res.setResponse("发送失败");
			}
			 String resStr = JSONObject.fromObject(res).toString();
			 //response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		        response.getWriter().append(resStr).flush();
		}
	}

}
