package Model;

public class InvalidInputException extends Exception{

	private static final long serialVersionUID = 1L;
	private String msg = "";
	
	public InvalidInputException(String msg) {
		this.msg += "*"+ msg +"\n";
	}
	
	public void Clear() {
		this.msg = "";
	}
	
	public String getMsg() {
		return this.msg;
	}
}
