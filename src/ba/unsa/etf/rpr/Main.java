package ba.unsa.etf.rpr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*System.out.println("Gradovi su:\n" + ispisiGradove());
        glavniGrad();*/

        Connection conn = null;

        try {
           /* Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/Users/Adi Pilav/Desktop/sqlitee/baza.db";
            conn = DriverManager.getConnection(url);*/
           GeografijaDAO.getInstance();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
