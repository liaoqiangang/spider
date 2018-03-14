package com.spider.queue;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  检索页面并下载下来
 * Created by liaoqiangang-pc on 2018/3/2.
 */
public class RetrivePage {
    private static HttpClient httpClient = new HttpClient();

    static {
        //httpClient.getHostConfiguration().setProxy("10.10.37.186",8080);
    }

    public static boolean downloadPage(String path) throws HttpException,IOException{
        InputStream is = null;
        OutputStream os = null;
        GetMethod postMethod = new GetMethod(path);
        int code = httpClient.executeMethod(postMethod);
        if(code == HttpStatus.SC_OK){
            is = postMethod.getResponseBodyAsStream();
            String fileName = path.substring(path.lastIndexOf("/")+1);
            os = new FileOutputStream(fileName);

            int tempByte = -1;
            while ((tempByte=is.read())>0){
                os.write(tempByte);
            }
            if(is!=null){
                is.close();
            }
            if(os!=null){
                os.close();
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            RetrivePage.downloadPage("http://www.runoob.com/regexp/regexp-tutorial.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
