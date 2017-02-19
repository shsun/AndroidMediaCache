package com.danikula.videocache.demo;

import android.content.Context;

import java.io.File;

public enum Video {

//    ORANGE_1(Config.ROOT + "orange1.mp4"),
//    ORANGE_2(Config.ROOT + "orange2.mp4"),
//    ORANGE_3(Config.ROOT + "orange3.mp4"),
//    ORANGE_4(Config.ROOT + "orange4.mp4"),
//    ORANGE_5(Config.ROOT + "orange5.mp4");


    ORANGE_1("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_1.mp4"),
    ORANGE_2("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_2.mp4"),
    ORANGE_3("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_3.mp4"),
    ORANGE_4("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_4.mp4"),
    ORANGE_5("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_5.mp4");


//    http://vdata.tool.hexun.com/2010-10-21/125208504.mp4
//
//    http://vdata.tool.hexun.com/2014-07-09/166471246.mp4
//    http://vdata.tool.hexun.com/2014-07-09/166471192.mp4
//    http://vdata.tool.hexun.com/2014-07-09/166471239.mp4
//
//
//
//
//    http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_2.mp4
//    http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_3.mp4
//    http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_4.mp4
//    http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_5.mp4
//    http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_6.mp4


    public final String url;

    Video(String url) {
        this.url = url;
    }

    public File getCacheFile(Context context) {
        File dir = context.getExternalCacheDir();
        String name = name();
        File f = new File(dir, name);
        return f;
    }

    private class Config {
        private static final String ROOT = "https://raw.githubusercontent.com/danikula/AndroidVideoCache/master/files/";
    }
}
