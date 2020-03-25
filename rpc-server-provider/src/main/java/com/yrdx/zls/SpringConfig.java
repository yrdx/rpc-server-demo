
package com.yrdx.zls;

import com.yrdx.zls.server.RpcServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author zhuls
 * @version V1.0
 * @since 2020-03-25 12:25
 */
@Configuration
@ComponentScan(basePackages = "com.yrdx.zls")
public class SpringConfig {

    @Bean(name="rpcServer")
    public RpcServer gpRpcServer(){
        return new RpcServer(8080);
    }
}
