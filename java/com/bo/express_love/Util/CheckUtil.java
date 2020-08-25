package com.bo.express_love.Util;

public class CheckUtil {

    public static boolean CheckExist(Object[] objects){
        for (Object e:
                objects) {
            if(null==e){
                return false;
            }
        }
        return true;
    }
    public static boolean CheckStringExist(String[] strings){
        for (String e:
                strings) {
            if(null==e||"".equals(e.trim())){
                return false;
            }
        }
        return true;
    }
    public static boolean CheckStringExist(String e){
            if(null==e||"".equals(e.trim())){
                return false;
            }
        return true;
    }
    public static boolean CheckStringExist(String e1,String e2){
            if(null==e1||"".equals(e1.trim())||null==e2||"".equals(e2.trim())){
                return false;
            }
        return true;
    }

    public static void main(String[] args) {
        String email="sss";
        System.out.println(CheckStringExist(new String[]{"1",email}));
    }
}
