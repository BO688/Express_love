package com.bo.express_love.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class HttpRequestUtil {
    public static void LoopRequestAttr(HttpServletRequest request){
        Enumeration enumeration =request.getAttributeNames();
        while (enumeration.hasMoreElements()){
            System.out.println(enumeration.nextElement());
        }

    }
}
