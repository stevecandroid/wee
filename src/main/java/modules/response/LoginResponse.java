package modules.response;

import db.UserDao;

public class LoginResponse  {

    private int status;
    private int userId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LoginResponse(int status, int userId) {
        this.status = status;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static final transient int PASSWORD_INCORRECT = 1;
    public static final transient int ACCOUNT_NOT_EXIST = 2;
}
