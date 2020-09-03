package com.youlu.linux.export.cmd;

import com.youlu.linux.export.model.Canshu;
import org.apache.commons.codec.binary.Base64;

/**
 * @author zhushilong
 */
public class SystemStartCmd {

    public String content(Canshu canshu,String dataPath,String data){
        Base64 base64 = new Base64();
        return " /usr/local/mysql/bin/mysqldump -u"+canshu.getDatabaseUserName()+" -p"+new String(base64.decode(canshu.getDatabasePassWord().substring(0,canshu.getDatabasePassWord().indexOf("是"))))+" -P"+canshu.getDatabasePort()+" --socket="+canshu.getDatabaseSock()+" --single-transaction --routines --skip-triggers --set-gtid-purged=OFF -B "+canshu.getDatabaseName()+" > /data/backup/"+dataPath+"/"+canshu.getDatabaseName()+"_1_"+data+".sql";
    }
}
