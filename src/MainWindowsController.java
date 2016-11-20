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

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.*;

public class MainWindowsController {


    public TextArea ElevatorStatusOutput;



    @FXML
    public void initialize(){



    }

    public void buttonOn(ActionEvent actionEvent) {

        Simulator ecs = new Simulator();

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                while(true){
                    ArrayList<String> status = ecs.getElevatorController().getElevatorsStatus();
                    try{
                    ElevatorStatusOutput.clear();
                    for (String st : status) {
                        ElevatorStatusOutput.appendText(st + '\n');
                    }
                    ElevatorStatusOutput.appendText("============="+ status.size()+"==========================" + '\n');
                    }catch(Exception e){
                        e.printStackTrace();

                    }

                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                return null;
            }
        };
        new Thread(task).start();


    }

    public void DiuOn(ActionEvent actionEvent) {


    }
}
