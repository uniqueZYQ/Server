package myServlet;

public class MyMsgResponse {
	private int id;
	private int oppo_id;
	private String oppo_name;
	private String last_time;
	private String last_detail;
	private String last;
	private int code;
	private String response;
	
	public void setCode(int code) {
		this.code=code;
	}
	public void setResponse(String response) {
		this.response=response;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	public void setOppo_id(int oppo_id) {
		this.oppo_id=oppo_id;
	}
	public void setOppo_name(String oppo_name) {
		this.oppo_name=oppo_name;
	}
	public void setLast_time(String last_time) {
		this.last_time=last_time;
	}
	public void setLast_detail(String last_detail) {
		this.last_detail=last_detail;
	}
	public void setLast(String last) {
		this.last=last;
	}
	
	public int getCode() {
		return code;
	}
	public int getId() {
		return id;
	}
	public String getResponse() {
		return response;
	}
	public int getOppo_id() {
		return oppo_id;
	}
	public String getLast_time() {
		return last_time;
	}
	public String getLast() {
		return last;
	}
	public String getLast_detail() {
		return last_detail;
	}
	public String getOppo_name() {
		return oppo_name;
	}
}
