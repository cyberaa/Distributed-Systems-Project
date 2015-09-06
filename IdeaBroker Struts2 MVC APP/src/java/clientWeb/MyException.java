package clientWeb;

public class MyException extends Exception{
    private String detail;
    public MyException(String detail){
        super(detail);
        this.detail = detail;
    }
    public String getDetail() {
        return detail;
    }
}