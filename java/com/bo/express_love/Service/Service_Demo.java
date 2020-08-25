package com.bo.express_love.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bo.express_love.Util.FileUtil;
import com.bo.express_love.Util.UploadUtil;
import com.bo.express_love.entity.Demo_2;
import com.bo.express_love.entity.User_demo;
import com.bo.express_love.repository.Demo_2Mapper;
import com.bo.express_love.repository.UserDemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.*;

import static com.bo.express_love.Util.CheckUtil.CheckStringExist;

@RestController
public class Service_Demo {
    @Autowired
    private Demo_2Mapper demo_2Mapper;
    @Autowired
    private UserDemoMapper userDemoMapper;

    private String[] CustomOriginWord=new String[]{
            "开始倾听浪漫表白",
            "过去的[我]一直是一个人生活，享受着孤独，也憧憬着爱情。",
            "一个人的长廊",
            "一个人的山岗",
            "一个人的地铁",
            "一个人的游乐场",
            "但Ta依然乐观，微笑着，等待着",
            "生活难免有风风雨雨",
            "Ta总是能够轻松的应对",
            "并且面带阳光、自信的笑容",
            "生活也不会总是一帆风顺",
            "但Ta每次都能勇敢的面对",
            "可是Ta的爱情又在哪里呢？",
            "在镜子里面吗？Ta不敢相信",
            "Ta去问大树，我的爱情在哪里？",
            "于是，Ta一个人继续向前走",
            "直到有一天遇见了你",
            "[我]喜欢[Ta]，非常非常的喜欢，因为[Ta]的出现，[我]脸上有了更加灿烂的笑容",
            "可是[Ta]会喜欢[我]吗？",
            "[我]想。。。",
            "[我]想和[Ta]在一起",
            "[我]会经常去找[Ta]",
            "然后两个人一起出去玩",
            "晚上[我]会把[Ta]送回家",
            "直到很晚",
            "然后高兴地进入梦乡，在梦中还会和[Ta]在一起",
            "[我]想成为了[Ta]的逛街助手",
            "然后，[我]和[Ta]一起去很多地方玩",
            "[我]也想和[Ta]一块成为一对吃货",
            "再然后，他们会搬到了一起",
            "再然后，[我]会开始学习她的技能",
            "煮饭",
            "那时候，每天早上，他们吃着自己做的美食",
            "然后在同一个站台，高高兴兴的一起上班",
            "[我]会感觉自己好幸福",
            "他们可能偶尔也会吵架",
            "[我]不想这样",
            "一定是我有什么做的不对,[我]会这么想",
            "如果没有[Ta]在身边，窗外就没有风景",
            "如果没有[Ta]在身后",
            "[我]又怎会飞的更高更远",
            "[我]不想这样。Ta要为[Ta]改变自己",
            "我愿意一直陪伴着你",
            "爱情就像花草一样",
            "直到一天，[我]不想让自己后悔",
            "看着惊喜一点点准备好了",
            "想想未来他们生活的样子",
            "[我]就会一直充满干劲",
            "此页面不支持文字",
            "[我]想和[Ta]一起过幸福的生活",
            "永远有多远?",
            "如果三年后你注定是我女朋友，你何不提早行使你的权利",
            "我爱你",
            "此页面不支持文字",
            "我相信[我]和[Ta]的故事会一直继续下去。",
    };

