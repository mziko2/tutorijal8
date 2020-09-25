package ba.unsa.etf.rs.tutorijal8;

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
    private ArrayList<Bus> busevi= new ArrayList<>();
    private ArrayList<Driver> vozaci= new ArrayList<>();

    public static TransportDAO getInstance() {
        if (instance == null) instance = new TransportDAO();
        return instance;
    }

    private TransportDAO() {
        try {
            //Class.forName("org.sqlite.JDBC");
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
        vozaci=voz;
        return  voz;
    }

    public void deleteDriver(Driver driver) {
        try {
            PreparedStatement pre= conn.prepareStatement("DELETE FROM vozaci WHERE ime=?");
            pre.setString(1,driver.getName());
            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public ArrayList<Bus> getBusses() {

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
                autobusi.add(new Bus(marka,serija,brS, d1,d2));
            }
            System.out.println(autobusi.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  autobusi;
    }

    private Driver DajVozaca(String jmbg) {
        Driver dr=null;
        for (Driver d:vozaci ) {
            if(d.getJMB().equals(jmbg)) return d;
        }
        return  dr;
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

    public void addBus(Bus bus) {
        try {
            PreparedStatement pre= conn.prepareStatement("INSERT INTO autobusi  VALUES (?,?,?,?,?)");
            pre.setString(1,bus.getMaker());
            pre.setString(2,bus.getSerija());
            pre.setInt(3, bus.getSeatNumber());
            pre.executeUpdate();

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
    }
}
