package com.httpclient.params;


/**
 * 请求参数
 */
public class BaseParams {
    private String jsonrpc="2.0";
    private String method;
    private Object params;
    private int id=-2339;

    public BaseParams(String command, Object params) {
        this.method = command;
        this.params = params;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public Object getParams() {
        return params;
    }

    public int getId() {
        return id;
    }
}
