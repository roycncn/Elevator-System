/**
 * Created by roycn on 2016/11/18.
 */
public class Ticker extends AppThread {
    protected int tick;
    protected  int numOfConsumer;
    public Ticker(String id,int tick,int numOfConsumer){
        super(id);
        this.messageBox = new MessageBox(id);
        this.tick = tick;
        this.numOfConsumer = numOfConsumer;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(tick);
            } catch (Exception e) {};
            for (int i=0;i<numOfConsumer;i++)
                messageBox.send(new Message("Ticker"));
        }
    }
}
