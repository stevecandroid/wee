package db;

/**
 * Created by steve on 17-10-24.
 */

public class Message {
    private long time;
    private int direction;
    private String message;

    public Message(long time, int direction, String message) {
        this.direction = direction;
        this.message = message;
        this.time = time;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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
}