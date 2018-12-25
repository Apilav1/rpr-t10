package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("forma.fxml"));
        //GlavnaController c = new GlavnaController(model);
        //loader.setController(c);
        Parent root = loader.load();
        //primaryStage.setTitle("Biblioteka");
        primaryStage.setTitle("Glavni prozor");
        primaryStage.setScene(new Scene(root, 600, 464));
        primaryStage.show();
        GeografijaDAO.getInstance();

    }
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
        //GeografijaDAO.getInstance();
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
           // for(Grad g: GeografijaDAO.getInstance().gradovi())
           //     System.out.println("---"+g.getNaziv());
           //System.out.println(GeografijaDAO.getInstance().glavniGrad("Engleska"));
           // glavniGrad();
            //GeografijaDAO.getInstance();
           /* Drzava testDrzava = new Drzava();
            Grad test = new Grad();
            testDrzava.setId(7);
            testDrzava.setNaziv("BIH");
            testDrzava.setGlavniGrad(test);
            test.setId(6);
            test.setNaziv("Sarajevo");
            test.setBrojStanovnika(222222222);
            test.setDrzava(testDrzava);
            //GeografijaDAO.getInstance().dodajDrzavu(testDrzava);
            GeografijaDAO.getInstance().izmijeniGrad(test);

           System.out.println(ispisiGradove());*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        launch(args);
    }
}
