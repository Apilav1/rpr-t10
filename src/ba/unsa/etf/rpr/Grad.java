package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Grad {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty brojStanovnika = new SimpleIntegerProperty(0);
    private SimpleStringProperty naziv = new SimpleStringProperty("");
    private Drzava drzava;
    private SimpleStringProperty nazivDrzave = new SimpleStringProperty("");

    public Grad() {}

    public int getBrojStanovnika() {
        return brojStanovnika.get();
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika.set(brojStanovnika);
    }

    public SimpleIntegerProperty getIdPropery(){ return id;}


    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty(){return id;}


    public String getNaziv() {
        return naziv.get();
    }

    public void setNaziv(String naziv) {
        this.naziv.set(naziv);
    }

    public SimpleStringProperty nazivProperty(){ return naziv;}

    public String getNazivDrzave() {
        return nazivDrzave.get();
    }

    public void setNazivDrzave(String naziv) {
        this.nazivDrzave.set(naziv);
    }

    public SimpleStringProperty nazivDrzaveProperty(){ return nazivDrzave;}

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
        //this.id = drzava.getId();
    }
}
