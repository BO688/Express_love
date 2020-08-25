package com.bo.express_love;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class TomcatApplicationHelp extends SpringBootServletInitializer {

    @Override/*在tomcat中需要这个来协助启动springboot*/
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ExpressLoveApplication.class);

    }

}