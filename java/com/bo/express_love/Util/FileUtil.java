package com.bo.express_love.Util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {
    private static FileUtil fileUtil=null;
    public static FileUtil getInstance(){
        if(null==fileUtil){
            fileUtil=new FileUtil();
        }
        return fileUtil;
    }
    /**
     * @Description:   写入路径和list，返回带有该路径的文件名的list(递归)
     * @Param:   path,list
     * @return:   List
     * @Author: BO
     * @Date: 2020/8/1
     */
    public  List getAllFileInDir(String path, List<File> list){/*分享文件路径*/
        File file = new File(path);

        File[] tempList = file.listFiles();/*文件的列表*/
        for (int i = 0; i < tempList.length; i++) {/*循环文件列表*/
            if (tempList[i].isFile()) {/*判断是否文件，是则输出*/
//                System.out.println("文件：" + tempList[i]);

            }
            if (tempList[i].isDirectory()) {/*判断是否是文件夹，是则递归*/
//              System.out.println("文件夹：" + tempList[i]);
                getAllFileInDir(tempList[i].getAbsolutePath(),list);
            }else{
                list.add(tempList[i]);/*把不是文件夹的放入list*/
            }
        }
        return list;
    }
    /**
      * @Description:   写入路径和list，返回带有该路径的文件名的list
      * @Param:   path,list
      * @return:   List
      * @Author: BO
      * @Date: 2020/8/1
      */
    public  List getFileInDir(String path, List<File> list){/*把*/
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(tempList!=null){
            for (int i = 0; i < tempList.length; i++) {
                list.add(tempList[i]);/*把所有的文件放入列表*/
            }
        }
        return list;
    }
    public  List getFileInDir(String path){/*把*/
        List<String> list=new LinkedList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(tempList!=null){
            for (int i = 0; i < tempList.length; i++) {
                int index=tempList[i].getAbsolutePath().lastIndexOf("\\static");
                ;
                list.add("..\\..\\"+tempList[i].getAbsolutePath().substring(index+1));/*把所有的文件放入列表*/
            }
        }
        return list;
    }
    /**
      * @Description:  找到不空的文件夹
      * @Param:   path 、list
      * @return:   list
      * @Author: BO
      * @Date: 2020/8/1
      */
    public  List getDirInDir(String path,List<File> list){
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(null!=tempList){
            for (int i = 0; i < tempList.length; i++) {
                if(tempList[i].isDirectory())
                list.add(tempList[i]);/*把所有的文件放入列表*/
            }
        }
        return list;
    }
    /**
      * @Description:输入文件夹位置，递归删除
      * @Param:   path
      * @return:   boolean
      * @Author: BO
      * @Date: 2020/8/3
      */
    public  boolean DeleteInDir(String path){
        try {
            File file = new File(path);
            File[] tempList = file.listFiles();
                for (File f:tempList
                     ) {
                    System.out.println(f.getName());
                    if(!f.delete()){
                       DeleteInDir(f.getPath());
                    }
                }
           file.delete();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String path= ResourceUtils.getURL("classpath:").getPath()+"static/testDelete";
        System.out.println(path);
        System.out.println(FileUtil.getInstance().DeleteInDir(path));
    }
}
