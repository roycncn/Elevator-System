/**
 * Created by roycn on 2016/11/19.
 */

import ElevatorBackend.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
//import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;

public class MainWindowsController {


    public TextArea ElevatorStatus;
    protected Simulator ecs;


    @FXML
    public void initialize(){
        this.ecs = Simulator.getInstance();

        //Reg Listener to Elevators
        for (Elevator ele:ecs.getElevators()){
            ele.addPositionListener(new ElevatorPositionListener() {
                @Override
                public void onPositionChange() {
                    UpdateElevatorStatus();
                }
            });
        }
        UpdateElevatorStatus();
        ElevatorStatus.setEditable(false);
    }

    public void UpdateElevatorStatus(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String result ="";
                for (String status:ecs.getElevatorController().getElevatorsStatus()
                        ) {
                    result+= status+"\n";
                }
                ElevatorStatus.setText(result);
            }
        });


    }

    public void onUseKioskBtn(ActionEvent actionEvent) {
        List<String> KioskChoices = new ArrayList<>();
        List<String> DstChoices = new ArrayList<>();

        TextInputDialog UserIdDlg = new TextInputDialog("");
        UserIdDlg.setTitle("Please input User ID");
        UserIdDlg.setHeaderText("Please input User ID");
        UserIdDlg.setContentText("Please input User ID:");

        //Generate DstChoices
        Optional<String> UserIdResult = UserIdDlg.showAndWait();
        if (UserIdResult.isPresent()){
            AccessConfiguration ac = AccessConfiguration.getInstance();
            Iterator<Integer> floorIt = ac.findAccessRule(UserIdResult.get()).getAccessibleFloorNumber().iterator();
            DstChoices.add("0");//Add G floor
            while (floorIt.hasNext()){
                DstChoices.add(String.valueOf(floorIt.next()));
            }
            DstChoices.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return Integer.parseInt(o1)-Integer.parseInt(o2);
                }
            });
        }else {
            return;
        }


        //Generate KioskChoices
        for (Kiosk k : ecs.getkioskArrayList()) {
            KioskChoices.add(String.valueOf(k.getLocation().getFloorLevel()));
        }


        ChoiceDialog<String> kioskFloorDlg = new ChoiceDialog<>(KioskChoices.get(0),KioskChoices);
        kioskFloorDlg.setTitle("Choose Kiosk");
        kioskFloorDlg.setHeaderText("Choose your Kiosk:");
        kioskFloorDlg.setContentText("Choose your Kiosk:");
        Optional<String> kioskFloorResult = kioskFloorDlg.showAndWait();
        if (kioskFloorResult.isPresent()){
            ChoiceDialog<String> dstFloorDlg = new ChoiceDialog<>(DstChoices.get(0),DstChoices);
            dstFloorDlg.setTitle("You are in front of Level "+kioskFloorResult.get()+"'s Kiosk");
            dstFloorDlg.setHeaderText("You are in front of Level "+kioskFloorResult.get()+"'s Kiosk");
            dstFloorDlg.setContentText("Please enter your Destination:");
            Optional<String> dstFloorResult = dstFloorDlg.showAndWait();
            // Traditional way to get the response value.
            if (dstFloorResult.isPresent()&&dstFloorResult.get()!= null){
                int from = Integer.parseInt(kioskFloorResult.get());
                int to = Integer.parseInt(dstFloorResult.get());
                if(from!=to){
                    //Find A elevator
                    String eleId = ecs.getElevatorController().findElevator(new Floor(from), new Floor(to));
                    System.out.println("Elevator "+eleId+" will serve you");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText(eleId+" will serve you");
                    alert.showAndWait();
                }else {
                    //When from floor == to floor
                    System.out.println("No Need Move!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Just Stay Here");
                    alert.showAndWait();
                }//End if(from!=to)
            }else{
                //When the Dst is Null
                System.out.println("No Dst!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Please Input your Destination Floor");
                alert.showAndWait();
            }//End if(DstFloorResult.isPresent()&&DstFloorResult.get()!= null)

        }

    }


    public void onAdminBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/AdminPanel.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Admin Panel");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void onMonitorBtn(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("FXML/MonitorPanel.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Monitor Panel");
        stage.setScene(new Scene(root));
        stage.show();



    }

    public void onSystemBtn(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConfigPanel.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Monitor Panel");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
