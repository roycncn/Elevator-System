/**
 * Created by Fai on 17/11/2016.
 */
public class Message {
    private String detail;
    private String type;

    public Message(String type, String detail) {
        this.type = type;
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }
}
