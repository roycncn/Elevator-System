import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
/**
 * Created by Fai on 17/11/2016.
 */
public class MessageBox {
    private String id;
    private ArrayList<Message> messageQueue = new ArrayList<Message>();
    private int msgCnt = 0;

    public MessageBox(String id) {
        this.id = id;
    }

    public final synchronized void send(Message msg) {
        msgCnt++;
        messageQueue.add(msg);
//        notifyAll();
    }


    @Nullable
    public final synchronized String receive(String receiver) {
        // wait if message queue is empty
//        if ( msgCnt <= 0) {
//            while (true) {
//                try {
//                    wait();
//                    break;
//                } catch (InterruptedException e) {
//                    if (msgCnt > 0) {
//                        break; // msg arrived already
//                    } else {
//                        continue; // no msg yet, continue waiting
//                    }
//                }
//            }
//        }

        if ( messageQueue.size() > 0 ) {
            Message msg = messageQueue.get(0);
            if ( msg.getReceiver().equals(receiver) ) {
                messageQueue.remove(0);
                msgCnt--;
                return msg.getDetail();
            }
        }


        return null;
    }
}
