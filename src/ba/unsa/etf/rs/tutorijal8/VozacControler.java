package ba.unsa.etf.rs.tutorijal8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;


public class VozacControler {
    @FXML
    private TextField fldIme;
    @FXML
    private TextField fldPrezime;
    @FXML
    private TextField fldJMBG;
    @FXML
    private DatePicker dRodjena;
    @FXML
    private DatePicker dtZaposlenja;
    @FXML
    private Button btnOK;

    @FXML
    private void intialize(){
        dRodjena.setValue(LocalDate.now());
        dtZaposlenja.setValue(LocalDate.now());
    }
    public void zatvori(ActionEvent actionEvent) {
        fldJMBG.setText("");
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.hide();
    }

    public void potvrdi(ActionEvent actionEvent) {
        if(!fldIme.getText().isEmpty()&&!fldPrezime.getText().isEmpty()&&!fldJMBG.getText().isEmpty()){
            Stage stage = (Stage) btnOK.getScene().getWindow();
            stage.hide();
        }
    }
    Driver unesiVozaca(){
        if(fldJMBG.getText().isEmpty())
            return null;
        return  new Driver(fldIme.getText(),fldPrezime.getText(),fldJMBG.getText(), dRodjena.getValue(),dtZaposlenja.getValue());
    }
}