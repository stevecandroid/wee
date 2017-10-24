package modules.response;

import db.UserDao;

import java.util.List;

public class SearchPeopleResponse extends BaseResponse {

    private List<UserDao> friends;

    public SearchPeopleResponse(int status, List<UserDao> friends) {
        super(status);
        this.friends = friends;
    }

    public List<UserDao> getFriends() {
        return friends;
    }

    public void setFriends(List<UserDao> friends) {
        this.friends = friends;
    }
}
