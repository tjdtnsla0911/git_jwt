package rc.bootsecurity;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootSecurityApplication {

    public static void main(String[] args) {
    	System.out.println("시작");
        SpringApplication.run(BootSecurityApplication.class, args);
        System.out.println("끝");
    }
    @Bean //이쪽은 빈객체라 바로올라감
    public ServletWebServerFactory servletContainer() {
    	System.out.println("BootSecurityApplication.java 의 ServletWebServerFactory이 시작됩니다 참고로여긴 @Bean");
        // Enable SSL Trafic

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {// 여긴일단 바로안올라온다.



            @Override
            protected void postProcessContext(Context context) {
                System.out.println("BootSecurityApplication.java 의 ServletWebServerFactory의 postProcessContext에 왔습니다 = ");
                SecurityConstraint securityConstraint = new SecurityConstraint();//여긴 첨에 []임 null은안적혀있음
                System.out.println("BootSecurityApplication.java 의 ServletWebServerFactory의 postProcessContext의 securityConstraiont = "+ securityConstraint);
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                System.out.println("BootSecurityApplication.java 의 ServletWebServerFactory의 postProcessContext의  securityConstraint.setUserConstraint(\"CONFIDENTIAL\")  = "+ securityConstraint);
                SecurityCollection collection = new SecurityCollection(); //첨에 여긴 [null]이다
                System.out.println("BootSecurityApplication.java 의 ServletWebServerFactory의postProcessContext의  collection = "+ securityConstraint);
                collection.addPattern("/*"); //특정경로에 대해서만 https로 리다이렉트시키기
                System.out.println("BootSecurityApplication.java 의 ServletWebServerFactory의postProcessContext의  collection이 collection.addPattern(/*)하고나서 = "+collection);
                //securityConstraint  도  securityConstraint 도 여기까진 뭘넣어고 [] [null]인건 변함없다



                securityConstraint.addCollection(collection);
                System.out.println("BootSecurityApplication.java 의 ServletWebServerFactory의 postProcessContext의  securityConstraint.addCollection(collection);= "+  securityConstraint);
                context.addConstraint(securityConstraint);

            }
        };

        // Add HTTP to HTTPS redirect
        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());


        return tomcat;
    }

    /*
    We need to redirect from HTTP to HTTPS. Without SSL, this application used
    port 8082. With SSL it will use port 8443. So, any request for 8082 needs to be
    redirected to HTTPS on 8443.
     */
    private Connector httpToHttpsRedirectConnector() {//여기가 먼저올라옴
    	   System.out.println("BootSecurityApplication.java 의 httpToHttpsRedirectConnector에 왓습니다 ");
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
 	   System.out.println("BootSecurityApplication.java 의 httpToHttpsRedirectConnector의 connector =  "+connector);
        connector.setScheme("http");
        connector.setPort(8000);
        connector.setSecure(false); // <-- 안전? 해제하는건가
        connector.setRedirectPort(8443);
  	   System.out.println("BootSecurityApplication.java 의 httpToHttpsRedirectConnector의 connector 전부받고나서 =  "+connector);
        return connector;
    }
}
