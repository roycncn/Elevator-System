/**
 * Created by roycn on 2016/11/19.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import static java.lang.Thread.*;

public class MainWindowsController {


    public TextArea ElevatorStatusOutput;



    @FXML
    public void initialize(){
        Simulator ecs = new Simulator();

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                while(true){
                    ElevatorStatusOutput.appendText(ecs.getElevatorController().getElevatorsStatus()+'\n');


                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                return null;
            }
        };
        new Thread(task).start();



    }

    public void buttonOn(ActionEvent actionEvent) {

    }
}
