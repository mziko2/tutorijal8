package ba.unsa.etf.rs.tutorijal8;

public class Bus {
    private String maker, serija;
    private int seatNumber;

    public Bus(String maker, String serija, int seatNumber, Driver driverOne, Driver driverTwo) {
        this.maker = maker;
        this.serija = serija;
        this.seatNumber = seatNumber;
        this.driverOne = driverOne;
        this.driverTwo = driverTwo;
        this.vozaci = vozaci;
    }

    private Driver driverOne;

    public Driver getDriverTwo() {
        return driverTwo;
    }

    public void setDriverTwo(Driver driverTwo) {
        this.driverTwo = driverTwo;
    }

    private Driver driverTwo;
    public String getMaker() {
        return maker;
    }

    public Driver getDriverOne() {
        return driverOne;
    }

    public void setDriverOne(Driver driverOne) {
        this.driverOne = driverOne;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getSerija() {
        return serija;
    }

    public void setSerija(String serija) {
        this.serija = serija;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumberint(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getVozaci() {
        return vozaci;
    }

    public void setVozaci(int vozaci) {
        this.vozaci = vozaci;
    }

    private int vozaci;

    public Bus(String maker, String serija, int brojSjedista, int vozaci) {
        this.maker = maker;
        this.serija = serija;
        this.seatNumber = brojSjedista;
        this.vozaci = vozaci;
    }

    public Bus(String maker, String serija, int brojSjedista) {
        this.maker = maker;
        this.serija = serija;
        this.seatNumber = brojSjedista;
    }

    @Override
    public String toString() {
        String s=maker+" "+serija+" ( seats: "+seatNumber+" )";
        if(driverOne!=null) s+=" - "+driverOne;
        if(driverTwo!=null) s+=" - "+driverTwo;
        return s;
    }

}
