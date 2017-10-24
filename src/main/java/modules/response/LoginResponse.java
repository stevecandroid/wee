package modules.response;

public class LoginResponse extends BaseResponse {

    public LoginResponse(int status) {
        super(status);
    }

    public static final int PASSWORD_INCORRECT = 1;
    public static final int ACCOUNT_NOT_EXIST = 2;
}
