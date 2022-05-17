package aplus.msales.comm.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

@Configuration
public class MbeanExporter{
	@Bean
	public MBeanExporter mBeanExporter(MbeanController mbeanController) {
		MBeanExporter mx = new MBeanExporter();
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("mbeanController:name=MbeanController", mbeanController);
		beans.put("testMBean:name=TESTBEANS", mbeanController);
		mx.setBeans(beans);
		return mx;
	}
	
	@Bean
	public MBeanExporter mBeanExporter2(testAppMBean appBean) {
		MBeanExporter mx = new MBeanExporter();
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("mbeanController:name=testAppBean", appBean);
		mx.setBeans(beans);
		return mx;
	}
}
