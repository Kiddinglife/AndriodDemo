package com.cupcake.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpDownloader
    {
        // delace and initilize the url
        private URL url = null;

        /**
         * text file download the return value is the string in the file
         */
        public String downloadTextFile(String urlStr)
            {
                //Log.v("mad", "url is : "+urlStr);
                StringBuffer sb = new StringBuffer();
                String line = null;
                BufferedReader buffer = null;
                try
                    {
                        // create url object
                        url = new URL(urlStr);
                        // create http connection
                        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                        //Log.v("mad", "content length is : "+urlConn.getContentLength());
                        // create buffer reader to read the contents from inputstream
                        buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = buffer.readLine()) != null) // read from the buffer
                            {
                                sb.append(line); // buffer the line contents in sb
                                //Log.v("mad", line);
                            }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        Log.v("mad", "exception");
                    } finally
                    {
                        try
                            {
                                buffer.close();
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                    }
                //Log.v("mad", sb.toString());
                return sb.toString(); // return the contents as string
            }
    }
