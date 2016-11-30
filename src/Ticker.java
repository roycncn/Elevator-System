/**
 * Created by roycn on 2016/11/18.
 */
public class Ticker extends Thread {
    protected int tick;
    private String id;
    protected int numOfConsumer;
    private ElevatorController elevatorController;

    public Ticker(String id, ElevatorController ec, int tick) {
        this.id = id;
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
