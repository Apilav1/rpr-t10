package ba.unsa.etf.rpr;

import java.sql.*;

public class GeografijaDAO {
    private static GeografijaDAO single_instance = null;

    private static Connection conn;


        private static void initialize() {
            single_instance = new GeografijaDAO();
        }
        private GeografijaDAO() {
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:C:/Users/Adi Pilav/Desktop/sqlitee/baza.db";
                conn = DriverManager.getConnection(url);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        public static GeografijaDAO getInstance() {
            if (single_instance == null) initialize();
            return single_instance;
        }
        public static void removeInstance() { single_instance = null; }

        public Grad glavniGrad(String drzava){
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery("SELECT g.* from grad g, drzava d\n" +
                        "where d.naziv = "+drzava+"g.drzava = d.id ");
                Grad novi = new Grad();
                novi.setId(result.getInt(1));
                novi.setNaziv(result.getString(2));
                novi.setBrojStanovnika(result.getInt(3));
                return novi;
            }
            catch (Exception e){

            }
            return null;
        }

    }
