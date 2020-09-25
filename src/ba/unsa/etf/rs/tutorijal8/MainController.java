package ba.unsa.etf.rs.tutorijal8;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.print.Book;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class MainController {
    @FXML  private TableView<Bus> tabBus;
    @FXML  private TableView<Driver> tabVoz;
    @FXML private TableColumn<Driver,String>coIme;
    @FXML private TableColumn<Driver,String>coJmbg;
    @FXML private TableColumn<Bus,String>coMarka;
    @FXML private TableColumn<Bus,String>coBrS;
    @FXML private TableColumn<Bus, String>coVozac1;
    @FXML private TableColumn<Bus, String>coVozac2;
    private static TransportDAO dao;
    MainController(TransportDAO model){
        this.dao=model;
    }
    @FXML
    private void initialize(){
        dao.resetDatabase();
        dao.napuni();
        coIme.setCellValueFactory(new PropertyValueFactory<>("name"));
        coJmbg.setCellValueFactory(new PropertyValueFactory<>("JMB"));
        coMarka.setCellValueFactory(new PropertyValueFactory<>("maker"));
        coBrS.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
        coVozac2.setCellValueFactory(new PropertyValueFactory<>("Jmbg2"));
        coVozac1.setCellValueFactory(new PropertyValueFactory<>("Jmbg1"));
        tabBus.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabVoz.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabBus.setItems(dao.getBusses());
        tabVoz.setItems(FXCollections.observableList(dao.getDrivers()));


        final ObservableList<TablePosition> selectedCellsBus = tabBus.getSelectionModel().getSelectedCells();
        selectedCellsBus.addListener((ListChangeListener<TablePosition>) change -> {
            for (TablePosition pos : selectedCellsBus) {
                dao.setCurernB(dao.getBusses().get(pos.getRow()));
                System.out.println("Cell selected in row "+pos.getRow()+" and column "+pos.getTableColumn().getText());
            }
        });
        final ObservableList<TablePosition> selectedCellsV = tabVoz.getSelectionModel().getSelectedCells();
        selectedCellsV.addListener((ListChangeListener<TablePosition>) change -> {
            for (TablePosition pos : selectedCellsV) {
                dao.setCurernV(FXCollections.observableList(dao.getDrivers()).get(pos.getRow()));
                System.out.println("Cell selected in row "+pos.getRow()+" and column "+pos.getTableColumn().getText());
            }
        });
    }


    public void dodajVozaca(ActionEvent actionEvent) {
        VozacControler voz = new VozacControler();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vozac.fxml"));
        loader.setController(voz);
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("DodajVozaca");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        if(voz.unesiVozaca()!=null){try {
            dao.addDriver(voz.unesiVozaca());
        }catch (Exception e){
            Alert a1 = new Alert(Alert.AlertType.NONE,
                    "Taj vozač već postoji!", ButtonType.OK);
            // show the dialog
            a1.show();
        }
        }
        tabVoz.setItems(FXCollections.observableList(dao.getDrivers()));
        tabBus.setItems(dao.getBusses());
    }



    public void dodajAutobus(ActionEvent actionEvent) {
        BusController voz = new BusController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/autobus.fxml"));
        loader.setController(voz);
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("DodajBus");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        if(voz.unesiBus()!=null)
            dao.addBus(voz.unesiBus());

        tabBus.setItems(dao.getBusses());
    }



    public void dodjeliVozaca(ActionEvent actionEvent) {
        if(dao.getCurernV()==null || dao.getCurernV()==null){
            Alert a1 = new Alert(Alert.AlertType.NONE,
                    "Selektujte autobus i vozaca kojeg zelite da dodate tom autobusu", ButtonType.OK);
            // show the dialog
            a1.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Izaberite mjesto zaa vozaca");
            alert.setHeaderText("Izaberite mjesto na koje zelite dodati vozaca");
            ButtonType buttonTypeOne = new ButtonType("Prvo mjesto");
            ButtonType buttonTypeTwo = new ButtonType("Drugo mjesto");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                dao.dodijeliVozacuAutobus(dao.getCurernV(), dao.getCurernB(), 1);
                dao.getCurernB().setDriverOne(dao.getCurernV());
            } else if (result.get() == buttonTypeTwo) {
                dao.dodijeliVozacuAutobus(dao.getCurernV(), dao.getCurernB(), 2);
                dao.getCurernB().setDriverTwo(dao.getCurernV());
            }
            tabBus.setItems(dao.getBusses());
        }
    }

    public void oduzmiVozaca(ActionEvent actionEvent) {
        if(dao.getCurernV()==null) return;
        dao.deleteDriver(dao.getCurernV());
        tabBus.setItems(dao.getBusses());
        tabVoz.setItems(FXCollections.observableList(dao.getDrivers()));
    }

    public void obrisiAutobus(ActionEvent actionEvent) {
        if(dao.getCurernB()==null) return;
        tabBus.getSelectionModel().clearSelection();
        ukloniAutobus(dao.getCurernB());
        tabBus.setItems(dao.getBusses());

    }

    private static void ukloniAutobus(Bus bus) {
        dao.deleteBus(bus);
    }





}