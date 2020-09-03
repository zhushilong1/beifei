package com.youlu.linux.export.model;

/**
 * @author zhushilong
 */
public class CmdResModel {
    private boolean isOk;
    private String okMsg;

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getOkMsg() {
        return okMsg;
    }

    public void setOkMsg(String okMsg) {
        this.okMsg = okMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    private String errMsg;
}
