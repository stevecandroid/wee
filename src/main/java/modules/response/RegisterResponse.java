package modules.response;

public class RegisterResponse extends BaseResponse {

    private int id;

    public RegisterResponse(int status , int id) {
        super(status);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
