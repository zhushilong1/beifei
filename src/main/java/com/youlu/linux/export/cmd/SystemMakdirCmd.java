package com.youlu.linux.export.cmd;

public class SystemMakdirCmd {

    public String content(String datePath){
        return " mkdir -pv /data/backup/"+datePath;
    }
}
