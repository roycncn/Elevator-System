/**
 * Created by roycn on 2016/11/19.
 */

import com.sun.tracing.dtrace.StabilityLevel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
//import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Thread.*;

public class MainWindowsController {


    public TextArea ElevatorStatusOutput;
    protected  Simulator ecs;


    @FXML
    public void initialize(){
        this.ecs = Simulator.getInstance();
    }

    public void buttonOn(ActionEvent actionEvent) {
        System.out.println("This Button is wasted");
        ecs.elevatorController.findElevator(new Floor(0), new Floor(10));
        ecs.elevatorController.findElevator(new Floor(2), new Floor(7));
        ecs.elevatorController.findElevator(new Floor(7), new Floor(3));
        ecs.elevatorController.findElevator(new Floor(6), new Floor(2));

    }


    public void addBtnOn(ActionEvent actionEvent) {

    }

    public void useKiosk(ActionEvent actionEvent) {

        List<String> choices = new ArrayList<>();
        List<Kiosk> kiosks = ecs.getkioskArrayList();
        for (Kiosk k : kiosks) {
            choices.add(k.getKioskID());
        }

        ChoiceDialog<String> Cdialog = new ChoiceDialog<>(choices.get(0),choices);
        Cdialog.setTitle("Choose Kiosk");
        Cdialog.setHeaderText("Look, a Choice Dialog");
        Cdialog.setContentText("Choose your Kiosk:");

// Traditional way to get the response value.
        Optional<String> Cresult = Cdialog.showAndWait();
        if (Cresult.isPresent()){
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("You Are in front of"+Cresult.get());
            dialog.setHeaderText("You Are in front of"+Cresult.get());
            dialog.setContentText("Please enter your Destination:");

// Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                System.out.println("You form: "+Cresult.get() +" Going to" + result.get());
            }

        }

    }
}
