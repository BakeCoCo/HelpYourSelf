package aplus.msales.comm.bean;

import org.springframework.stereotype.Service;

@Service("MbeanController")
public class MbeanController implements AplusConfigurationMBean{
	
	private String name = "LHJ";
	private int valX = 0;
	private int valY = 0;
	
	public void setName(String name) {
		this.name = name;
	}
	public int getValX() {
		return valX;
	}
	public void setValX(int valX) {
		this.valX = valX;
	}
	public int getValY() {
		return valY;
	}
	public void setValY(int valY) {
		this.valY = valY;
	}
	
	@Override
	public String name() {		return name;	}
	public int add() {		return valX+valY;	}
	public int sub() {		return valX-valY;	}
	public int mul() {		return valX*valY;	}
	public int div() {		return valX/valY;	}
}
