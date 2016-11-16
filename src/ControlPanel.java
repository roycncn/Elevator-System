/**
 * Created by test on 2016/11/16.
 */
public class ControlPanel extends Panel {
    public ControlPanel(String panelName) {
        super(panelName);
    }

    public void showStatus(ElevatorController eleController) {
        eleController.showElevatorsStatus();
        eleController.showKiosksStatus();
    }
}