    @RequestMapping("/findDemo")
    public JSONObject findDemo(){
        try {
            List list = new ArrayList();
            String dir=ResourceUtils.getURL("classpath:").getPath()+"static/pic";
            list= FileUtil.getInstance().getDirInDir(dir,list);
            JSONObject jo=new JSONObject();
            jo.put("demoType",JSONObject.toJSON(list));
           return jo;
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        }
        return JSONObject.parseObject("'status':'Error'");
    }
    @RequestMapping("/findDemoInfo")
    public JSONObject findDemoInfo(HttpServletRequest request){
        String demo=request.getParameter("demo");
        try {
            if(CheckStringExist(new String[]{demo})){
                List list = new ArrayList();
                String dir=ResourceUtils.getURL("classpath:").getPath()+"static/pic/"+demo;
                list=FileUtil.getInstance().getFileInDir(dir,list);
                JSONObject jo=new JSONObject();
                jo.put("demoInfo",JSONObject.toJSON(list));
                return jo;
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        }
        return JSONObject.parseObject("'status':'Error'");
    }
    @RequestMapping("/UploadImageDemo_1")
    @ResponseBody
    public JSONObject UploadImageDemo_1(@RequestParam(value = "file",required = false) MultipartFile[] file, HttpServletRequest request){//前端获得demo类型和图片，开始监听是否alive
        for (MultipartFile f:file
             ) {
            System.out.println(f.getOriginalFilename());
        }
        JSONObject jsonObject=new JSONObject();
        try {
           String client=request.getSession().getAttribute("login").toString();
            if(!CheckStringExist(new String[]{client})){
                jsonObject.put("status","Param incorrect");
                return jsonObject;
            }
           String path=ResourceUtils.getURL("classpath:").getPath()+"static/client/"+client+"/demo_1";
            UploadUtil.getInstance().fileUtil_3(file, path);
            User_demo user_demo=new User_demo();
            user_demo.setEmail(client);
            System.out.println(client);
            user_demo.setType(1);
            userDemoMapper.addDemo(user_demo);
//            CheckSessionFactory.getInstance().Builder(request,"Demo_1");
       }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("status","Error");
           return jsonObject;
       }
       jsonObject.put("status","OK");
        return jsonObject;
    }

