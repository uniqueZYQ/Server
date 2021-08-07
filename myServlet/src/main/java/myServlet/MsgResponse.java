package myServlet;

public class MsgResponse {
	private int code;
	private String response;
	
	private int id;
    private String content;
    private int sub_id;
    private int obj_id;
    private String time;
    private int picture;
    private int recalled;

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSub_id(){
        return sub_id;
    }
    public int getObj_id(){
        return obj_id;
    }
    public String getTime(){
        return time;
    }
    public String getContent(){
        return content;
    }
    public int getCode() {
    	return code;
    }
    public void setCode(int code) {
    	this.code=code;
    }
    public void setResponse(String response) {
    	this.response=response;
    }
    public String getResponse() {
    	return response;
    }

    public int getPicture(){return picture;}
    public int getRecalled(){
        return recalled;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public void setObj_id(int obj_id) {
        this.obj_id = obj_id;
    }
    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }
    public void setTime(String time) {
        this.time = time;
    }
   
    public void setPicture(int picture) {
        this.picture = picture;
    }
    public void setRecalled(int recalled){
        this.recalled=recalled;
    }
}
