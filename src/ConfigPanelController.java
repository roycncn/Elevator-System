import ElevatorBackend.ElevatorConfiguration;
import ElevatorBackend.ElevatorFactorySetting;
import ElevatorBackend.Simulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jason on 1/12/2016.
 */
public class ConfigPanelController {

    public TextArea ConfigTextField;

    @FXML
    public void initialize() {

        ConfigTextField.setText(readFile(new File(Simulator.getInstance().getElevatConfigConfigPath())));
        ConfigTextField.setEditable(false);

    }


    @SuppressWarnings("Duplicates")
    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text+"\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

}
