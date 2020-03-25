
package com.yrdx.zls.handler;

import com.yrdx.zls.request.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 *
 * @author zhuls
 * @version V1.0
 * @since 2020-03-25 11:35
 */
public class ProcessorHandler implements Runnable{

    private Socket socket;
    private Map<String,Object> handlerMap;


    public ProcessorHandler(Socket socket, Map<String,Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream=null;
        ObjectOutputStream objectOutputStream=null;
        try {
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            //输入流中应该有什么东西？
            //请求哪个类，方法名称、参数
            RpcRequest rpcRequest=(RpcRequest)objectInputStream.readObject();
            Object result=invoke(rpcRequest); //反射调用本地服务
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest request)
        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String serviceName=request.getClassName();
        String version=request.getVersion();
        //增加版本号的判断
        if(!StringUtils.isEmpty(version)){
            serviceName+="-"+version;
        }

        Object service=handlerMap.get(serviceName);
        if(service==null){
            throw new RuntimeException("service not found:"+serviceName);
        }
        Object[] args = request.getParameters();
        Method method = null;
        if(args!=null) {
            Class<?>[] types = new Class[args.length]; //获得每个参数的类型
            for (int i=0;i < types.length; i++) {
                types[i] = args[i].getClass();
            }
            Class clazz=Class.forName(request.getClassName());
            method=clazz.getMethod(request.getMethodName(),types);
        } else {
            Class<?> clazz = Class.forName(request.getClassName());
            method = clazz.getMethod(request.getMethodName());
        }
        Object result = method.invoke(service, args);
        return result;
    }
}
