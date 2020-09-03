package com.youlu.linux.export.kit;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.youlu.linux.export.model.CmdResModel;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhushilong
 */
public class RemoteShellKit {

    /**
     * @param username 用户名
     * @param password 密码
     * @param ip IP
     * @param command 命令
     * @return
     * @throws IOException
     */
    public static CmdResModel executor(String username, String password, String ip, String command) throws IOException {
        //参数传服务器的地址
        Connection conn = new Connection(ip);
        conn.connect();

        // check conn
        if (!conn.authenticateWithPassword(username, password)) {
            throw new IOException("Auth failed !");
        }

        Session session = conn.openSession();
        session.execCommand(command);

        InputStream stdOut = new StreamGobbler(session.getStdout());

        //正常IO流读取输入流数据的操作
        StringBuffer sb = new StringBuffer();
        byte[] bys = new byte[1024];
        int len = 0;
        while ((len = stdOut.read(bys)) != -1) {
            sb.append(new String(bys, 0, len));
        }
        String res = sb.toString();

//这是接收错误信息
        InputStream stdErr = new StreamGobbler(session.getStderr());
        sb = new StringBuffer();
        bys = new byte[1024];
        len = 0;
        while ((len = stdErr.read(bys)) != -1) {
            sb.append(new String(bys, 0, len));
        }
        String err = sb.toString();

        session.waitForCondition(ChannelCondition.EXIT_STATUS, 3000);


        if(stdOut!=null) {
            stdOut.close();
        }
        if(stdErr!=null) {
            stdErr.close();
        }
        if(session!=null) {
            session.close();
        }
        if(conn!=null) {
            conn.close();
        }

        CmdResModel model =  new CmdResModel();
        model.setErrMsg(err);
        model.setOk(StringUtils.isEmpty(err));
        model.setOkMsg(res);
        return model;
    }
}
