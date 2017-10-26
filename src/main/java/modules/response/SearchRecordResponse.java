package modules.response;

import db.Message;

import java.util.List;

public class SearchRecordResponse extends BaseResponse {

    private List<Message> messages;

    public SearchRecordResponse(int status, List<Message> messages) {
        super(status);
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
