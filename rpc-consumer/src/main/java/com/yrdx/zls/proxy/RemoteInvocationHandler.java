
package com.yrdx.zls.proxy;

import com.yrdx.zls.request.RpcRequest;
import com.yrdx.zls.transport.RpcTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author zhuls
 * @version V1.0
 * @since 2020-03-25 12:33
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;

    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求数据的包装
        RpcRequest rpcRequest=new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion("v1.0");
        //远程通信
        RpcTransport netTransport=new RpcTransport(host,port);
        Object result=netTransport.send(rpcRequest);
        return result;
    }
}
