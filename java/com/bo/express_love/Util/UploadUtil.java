package com.bo.express_love.Util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UploadUtil {
    private static UploadUtil uploadUtil=null;
    public static UploadUtil getInstance(){
        if(null==uploadUtil){
            uploadUtil=new UploadUtil();
        }
        return uploadUtil;
    }
    /**
      * @Description:   获得前端的文件判断是否上传完成
      * @Param:   request,path
      * @return:   OK or Error
      * @Author: BO
      * @Date: 2020/8/3
      */
    public  void fileUtil_1(HttpServletRequest request,String path) throws IOException {/*multipartfileone解析文件函数，返回文件存放路径*/
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());/*获得request*/
        MultipartHttpServletRequest req = resolver.resolveMultipart(request);/*解析request*/

        System.out.println(req.getParameter("file0"));
        System.out.println(req.getFile("file0"));
        System.out.println(req.getAttribute("file0"));
        System.out.println(req.getMultiFileMap().get(0));

//        req.getParameter("file");

        Map map=req.getParameterMap();//遍历req所有的参数
        Iterator i=map.keySet().iterator();
        while (i.hasNext()){
            System.out.println(i.next());
        }
        int x=0;
        MultipartFile File =  req.getFile("file"+x);/*获得文件*/
//        System.out.println(File);
        while (File!=null){
            String filePath;
            filePath=path;/*写出临时路径*/
            File dir = new File(filePath);/*没有则创建*/
            if (!dir.exists()) {
                dir.mkdir();
            }
            filePath=filePath+"/"+File.getOriginalFilename();/*获得文件名，并连接到临时路径*/
            File file  =  new File(filePath);// 文件保存位置和文件名
            File.transferTo(file);/*直接保存*/
            ++x;
            File =  req.getFile("file"+x);/*获得文件*/
        }





    }
    public  void fileUtil_2(HttpServletRequest request,String uploadPath) throws Exception {
        final long serialVersionUID = 1L;
        // 上传文件存储目录
        // 上传配置
        final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;//3M
        final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
        final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            System.out.println("不是多媒体上传！");
        }
// 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        File uploadDir = new File(uploadPath);//
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String filePath=null;
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            System.out.println("解析"+formItems);
            if (formItems != null && formItems.size() > 0) {

                // 迭代表单数据

//               设置显示图片的个数
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
//                    if (!item.isFormField())
                    if(item.getFieldName().equals("file"))
                    {

//                        有一次图片上传
                        String fileName = new File(item.getName()).getName();
//                        System.out.println(fileName);
                        if(fileName==null||fileName==""){continue;}
//                        filePath = uploadPath +File.separator+ fileName;//开发
                        filePath = uploadDir.getAbsolutePath() +File.separator+ fileName;//发布
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);


                    }
                }
            }

    }
    public  void fileUtil_3(MultipartFile[] files,String path) throws IOException ,NullPointerException{/*multipartfileone解析文件函数，返回文件存放路径*/
        if(files.length==0){
            throw new NullPointerException();
        }
        for (MultipartFile f:files
             ) {
            String filePath=path;/*写出临时路径*/
            File dir = new File(filePath);/*没有则创建*/
            if (!dir.exists()) {
                dir.mkdirs();
            }
            filePath=filePath+"/"+f.getOriginalFilename();/*获得文件名，并连接到临时路径*/
            File file  =  new File(filePath);// 文件保存位置和文件名
            f.transferTo(file);/*直接保存*/

        }






    }
}
