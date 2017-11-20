import com.google.gson.Gson;
import db.BaseDao;
import db.UserDao;
import modules.Message;
import uitls.DbHelper;
import uitls.Utils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@ServerEndpoint(value = "/websocket/{id}")
public class MyWebSocket {


    public static String CHAT_PREFIX = "c:";
    public static String ONLINE = "u:";
    public static String OFFLINE = "d:";

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentMap<String,MyWebSocket> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String id ;

    private String[] friends;


    /**
     * 25      * 连接建立成功调用的方法
     * 26      * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * 27
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id , Session session) throws IOException {
        System.out.println("one user login id = " + id );
        this.id = id ;
        this.session = session;

        UserDao.setOnline(Integer.parseInt(id));
        String queryFriends = UserDao.queryFriends(Integer.parseInt(id));
        this.friends = queryFriends.split("\\|");
        //广播好友 表示自己上线了
        for(String f : friends){
            MyWebSocket to = webSocketMap.get(f);
            if(to != null){
                to.session.getBasicRemote().sendText(ONLINE+id);
            }
        }



        webSocketMap.put(id,this);     //ru map

    }

    @OnMessage
    public void onMessage(String message, Session session) {

        Message msg = new Gson().fromJson(message,Message.class);

        // 自己规定的协议,CHAT_PREFIX开头的是两个人聊天
        if(msg.getType() == Message.CHAT) {
            //   c:t123123from'id'to>id,id,id,id,id,id,id,id<Message 聊天发送的格式
//            int start = message.indexOf('>');
//            int end = message.indexOf('<');
//            String rawids = message.substring(start + 1, end);
//            String[] ids = rawids.split(",");

             String id = String.valueOf(msg.getToId());
                try {
                    //得到接收方的对象,并主动发送信息
                    MyWebSocket websocket = webSocketMap.get(id);
                    if (websocket != null) {
//                    websocket.session.getBasicRemote().sendText(message.substring(end+1,message.length()));
                        websocket.session.getBasicRemote().sendText(message);
                    }

                    //记录发送信息的时间
                    long time = msg.getTime();

                    //记录发送方的聊天记录


                    String sql = "INSERT chat_record (id,time,message,direction) VALUES " +
                            "(" + Utils.parseString(this.id + "-" + id) + "," + Utils.parseString(String.valueOf(time)) + ","
                            + Utils.parseString(message) + "," + "1" + ")";

                    //记录接收方的聊天记录
                    String sql2 = "INSERT chat_record (id,time,message,direction) VALUES " +
                            "(" + Utils.parseString(id + "-" + this.id) + "," + Utils.parseString(String.valueOf(time)) + ","
                            + Utils.parseString(message) + "," + "0" + ")";

                    DbHelper.execute(BaseDao.getConnection(), sql);
                    DbHelper.execute(BaseDao.getConnection(), sql2);

                } catch (IOException | SQLException e) {
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
    public void onClose() throws IOException {

        for(String f : friends){
            MyWebSocket to = webSocketMap.get(f);
            if(to != null){
                to.session.getBasicRemote().sendText(OFFLINE+id);
            }
        }
        UserDao.setOffLine(Integer.parseInt(id));
        System.out.println("Close " + this.id );
        webSocketMap.remove(this.id);
    }

}