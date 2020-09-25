package ba.unsa.etf.rs.tutorijal8;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TransportDAO {
    private static TransportDAO instance;
    private Connection conn;
    private PreparedStatement glavniUpit;
    private ObservableList<Bus> busevi= FXCollections.observableArrayList();
    private ObservableList<Driver> vozaci= FXCollections.observableArrayList();
    private SimpleObjectProperty<Bus> curernB= new SimpleObjectProperty();
    private SimpleObjectProperty curernV= new SimpleObjectProperty();

    public static void setInstance(TransportDAO instance) {
        TransportDAO.instance = instance;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getGlavniUpit() {
        return glavniUpit;
    }

    public void setGlavniUpit(PreparedStatement glavniUpit) {
        this.glavniUpit = glavniUpit;
    }

    public ObservableList<Bus> getBusevi() {
        return busevi;
    }

    public void setBusevi(ObservableList<Bus> busevi) {
        this.busevi = busevi;
    }

    public ObservableList<Driver> getVozaci() {
        return vozaci;
    }

    public void setVozaci(ObservableList<Driver> vozaci) {
        this.vozaci = vozaci;
    }

    public Bus getCurernB() {
        return curernB.get();
    }

    public SimpleObjectProperty<Bus> curernBProperty() {
        return curernB;
    }

    public void setCurernB(Bus curernB) {
        this.curernB.set(curernB);
    }

    public Object getCurernV() {
        return curernV.get();
    }

    public SimpleObjectProperty<Driver> curernVProperty() {
        return curernV;
    }

    public void setCurernV(Driver curernV) {
        this.curernV.set(curernV);
    }

    ;
    public static TransportDAO getInstance() {
        if (instance == null) instance = new TransportDAO();
        return instance;
    }

    private TransportDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            glavniUpit = conn.prepareStatement("SELECT * FROM autobusi,vozaci WHERE autobusi.vozac2=vozaci.jmbg AND autobusi.proizvodjac=?");
        } catch (SQLException e) {
            resetDatabase();
            try {
                glavniUpit = conn.prepareStatement("SELECT * FROM autobusi,vozaci WHERE autobusi.vozac2=vozaci.jmbg AND autobusi.proizvodjac=?");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
    public  void napuni(){
        addDriver(new Driver("Test","Testović","1111111111111", LocalDate.now().minusYears(20),LocalDate.now()));
        addDriver(new Driver("Priprema","Pripremović","2222222222222",LocalDate.now().minusYears(23),LocalDate.now().minusYears(1)));
        addBus(new Bus("Man","Serija",52));
        addBus(new Bus("Mercedes-Benz","Serija",49));
        addBus(new Bus("Iveco","Serija",54));
    }
    public void resetDatabase() {

        Scanner ulaz;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';'){
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addDriver(Driver driver) {
        try {
            PreparedStatement pre = conn.prepareStatement("INSERT INTO vozaci  VALUES (?,?,?,?,?)");
            pre.setString(1, driver.getName());
            pre.setString(2, driver.getPrezime());
            pre.setString(3, driver.getJMB());
            pre.setString(4, driver.getBirthday().toString());
            pre.setString(5, driver.getDatumZaposlenja().toString());
            pre.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException("Taj vozač već postoji!");
        }

    }
    public ArrayList<Driver> getDrivers() {
        ArrayList<Driver> voz = new ArrayList<>();
        Statement st;
        try {
            st=conn.createStatement();
            ResultSet result = st.executeQuery("SELECT * FROM vozaci");
            while(result.next()) {
                voz.add(new Driver(result.getString(1),result.getString(2),result.getString(3),
                        LocalDate.parse(result.getString(4)),LocalDate.parse(result.getString(5))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vozaci= FXCollections.observableArrayList(voz);
        return  voz;
    }
    public void addBus(Bus bus) {
        try {
            PreparedStatement pre= conn.prepareStatement("INSERT INTO autobusi  VALUES (?,?,?,?,?)");
            pre.setString(1,bus.getMaker());
            pre.setString(2,bus.getSerija());
            pre.setInt(3, bus.getSeatNumber());
            pre.executeUpdate();

        } catch (SQLException e) {

        }

    }
    public ObservableList<Bus> getBusses() {

        ArrayList<Bus> autobusi = new ArrayList<>();
        Statement st;
        try {
            st=conn.createStatement();
            ResultSet result = st.executeQuery("SELECT * FROM autobusi");
            while(result.next()) {
                String marka=result.getString(1);
                String serija=result.getString(2);
                int brS=result.getInt(3);
                Driver d1=DajVozaca(result.getString(4));
                Driver d2=DajVozaca(result.getString(5));
                autobusi.add(new Bus(marka,serija,brS, d1,d2,result.getString(4),result.getString(5)));
            }
            System.out.println(autobusi.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  FXCollections.observableArrayList(autobusi);
    }

    public void deleteDriver(Driver driver) {
        try {
            PreparedStatement pre= conn.prepareStatement("DELETE FROM vozaci WHERE ime=?");
            PreparedStatement pre1= conn.prepareStatement(" UPDATE autobusi SET vozac1 = null WHERE vozac1=?");
            PreparedStatement pre2= conn.prepareStatement(" UPDATE autobusi SET vozac2 = null WHERE vozac2=?");
            pre.setString(1,driver.getName());
            pre1.setString(1,driver.getJMB());
            pre2.setString(1,driver.getJMB());
            pre.executeUpdate();
            pre1.executeUpdate();
            pre2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteBus(Bus bus) {
        try {
            PreparedStatement pre= conn.prepareStatement("DELETE FROM autobusi WHERE proizvodjac=?");

            pre.setString(1,bus.getMaker());
            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodijeliVozacuAutobus(Driver driver, Bus bus, int which) {
        try {PreparedStatement pre;
            if(which==1)
                pre = conn.prepareStatement("UPDATE autobusi SET vozac1=? WHERE proizvodjac=?");
            else
                pre = conn.prepareStatement("UPDATE autobusi SET vozac2=? WHERE proizvodjac=?");
            pre.setString(1,driver.getJMB());
            pre.setString(2,bus.getMaker());
            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    Driver DajVozaca(String jmbg){
        Driver dr=null;
        for (Driver d:vozaci ) {
            if(d.getJMB().equals(jmbg)) return d;
        }
        return  dr;
    }
}