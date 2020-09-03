package com.youlu.linux.export.model;

/**
 * @author zhushilong
 */
public class Canshu {
    private String serverUserName;
    private String serverPassWord;
    private String serverIp;
    private String databaseUserName;
    private String databasePassWord;
    private String databasePort;
    private String databaseSock;
    private String databaseName;

    public String getServerUserName() {
        return serverUserName;
    }

    public void setServerUserName(String serverUserName) {
        this.serverUserName = serverUserName;
    }

    public String getServerPassWord() {
        return serverPassWord;
    }

    public void setServerPassWord(String serverPassWord) {
        this.serverPassWord = serverPassWord;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getDatabaseUserName() {
        return databaseUserName;
    }

    public void setDatabaseUserName(String databaseUserName) {
        this.databaseUserName = databaseUserName;
    }

    public String getDatabasePassWord() {
        return databasePassWord;
    }

    public void setDatabasePassWord(String databasePassWord) {
        this.databasePassWord = databasePassWord;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseSock() {
        return databaseSock;
    }

    public void setDatabaseSock(String databaseSock) {
        this.databaseSock = databaseSock;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String toString() {
        return "Canshu{" +
                "serverUserName='" + serverUserName + '\'' +
                ", serverPassWord='" + serverPassWord + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", databaseUserName='" + databaseUserName + '\'' +
                ", databasePassWord='" + databasePassWord + '\'' +
                ", databasePort='" + databasePort + '\'' +
                ", databaseSock='" + databaseSock + '\'' +
                ", databaseName='" + databaseName + '\'' +
                '}';
    }
}
