package com.youlu.linux.export.cmd;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youlu.linux.export.model.Canshu;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhushilong
 */
public class HelloB {

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


    public List<Canshu> aa(){
        String path = HelloB.class.getClassLoader().getResource("Movie.json").getPath();
        String s = readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        JSONArray movies = jobj.getJSONArray("RECORDS");
        List<Canshu> canshuList = new ArrayList<>();
        for (int i = 0 ; i < movies.size();i++){
            JSONObject key = (JSONObject)movies.get(i);
            Canshu canshu = new Canshu();
            canshu.setServerUserName((String)key.get("serverUserName"));
            canshu.setServerPassWord((String)key.get("serverPassWord"));
            canshu.setServerIp((String)key.get("serverIp"));
            canshu.setDatabaseUserName((String)key.get("databaseUserName"));
            canshu.setDatabasePassWord((String)key.get("databasePassWord"));
            canshu.setDatabasePort((String)key.get("databasePort"));
            canshu.setDatabaseSock((String)key.get("databaseSock"));
            canshu.setDatabaseName((String)key.get("databaseName"));
            canshuList.add(canshu);
        }
        return canshuList;
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }


    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    public static void main(String[] args) {
//        String s = new String("youlu");
//        System.out.println("原始：" + s);
//        System.out.println("MD5后：" + string2MD5(s));
//        System.out.println("加密的：" + convertMD5(s));
//        System.out.println("解密的：" + convertMD5(convertMD5(s)));

        Base64 base64 = new Base64();
        String s = base64.encodeToString("hqyl1qaz@WSX".getBytes());
        System.out.println(s);
        String s1 = new String(base64.decode(s));
        System.out.println(s1);
    }
}
