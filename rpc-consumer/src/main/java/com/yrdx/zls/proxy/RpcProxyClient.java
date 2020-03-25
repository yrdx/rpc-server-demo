
package com.yrdx.zls.proxy;

import java.lang.reflect.Proxy;

/**
 *
 * @author zhuls
 * @version V1.0
 * @since 2020-03-25 12:38
 */
public class RpcProxyClient {

  public <T> T clientProxy(final Class<?> interfaceCls, final String host, final int port) {
      return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class<?>[]{interfaceCls} ,
           new RemoteInvocationHandler(host, port));

  }
}
