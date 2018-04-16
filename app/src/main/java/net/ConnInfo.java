package net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class ConnInfo {
    private InputStream inputStream;
    private InputStream errorStream;
    private int responseCode;

    private ConnInfo() {

    }

    public ConnInfo(HttpURLConnection conn) {
        try {
            this.responseCode = conn.getResponseCode();
            this.inputStream = conn.getInputStream();
            this.errorStream = conn.getErrorStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public InputStream getErrorStream() {
        return errorStream;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
