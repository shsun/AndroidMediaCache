package com.biz.net;

import com.base.net.NanoHTTPD;

import java.io.IOException;


/**
 * Created by shsun on 17/1/9.
 */
public class BMServer extends NanoHTTPD {


    public BMServer(int port) throws IOException {
        super(port);
        start();
        System.out.println("\nRunning! Point your browers to http://localhost:" + port + "/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Hello server</h1>\n";
        msg += "<p>We serve " + session.getUri() + " !</p>";
        return newFixedLengthResponse(msg + "</body></html>\n");
    }
}