
package com.yrdx.zls.transport;

import com.yrdx.zls.request.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author zhuls
 * @version V1.0
 * @since 2020-03-25 12:36
 */
public class RpcTransport {

    private String host;
    private int port;

    public RpcTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(RpcRequest request){
        Socket socket=null;
        Object result=null;
        ObjectOutputStream outputStream=null;
        ObjectInputStream inputStream=null;
        try {
            socket=new Socket(host,port); //建立连接
            outputStream =new ObjectOutputStream(socket.getOutputStream());//网络socket
            outputStream.writeObject(request); //序列化()
            outputStream.flush();
            inputStream=new ObjectInputStream(socket.getInputStream());
            result=inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }
}
