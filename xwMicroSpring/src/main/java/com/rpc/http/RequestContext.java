package com.rpc.http;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 */
public class RequestContext {

    private final static RequestContext requestContext = new RequestContext();
    private static ThreadLocal<Future<Object>> local = new ThreadLocal<Future<Object>>() {
            protected Future<Object> initialValue() {
                return null;
            }
        };

    private RequestContext(){}

    public static RequestContext getInstance(){
        return requestContext;
    }

    public void put(Future<Object> future){
        local.set(future);
    }

    public Object getResult(){
        Future<Object> future = local.get();
        try {
            return future != null ? future.get():null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
