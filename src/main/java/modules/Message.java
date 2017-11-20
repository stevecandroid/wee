package modules;

/**
 * Created by steve on 17-10-24.
 */

public class Message {

    public final static int CHAT = 1;
    public final static int ONLINE = 2;
    public final static int OFFLINE = 3;


    private int type ;
    private long time;

    private String message;
    private int fromId;
    private int toId;
    private int direction;

    public Message(int type, long time, String message, int fromId, int toId , int direction) {
        this.type = type;
        this.time = time;

        this.message = message;
        this.fromId = fromId;
        this.toId = toId;

        this.direction = direction;

    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}