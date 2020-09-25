package ba.unsa.etf.rs.tutorijal8;

import java.time.LocalDate;

public class Driver {
    private String name,Prezime,JMB;
    private LocalDate birthday;
    private LocalDate DatumZaposlenja;

    public Driver(String ime, String prezime, String JMB, LocalDate dirthday, LocalDate datumZaposlenja) {
        name = ime;
        Prezime = prezime;
        this.JMB = JMB;
        this.birthday = dirthday;
        DatumZaposlenja = datumZaposlenja;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public String getJMB() {
        return JMB;
    }

    public void setJMB(String JMB) {
        this.JMB = JMB;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getDatumZaposlenja() {
        return DatumZaposlenja;
    }

    public void setDatumZaposlenja(LocalDate datumZaposlenja) {
        DatumZaposlenja = datumZaposlenja;
    }
    @Override
    public String toString() {
        return  "("+name+" "+getPrezime()+" ( "+getJMB()+" )"+")";
    }
}
