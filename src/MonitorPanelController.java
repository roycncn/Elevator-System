import ElevatorBackend.Elevator;
import ElevatorBackend.ElevatorQueueListener;
import ElevatorBackend.Simulator;
import javafx.scene.control.TextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Created by roycncn on 2016/12/1.
 */
public class MonitorPanelController {

    protected Simulator ecs;
    public TextArea queueStatus;

    @FXML
    public void initialize(){
        this.ecs = Simulator.getInstance();

        //Reg Listener to Elevators
        for (Elevator ele:ecs.getElevators()){
            ele.addQueueListener(new ElevatorQueueListener() {
                @Override
                public void onQueueChange() {
                    UpdateQueueStatus();
                }
            });
        }
        UpdateQueueStatus();
        queueStatus.setStyle("-fx-font: 20 arial;");
        queueStatus.setEditable(false);
    }

    private void UpdateQueueStatus() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String result ="";
                for (String status:ecs.getElevatorController().getElevatorsQueueStatus()
                        ) {
                    result+= status+"\n";
                }
                queueStatus.setText(result);
            }
        });




    }
}
