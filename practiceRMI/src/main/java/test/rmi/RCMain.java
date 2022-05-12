package test.rmi;

import java.rmi.Naming;

public class RCMain {
    public static void main(String[] args) {
        try{
            String url = "rmi://127.0.0.1/rs";
            RSInterface rs = (RSInterface ) Naming.lookup(url);
            for (int i =0; i<args.length; i++){
                rs.println(i+"번째 클라이언트 호출 : "+args[i]);
                Thread.sleep(1000);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
