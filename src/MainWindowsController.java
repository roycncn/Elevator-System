/**
 * Created by roycn on 2016/11/19.
 */

import ElevatorBackend.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        //Generate KioskChoices
        for (Kiosk k : ecs.getkioskArrayList()) {
            KioskChoices.add(String.valueOf(k.getLocation().getFloorLevel()));
        }
        //Generate DstChoices
        for (int i=0;i<ecs.getBuilding().getFloorNum();i++) {
            DstChoices.add(String.valueOf(i));
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
                alert.setContentText("Please Input your Destination Flooor");
                alert.showAndWait();
            }//End if(DstFloorResult.isPresent()&&DstFloorResult.get()!= null)

        }

    }

    public void onTestBtn(ActionEvent actionEvent) {

    }
}
