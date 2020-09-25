package ba.unsa.etf.rs.tutorijal8;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BusController {
    @FXML public TextField fldMarka;
    @FXML public Spinner<Integer> spiner;
    @FXML  public TextField fldSerija;
    @FXML  public Button btnOK;

    public void zatvori(ActionEvent actionEvent) {
        fldMarka.setText("");
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.hide();
    }

    public void potvrdi(ActionEvent actionEvent) {
        if(!fldMarka.getText().isEmpty()&&!fldSerija.getText().isEmpty()){
            Stage stage = (Stage) btnOK.getScene().getWindow();
            stage.hide();
        }
    }
    Bus unesiBus(){
        if(fldMarka.getText().isEmpty())
            return null;
        return  new Bus(fldMarka.getText(), fldSerija.getText(),spiner.getValue().intValue());
    }
}