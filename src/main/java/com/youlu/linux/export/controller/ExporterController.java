package com.youlu.linux.export.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youlu.linux.export.cmd.HelloB;
import com.youlu.linux.export.cmd.SystemCtlCmd;
import com.youlu.linux.export.cmd.SystemMakdirCmd;
import com.youlu.linux.export.cmd.SystemStartCmd;
import com.youlu.linux.export.kit.RemoteShellKit;
import com.youlu.linux.export.model.Canshu;
import com.youlu.linux.export.model.CmdResModel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhushilong
 */
@RestController
public class ExporterController {

    @RequestMapping("/hello")
    public ModelAndView hello(HttpServletRequest httpServletRequest){
        List<Canshu> aa = aa();
        httpServletRequest.setAttribute("canshuList",aa);
        return new ModelAndView("hello","canshuList",aa);
    }

    @ResponseBody
    @RequestMapping("/beefed")
    public String export(Canshu canshu){
        SystemCtlCmd cmd = new SystemCtlCmd();
        String cmdContent = cmd.content();


        Base64 base64 = new Base64();
        try {
            CmdResModel executor = RemoteShellKit.executor(canshu.getServerUserName(), new String(base64.decode(canshu.getServerPassWord().substring(0,canshu.getServerPassWord().indexOf("是")))), canshu.getServerIp(), cmdContent);
            System.out.println("已经进入到/data/backup中"+(executor.isOk() ? "OK": executor.getErrMsg()));
            if(executor.isOk()){

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String qqqq = format.format(date).replaceAll("-", "");
                String s = qqqq.replaceAll(" ", "");
                String dataPath = s.replaceAll(":", "");
                System.out.println("当前日期字符串：" + dataPath);


                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = new Date();
                String qqq = format1.format(date1).replaceAll("-", "");

                SystemMakdirCmd systemMakdirCmd =new SystemMakdirCmd();
                String content = systemMakdirCmd.content(qqq);
                CmdResModel executor1 = RemoteShellKit.executor(canshu.getServerUserName(), new String(base64.decode(canshu.getServerPassWord().substring(0,canshu.getServerPassWord().indexOf("是")))), canshu.getServerIp(), content);
                System.out.println("已创建新文件夹"+(executor1.isOk() ? "OK": executor1.getErrMsg()));

                //开始备份
                SystemStartCmd systemStartCmd = new SystemStartCmd();
                String content1 = systemStartCmd.content(canshu, qqq,dataPath);
                CmdResModel executor2 = RemoteShellKit.executor(canshu.getServerUserName(), new String(base64.decode(canshu.getServerPassWord().substring(0,canshu.getServerPassWord().indexOf("是")))), canshu.getServerIp(), content1);
                System.out.println("已备份完毕"+(executor2.isOk() ? "OK": executor2.getErrMsg()));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "备份成功";
    }


    public JSONObject bb() {
//        String Path="D:/data/backup.json";
        String Path="/Users/zhushilong/Desktop/backup.json";
        BufferedReader reader = null;
        String laststr = "";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONObject jobj = JSON.parseObject(laststr);
        return jobj;
    }

    public List<Canshu> aa(){
        /*String path = HelloB.class.getClassLoader().getResource("Movie.json").getPath();
        String s = readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);*/
        JSONObject jobj = bb();
        JSONArray movies = jobj.getJSONArray("RECORDS");
        List<Canshu> canshuList = new ArrayList<>();
        Base64 base64 = new Base64();
        for (int i = 0 ; i < movies.size();i++){
            JSONObject key = (JSONObject)movies.get(i);
            Canshu canshu = new Canshu();
            canshu.setServerUserName((String)key.get("serverUserName"));

            String serverPassWord = (String)key.get("serverPassWord");
            canshu.setServerPassWord(base64.encodeToString(serverPassWord.getBytes()) +"是" +key.get("serverIp")+" mv的密码");
            canshu.setServerIp((String)key.get("serverIp"));
            canshu.setDatabaseUserName((String)key.get("databaseUserName"));

            String databasePassWord = (String)key.get("databasePassWord");
            canshu.setDatabasePassWord(base64.encodeToString(databasePassWord.getBytes()) +"是" +key.get("serverIp")+"里的数据库  用户名为"+key.get("databaseUserName")+" 端口为"+key.get("databasePort")+" 的密码");

            canshu.setDatabasePort((String)key.get("databasePort"));
            canshu.setDatabaseSock((String)key.get("databaseSock"));
            canshu.setDatabaseName((String)key.get("databaseName"));
            canshuList.add(canshu);
        }
        return canshuList;
    }


    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
