package ba.unsa.etf.rpr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static String ispisiGradove(){
        String ispis="";
        ArrayList<Grad> g = GeografijaDAO.getInstance().gradovi();
        for(Grad grad: g){
            ispis += grad.getNaziv()+ " (" + grad.getDrzava().getNaziv() + ") " + " - " +grad.getBrojStanovnika() +"\n";
        }
        return ispis;
    }
    public static void glavniGrad(){
        String drzava;
        System.out.println("Unesi naziv drzave: ");
        Scanner ulaz = new Scanner(System.in);
        drzava = ulaz.next();
        Grad g = GeografijaDAO.getInstance().glavniGrad(drzava);
        if(g != null)
            System.out.println("Glavni grad drzave "+drzava+" je "+g.getNaziv());
        else
            System.out.println("Nepostojeca drzava");
    }
    public static void main(String[] args) {
        /*System.out.println("Gradovi su:\n" + ispisiGradove());
        glavniGrad();*/

        Connection conn = null;

        try {
           /* Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/Users/Adi Pilav/Desktop/sqlitee/baza.db";
            conn = DriverManager.getConnection(url);*/
            glavniGrad();
            ispisiGradove();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
