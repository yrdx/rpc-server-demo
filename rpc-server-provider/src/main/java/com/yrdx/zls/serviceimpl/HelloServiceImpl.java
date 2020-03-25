
package com.yrdx.zls.serviceimpl;

import com.yrdx.zls.RpcService;
import com.yrdx.zls.service.IHelloService;

/**
 * @author zhuls
 * @version V1.0
 * @since 2020-03-25 11:19
 */
@RpcService(value = IHelloService.class, version = "v1.0")
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String str) {
        return "hello " + str + "!";
    }
}
