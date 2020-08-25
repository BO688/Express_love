package com.bo.express_love.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CheckSessionFactory {
    private static CheckSessionFactory checkSessionFactory=null;
   private Map<String,CheckSessionThread>map=new HashMap<>();
   private class CheckSessionThread implements Runnable{
        private HttpServletRequest  request;
        private String Demo;
        CheckSessionThread(HttpServletRequest request,String demo){
            this.request=request;
            Demo=demo;
        }
        @Override
        public void run() {
            long LastCount=0;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long NowCount=(long)request.getSession().getAttribute("alive");
            while (LastCount!=NowCount){
                LastCount=NowCount;
                NowCount=(long)request.getSession().getAttribute("alive");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            map.remove(request.getSession().getId()+Demo);
            String Client=(String)request.getSession().getAttribute("email");

        }
    }

    public static CheckSessionFactory getInstance(){
        if(null==checkSessionFactory){
            checkSessionFactory=new CheckSessionFactory();
        }
        return checkSessionFactory;
    }
    /**
     * @Description: 创建Thread并放入map中(避免重复创建，key由sessionID和demo组成)
     * @Param: request,demo
     * @return:   void
     * @Author: BO
     * @Date: 2020/8/3
     */
    public   void Builder(HttpServletRequest request,String demo){
       String key=request.getSession().getId()+demo;
       if (null==map.get(key)){
           CheckSessionThread checkSessionThread=new CheckSessionThread(request,demo);
           map.put(key,checkSessionThread);
           checkSessionThread.run();
       }

   }
   public CheckSessionThread getCheckSessionThread(HttpServletRequest request,String demo){
       return map.get(request.getSession().getId()+demo);
    }
}
