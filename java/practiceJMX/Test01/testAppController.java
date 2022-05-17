package aplus.msales.comm.bean;

import org.springframework.stereotype.Service;

@Service("testAppController")
public class testAppController implements testAppMBean{

	public String print(String msg) {return "메시지를 보여줍니다 : "+msg;}
	public String SQL(String sql) 	{return "아직 기능이 없습니다. ["+sql+"]";}

}
