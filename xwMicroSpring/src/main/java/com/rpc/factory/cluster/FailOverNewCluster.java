package com.rpc.factory.cluster;

import com.rpc.factory.ParamsData;
import com.rpc.factory.RpcData;
import com.rpc.factory.balance.Balance;
import com.rpc.factory.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/4.
 */
public class FailOverNewCluster implements Cluster {
    private static Logger logger = LoggerFactory.getLogger(FailOverCluster.class);
    private static final int failNum = 3;
    @Override
    public Object cluster(Balance balance, Protocol protocol, RpcData rpcData) throws Exception {
        int loop = rpcData.getRetries() < failNum ? rpcData.getRetries() : failNum;
        for (int i = 0; i < loop; i++) {
            Method method = null;
            Object proxy = null;
            String url = null;
            try {
                Class<?> clazz = rpcData.getClazz();
                url = balance.getUrl(rpcData);
                proxy = protocol.refer(clazz, url
                        , new ParamsData(rpcData.getClazz().getName(), rpcData.getMethodName(), rpcData.getTimeout()
                                , rpcData.getClusterKey(), rpcData.getParameterType(), rpcData.getArgs()));
                method = proxy.getClass().getMethod(rpcData.getMethodName(), rpcData.getParameterType());
            } catch (Exception e) {
                logger.error("url is {},{}", url, e);
            }
            if (method != null && proxy != null) {
                try {
                    return method.invoke(proxy, rpcData.getArgs());
                } catch (Exception e) {
                    logger.error("", e);
                    if ((i + 1) == loop)
                        throw e;
                }
            } else {
                logger.error("method or proxy is null, method is {},proxy is {}", method, proxy);
            }
        }
        throw new Exception(rpcData.getClazz() + "超过重试次数");
    }
}
