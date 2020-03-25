
package com.yrdx.zls;

import com.yrdx.zls.proxy.RpcProxyClient;
import com.yrdx.zls.service.IHelloService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author zhuls
 * @version V1.0
 * @since 2020-03-25 12:32
 */
public class APP {
    public static void main(String[] args) {
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient=context.getBean(RpcProxyClient.class);
        IHelloService helloService = rpcProxyClient.clientProxy(IHelloService.class, "localhost", 8080);
        String result = helloService.sayHello("zls");
        System.out.println(result);
    }
}
