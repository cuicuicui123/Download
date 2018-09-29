package com.example.cuiweicong.download.file;

public class FileManager {

    public static class SingletonHolder{
        static FileManager instance = new FileManager();
    }

    public static FileManager getInstance(){
        return SingletonHolder.instance;
    }

    private FileManager (){

    }

}
