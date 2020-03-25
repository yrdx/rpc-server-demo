
package com.yrdx.zls;

import com.yrdx.zls.proxy.RpcProxyClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author zhuls
 * @version V1.0
 * @since 2020-03-25 12:43
 */
@Configuration
public class SpringConfig {

    @Bean(name="rpcRoxyClient")
    public RpcProxyClient proxyClient(){
        return new RpcProxyClient();
    }
}
