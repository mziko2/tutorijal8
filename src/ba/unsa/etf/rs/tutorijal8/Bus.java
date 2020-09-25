package ba.unsa.etf.rs.tutorijal8;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Bus {


    private SimpleStringProperty maker;
    private String serija;
    private SimpleIntegerProperty seatNumber;
    private SimpleObjectProperty<Driver> driverTwo;
    private SimpleObjectProperty<Driver> driverOne;
    private SimpleStringProperty  Jmbg1= new SimpleStringProperty();
    private SimpleStringProperty Jmbg2= new SimpleStringProperty();
    public SimpleStringProperty makerProperty() {
        return maker;
    }
    public String getJmbg1() {
        return Jmbg1.get();
    }

    public SimpleStringProperty jmbg1Property() {
        return Jmbg1;
    }

    public void setJmbg1(String jmbg1) {
        this.Jmbg1.set(jmbg1);
    }

    public String getJmbg2() {
        return Jmbg2.get();
    }

    public SimpleStringProperty jmbg2Property() {
        return Jmbg2;
    }

    public void setJmbg2(String jmbg2) {
        this.Jmbg2.set(jmbg2);
    }
    public void setMaker(String maker) {
        this.maker.set(maker);
    }

    public SimpleIntegerProperty seatNumberProperty() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber.set(seatNumber);
    }

    public SimpleObjectProperty<Driver> driverOneProperty() {
        return driverOne;
    }

    public void setDriverOne(Driver driverOne) {
        this.Jmbg1= new SimpleStringProperty(driverOne.getJMB());
        this.driverOne.set(driverOne);
    }

    public SimpleObjectProperty<Driver> driverTwoProperty() {
        return driverTwo;
    }

    public void setDriverTwo(Driver driverTwo) {
        this.Jmbg2= new SimpleStringProperty(driverTwo.getJMB());
        this.driverTwo.set(driverTwo);
    }

    public Bus(String maker, String serija, int seatNumber, Driver driverOne, Driver driverTwo, String jed,String dva) {
        this.maker = new SimpleStringProperty( maker);
        this.serija = serija;
        this.seatNumber = new SimpleIntegerProperty( seatNumber);
        this.driverOne = new SimpleObjectProperty<>(driverOne);
        this.driverTwo = new SimpleObjectProperty<>(driverTwo);
        this.Jmbg1= new SimpleStringProperty(jed);
        this.Jmbg2= new SimpleStringProperty(dva);
    }


    public Driver getDriverTwo() {
        return driverTwo.get();
    }





    public Driver getDriverOne() {
        return driverOne.get();
    }


    public String getSerija() {
        return serija;
    }

    public void setSerija(String serija) {
        this.serija = serija;
    }


    public int getVozaci() {
        return vozaci;
    }

    public void setVozaci(int vozaci) {
        this.vozaci = vozaci;
    }

    private int vozaci;

    public Bus(String maker, String serija, int brojSjedista, int vozaci) {
        this.maker = new SimpleStringProperty(maker);
        this.serija = serija;
        this.seatNumber = new SimpleIntegerProperty(brojSjedista);
        this.vozaci = vozaci;
    }

    public Bus(String maker, String serija, int brojSjedista) {
        this.maker = new SimpleStringProperty(maker);
        this.serija = serija;
        this.seatNumber = new SimpleIntegerProperty(brojSjedista);    }

    @Override
    public String toString() {
        String s=maker+" "+serija+" ( seats: "+seatNumber+" )";
        if(driverOne!=null) s+=" - "+driverOne;
        if(driverTwo!=null) s+=" - "+driverTwo;
        return s;
    }

    public int getSeatNumber() {
        return seatNumber.get();
    }

    public String getMaker() {
        return  maker.get();
    }
}