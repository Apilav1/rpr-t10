package ba.unsa.etf.rpr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GeografijaDAO {
    private static GeografijaDAO geografija = null;

    private static Connection conn;

    public GeografijaDAO() {
        conn = null;

        try {
            String url = "jdbc:sqlite:resources/baza.db";
            conn = DriverManager.getConnection(url);
            //generirajBazu();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



}
