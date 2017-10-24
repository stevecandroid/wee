import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@ServerEndpoint(value = "/websocket/{id}")
public class MyWebSocket {


    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentMap<String,MyWebSocket> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String id ;


    /**
     * 25      * 连接建立成功调用的方法
     * 26      * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * 27
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id , Session session) {
        System.out.println("one user login id = " + id );
        this.id = id ;
        this.session = session;
        webSocketMap.put(id,this);     //ru map

    }

    @OnMessage
    public void onMessage(String message, Session session) {
        //   to>id,id,id,id,id,id,id,id<Message
        int start = message.indexOf('>');
        int end = message.indexOf('<');
        String rawids = message.substring(start+1,end);
        System.out.println("来自客户端的消息:" + message);
        System.out.println(rawids);
        String[] ids = rawids.split(",");

        for(String id : ids){
            try {
                MyWebSocket websocket = webSocketMap.get(id);
                if(websocket != null){
                    websocket.session.getBasicRemote().sendText(message.substring(end+1,message.length()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误 " + error.toString());
        webSocketMap.remove(this.id);
    }

    @OnClose
    public void onClose(){
        System.out.println("Close " + this.id );
        webSocketMap.remove(this.id);
    }

}