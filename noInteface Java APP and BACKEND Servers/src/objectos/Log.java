package objectos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class Log implements Serializable{
	private int id_log;
	private Date log_entry;
	private Ideia buy;
	private Ideia sell;
	private Ideia alterada;
	private float unit_price;
	private int amount;
	private String type = "";
	
	
	public Log(Date entry,Ideia buy,float unit_price,int amount,String type) {
		// TODO Auto-generated constructor stub
		this.id_log = 0;
		this.log_entry = entry;
		this.buy = buy;
		this.unit_price = unit_price;
		this.amount = amount;
		this.type = "Buy" + type;
	}
	public Log(Date entry,Ideia buy,Ideia sell,float unit_price,int amount,String type) {
		// TODO Auto-generated constructor stub
		this.id_log = 0;
		this.log_entry = entry;
		this.buy = buy;
		this.sell = sell;
		this.unit_price = unit_price;
		this.amount = amount;
		this.type = "Sell" + type;
	}
	public Log(Date entry,Ideia alterada,String type) {
		// TODO Auto-generated constructor stub
		this.log_entry = entry;
		this.alterada = alterada;
		this.type = type;
	}
	public Ideia getAlterada() {
		return alterada;
	}
	public void setAlterada(Ideia alterada) {
		this.alterada = alterada;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void setId_log(int id_log) {
		this.id_log = id_log;
	}
	public int getId_log() {
		return id_log;
	}
	public int getAmount() {
		return amount;
	}
	public Ideia getBuy() {
		return buy;
	}
	public Date getLog_entry() {
		return log_entry;
	}
	public Ideia getSell() {
		return sell;
	}
	public float getUnit_price() {
		return unit_price;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setBuy(Ideia buy) {
		this.buy = buy;
	}
	public void setLog_entry(Date log_entry) {
		this.log_entry = log_entry;
	}
	public void setSell(Ideia sell) {
		this.sell = sell;
	}
	public void setUnit_price(float unit_price) {
		this.unit_price = unit_price;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	

}