    @RequestMapping(value = "/UploadTextDemo_2", method = RequestMethod.POST)
    public JSONObject UploadText(HttpServletRequest request){
        String Pattern=request.getParameter("Pattern");
        String you=request.getParameter("you");
        String me=request.getParameter("me");
        if(!CheckStringExist(new String[]{you,me})){
            return JSONObject.parseObject("{'status':'Param Error'}");
        }
        try {
            String client=request.getSession().getAttribute("login").toString();
            if("Self-determination".equals(Pattern)){
                System.out.println(request.getParameter("Pattern"));
                String title=request.getParameter("title");
                String success_word=request.getParameter("success_word");
                String fail_word=request.getParameter("fail_word");
                User_demo user_demo=new User_demo();
                user_demo.setEmail(client);
                System.out.println(client);
                user_demo.setType(21);
                userDemoMapper.addDemo(user_demo);
                Demo_2 demo_2=new Demo_2();
                demo_2.setMe(me);
                demo_2.setYou(you);
                demo_2.setEmail(client);
                demo_2.setTitle(title);
                demo_2.setSuccess_word(success_word);
                demo_2.setFail_word(fail_word);
                demo_2Mapper.AddDemo_21(demo_2);
                return JSONObject.parseObject("{'status':'OK'}");
            }else if("Custom-definition".equals(Pattern)){
                HashMap<String,String> map=new HashMap<>();
                System.out.println(request.getParameter("Pattern"));
                String title=request.getParameter("title");
                String success_word=request.getParameter("success_word");
                String fail_word=request.getParameter("fail_word");
                String [] CustomWord=request.getParameterValues("CustomWord");
                for (int i = 0; i <CustomWord.length ; i++) {
//                    System.out.println(CustomWord[i]);
                    if(!CustomWord[i].equals(CustomOriginWord[i])){
//                        System.out.println(i);
                        map.put("a"+(i+1),CustomWord[i]);
                    }
                }
                User_demo user_demo=new User_demo();
                user_demo.setEmail(client);
                System.out.println(client);
                user_demo.setType(22);
                userDemoMapper.addDemo(user_demo);
                map.put("title",title);
                map.put("success_word",success_word);
                map.put("fail_word",fail_word);
                map.put("email",client);
                map.put("me",me);
                map.put("you",you);
                demo_2Mapper.AddDemo_22(map);
                return JSONObject.parseObject("{'status':'OK'}");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("异常");
        }
        return JSONObject.parseObject("{'status':'Error'}");
    }
    @RequestMapping("/keepAlive")
    public void keepAlive(HttpServletRequest request){
        long count;
        try {
           count=Long.valueOf(request.getSession().getAttribute("alive").toString());
           request.getSession().setAttribute("alive",++count);
        }catch (NullPointerException npe){
            request.getSession().setAttribute("alive",0);
        }
    }
    @RequestMapping(value = "Client/1/img",method =  RequestMethod.POST)
    public JSONObject Client_1(HttpServletRequest request){
        try {
            String name=request.getSession().getAttribute("Username").toString();
            String email=request.getSession().getAttribute("login").toString();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",email);
            jsonObject.put("name",name);
            HashMap map=new HashMap();
            map.put("type","1");
            map.put("email",email);
            List<User_demo>Lud=userDemoMapper.ListDemo(map);
            if(Lud.size()>0){
                String path=ResourceUtils.getURL("classpath:").getPath()+"static/client/"+email+"/demo_1/";
                List list=FileUtil.getInstance().getFileInDir(path);
                jsonObject.put("img",list);
//                System.out.println(jsonObject);
                return jsonObject;
            }
        }
        catch (NullPointerException npe){
            System.err.println("incorrect access");
        } catch (FileNotFoundException e) {
            return JSONObject.parseObject("{'status':'not found'}");
        }
        return JSONObject.parseObject("{'status':'not found'}");
    }

    @RequestMapping(value = "Client/2" ,method =  RequestMethod.POST)
    public JSONObject Client_2(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        try {

            String email=request.getSession().getAttribute("login").toString();
            HashMap<String,String> map=new HashMap<>();
            map.put("email",email);
            map.put("type","2");
            List<User_demo>Lud=userDemoMapper.ListDemo_2(map);
            if(Lud.size()>0){
                int type=Lud.get(0).getType();
                System.out.println(type);
                if(type==21){
                    List<Demo_2> list=demo_2Mapper.SelectDemo_21(map);
                    if(list.size()>0){
                        Demo_2 demo_2=list.get(0);
//                        System.out.println(JSON.toJSON(demo_2));
                        jsonObject=JSONObject.parseObject(JSON.toJSON(demo_2).toString());
                        jsonObject.put("type",21);
                        return jsonObject;
                    }
                }else {
                    Map DemoMap =demo_2Mapper.SelectDemo_22(map);
                    if(DemoMap.size()>0){
                        Set set=DemoMap.keySet();
                        Iterator iterator=set.iterator();
                        while (iterator.hasNext()){
                            Object o=iterator.next();
                            System.out.println(o);
                            System.out.println(DemoMap.get(o));
                            jsonObject.put(o.toString(),DemoMap.get(o));
                        }
                        jsonObject.put("type",22);
                        return jsonObject;
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            return JSONObject.parseObject("{'status':'not found'}");
        }

        return JSONObject.parseObject("{'status':'not found'}");
    }

    @RequestMapping(value = "CheckDemoExist",method =  RequestMethod.POST)
    public JSONObject CheckDemoExist(HttpServletRequest request){
        try {
            String email=request.getSession().getAttribute("login").toString();
            String type=request.getParameter("demoType");
            if(CheckStringExist(new String[]{type})){
                JSONObject jsonObject=new JSONObject();
                HashMap map=new HashMap();
                map.put("type",type);
                map.put("email",email);
                List<User_demo>Lud=userDemoMapper.ListDemo(map);
                if(Lud.size()>0){
                    System.out.println(Lud.get(0).getType());
                    System.out.println(Lud.get(0).getEmail());
                    jsonObject.put("status","OK");
                    return jsonObject;
                }
            }

        }
        catch (NullPointerException npe){
            System.err.println("incorrect access");
        }
        return JSONObject.parseObject("{'status':'not found'}");
    }


}
