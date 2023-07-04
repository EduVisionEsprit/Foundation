import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testNote extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
Parent root = FXMLLoader.load(getClass().getResource("NoteEtudiant.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Note Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
