/**
 * Created by roycn on 2016/11/18.
 */
public class Ticker extends AppThread {
    protected int tick;
    protected int numOfConsumer;
    private ElevatorController elevatorController;

    public Ticker(String id, ElevatorController ec, int tick) {
        super(id, ec);
        this.tick = tick;
        this.elevatorController = ec;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(tick);
            } catch (Exception e) {

            }
                this.elevatorController.sendMessageToAllElevators(new Message("TIC", "Message from ticker"));
        }
    }
}
