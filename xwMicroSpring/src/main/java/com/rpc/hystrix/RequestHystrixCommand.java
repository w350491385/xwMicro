package com.rpc.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.rpc.factory.RpcData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 熔断处理
 */
public class RequestHystrixCommand extends HystrixCommand<Object> {
    private static Logger logger = LoggerFactory.getLogger(RequestHystrixCommand.class);
    private String name;
    private RpcData rpcData;
    private RpcInvoker rpcInvoker;

    public RequestHystrixCommand(RpcData rpcData, RpcInvoker invoker) {
       /* 配置依赖超时时间,1000毫秒*/
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(rpcData.getHystrixGroupName()))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationThreadTimeoutInMilliseconds(1000)));
        this.name = rpcData.getHystrixName();
        this.rpcData = rpcData;
        this.rpcInvoker = invoker;
    }

    @Override
    protected Object getFallback() {
        try {
            Class<?> result = this.rpcData.getResult();
            return result != null ? result.newInstance():null;
        } catch (InstantiationException e) {
            logger.error("",e);
        } catch (IllegalAccessException e) {
            logger.error("",e);
        }
        return null;
    }

    @Override
    protected Object run() throws Exception {
        return this.rpcInvoker.exe();
    }
}
