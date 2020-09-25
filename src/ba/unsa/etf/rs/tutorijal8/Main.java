package ba.unsa.etf.rs.tutorijal8;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        TransportDAO dao = TransportDAO.getInstance();
        FXMLLoader loader=new FXMLLoader((getClass().getResource("/fxml/tut8.fxml")));
        loader.setController(new MainController(dao));
        Parent root = loader.load();
        primaryStage.setTitle("Tut8");
        primaryStage.setScene(new Scene(root, 750, 500));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }
}