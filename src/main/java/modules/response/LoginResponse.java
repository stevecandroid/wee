package modules.response;

import db.UserDao;

public class LoginResponse  {

    private int status;
    private UserDao user;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LoginResponse(int status, UserDao user) {
        this.status = status;
        this.user = user;

    }

    public UserDao getUser() {
        return user;
    }

    public void setUser(UserDao user) {
        this.user = user;
    }

    public static final transient int PASSWORD_INCORRECT = 1;
    public static final transient int ACCOUNT_NOT_EXIST = 2;
}
