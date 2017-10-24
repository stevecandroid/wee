import javax.websocket.*;

abstract class WebSocket  {

    @OnOpen
    public abstract void onOpen(Session session);



    @OnMessage
    public abstract void onMessage(String message, Session session);



    @OnError
    public abstract void onError(Session session, Throwable error);



    @OnClose
    public abstract void onClose();


}
