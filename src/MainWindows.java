/**
 * Created by roycn on 2016/11/19.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

public class MainWindows extends Application {


    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/MainWindows.fxml"));
        primaryStage.setTitle("***The Best Elevator System***");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent e){

                try {
                    Platform.exit();
                    System.exit(0);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });



    }


}
