package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Drzava {
    private SimpleStringProperty naziv = new SimpleStringProperty("");
    private SimpleStringProperty nazivGlGrada = new SimpleStringProperty("");
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private Grad glavniGrad;

    public Drzava(){}
    public String getNaziv() {
        return naziv.get();
    }

    public void setNaziv(String naziv) {
        this.naziv.set(naziv);
    }

    public SimpleStringProperty nazivProperty(){ return naziv;}

    public String getNazivGlGrada() {
        return nazivGlGrada.get();
    }

    public void setNazivGlGrada(String naziv) {
        this.nazivGlGrada.set(naziv);
    }

    public SimpleStringProperty nazivGlGradaProperty(){ return nazivGlGrada;}

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }
    public SimpleIntegerProperty idProperty(){return id;}

    public Grad getGlavniGrad() {
        return glavniGrad;
    }

    public void setGlavniGrad(Grad glavniGrad) {
        this.glavniGrad = glavniGrad;
    }

    public String toString(){
        return naziv.get();
    }
}
