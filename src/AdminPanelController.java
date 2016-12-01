import ElevatorBackend.*;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by roycn on 2016/12/1.
 */
public class AdminPanelController {


    public TextArea AdminTextField;

    public void initialize(){

        AdminTextField.setText(readFile(new File(Simulator.getInstance().getAccessConfigPath())));

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

    public void onSaveBtn(ActionEvent actionEvent) throws FileNotFoundException {

        String text = AdminTextField.getText();
        try (PrintStream out = new PrintStream(new FileOutputStream(Simulator.getInstance().getAccessConfigPath()))) {
            out.print(text);
        }
        AccessConfiguration.reload();
        Stage stage = (Stage) AdminTextField.getScene().getWindow();
        stage.close();
    }

    public void onCanelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) AdminTextField.getScene().getWindow();
        stage.close();
    }
}
