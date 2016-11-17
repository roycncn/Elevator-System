/**
 * Created by Fai on 17/11/2016.
 */
public class Message {
    private String receiver;
    private String detail;

    public Message(String receiver, String detail) {
        this.receiver = receiver;
        this.detail = detail;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public String getDetail() {
        return this.detail;
    }
}
