import ElevatorBackend.ElevatorConfiguration;
import ElevatorBackend.ElevatorFactorySetting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * Created by jason on 1/12/2016.
 */
public class ConfigPanelController {
    public TableView ConfigTable;

    @FXML
    public void initialize() {


        ConfigTable.setItems(ProduceData());



    }

    private ObservableList<ElevatorFactorySetting> ProduceData() {
        ObservableList<ElevatorFactorySetting> efList = FXCollections.emptyObservableList();

        for (ElevatorFactorySetting ef: ElevatorConfiguration.getInstance().getAllSettings()
             ) {

            efList.add(ef);

        }


        return efList;


    }

}
