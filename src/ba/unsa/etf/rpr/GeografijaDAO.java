package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.ArrayList;

public class GeografijaDAO {
    private ArrayList<Grad> gradovi;
    private ArrayList<Drzava> drzave;
    private static GeografijaDAO single_instance = null;
    public boolean gradExists=false, drzavaExists=false;
    private static Connection conn;


        private static void initialize() {
            single_instance = new GeografijaDAO();
        }
        private GeografijaDAO() {
            gradovi = new ArrayList<>();
            drzave = new ArrayList<>();
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:C:/Users/Adi Pilav/Desktop/sqlitee/baza.db";
                conn = DriverManager.getConnection(url);
                DatabaseMetaData dbm = conn.getMetaData();
                ResultSet tables = dbm.getTables(null, null, "drzava", null);
                if (tables.next())
                    drzavaExists = true;
                tables = dbm.getTables(null, null, "grad", null);
                if(tables.next())
                    gradExists = true;

                if(!gradExists){
                    String kreiranjeTabeleGrad = "CREATE TABLE grad(id INTEGER, naziv text, broj_stanovnika INTEGER, drzava INTEGER,PRIMARY KEY(id), FOREIGN KEY(drzava) REFERENCES Drzava(id);";
                    PreparedStatement ps = conn.prepareStatement(kreiranjeTabeleGrad);
                    ps.execute();
                }
                if(!drzavaExists){
                    String kreiranjeTabeleGrad = "CREATE TABLE drzava(id INTEGER, naziv text, gravni_grad INTEGER,PRIMARY KEY(id), FOREIGN KEY(glavni_grad) REFERENCES grad(id);";
                    PreparedStatement ps = conn.prepareStatement(kreiranjeTabeleGrad);
                    ps.execute();
                }
                if(!gradExists && !drzavaExists){
                    //pariz
                    Grad Pariz = new Grad();
                    Drzava Francuska = new Drzava();
                    Francuska.setNaziv("Francuska");
                    Francuska.setGlavniGrad(Pariz);
                    Francuska.setId(1);
                    Pariz.setId(1);
                    Pariz.setNaziv("Pariz");
                    Pariz.setDrzava(Francuska);
                    Pariz.setBrojStanovnika(321231213);
                    gradovi.add(Pariz);
                    drzave.add(Francuska);
                    dodajGrad(Pariz);
                    dodajDrzavu(Francuska);
                    //london
                    Grad London = new Grad();
                    Drzava Engleska = new Drzava();
                    Engleska.setId(2);
                    Engleska.setGlavniGrad(London);
                    Engleska.setNaziv("Engleska");
                    London.setDrzava(Engleska);
                    London.setNaziv("London");
                    London.setId(2);
                    London.setBrojStanovnika(65432132);
                    gradovi.add(London);
                    drzave.add(Engleska);
                    dodajGrad(London);
                    dodajDrzavu(Engleska);
                    //Beč
                    Grad Bec = new Grad();
                    Drzava Austrija = new Drzava();
                    Austrija.setId(3);
                    Austrija.setNaziv("Austrija");
                    Austrija.setGlavniGrad(Bec);
                    Bec.setDrzava(Austrija);
                    Bec.setNaziv("Bec");
                    Bec.setBrojStanovnika(56423100);
                    Bec.setId(3);
                    gradovi.add(Bec);
                    drzave.add(Austrija);
                    dodajGrad(Bec);
                    dodajDrzavu(Austrija);
                    //Manchester
                    Grad Manchester = new Grad();
                    Manchester.setId(4);
                    Manchester.setBrojStanovnika(65489723);
                    Manchester.setNaziv("Manchester");
                    Manchester.setDrzava(Engleska);
                    gradovi.add(Manchester);
                    dodajGrad(Manchester);
                    //Graz
                    Grad Graz = new Grad();
                    Graz.setDrzava(Austrija);
                    Graz.setNaziv("Graz");
                    Graz.setId(5);
                    Graz.setBrojStanovnika(6587987);
                    gradovi.add(Graz);
                    dodajGrad(Graz);
                }

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
            Grad grad = null;
            try {
                String query = "SELECT glavni_grad, id from drzava where naziv = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, drzava);
                ResultSet s = ps.executeQuery();
                if(s.next()){
                    int id_drzave = s.getInt(2);
                    int id_grad = s.getInt(1);
                    query = "SELECT naziv, broj_stanovnika, drzava from grad where id = ?";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, id_grad);
                    ResultSet s2 = ps.executeQuery();
                    while(s2.next()){
                        grad = new Grad();
                        grad.setId(id_grad);
                        grad.setNaziv(s2.getString(1));
                        grad.setBrojStanovnika(s2.getInt(2));
                        query = "SELECT id, naziv, glavni_grad from drzava where id = ?";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, s2.getInt(3));
                        ResultSet s3 = ps.executeQuery();
                        Drzava temp = new Drzava();
                        temp.setId(s3.getInt(1));
                        temp.setNaziv(s3.getString(2));
                        temp.setGlavniGrad(grad);
                        grad.setDrzava(temp);
                    }
                    return grad;
                }
            }
            catch (Exception e){
                System.out.println("Greska u glavniGrad(String drzava): "+e);
            }
            return null;
        }
        void obrisiDrzavu(String drzava){
            try {
                String query = "SELECT id from drzava where naziv = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, drzava);
                ResultSet s = ps.executeQuery();
                if(s.next()){
                    int id_drzave = s.getInt(1);
                    query = "delete from drzava where id =?";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, id_drzave);
                    ps.executeUpdate();

                    query = "delete from grad where drzava = ?";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, id_drzave);
                    ps.execute();
                }
            }
            catch (Exception e){
                System.out.println("Greska u obrisiDrzavu(String drzava): "+e);
            }
        }

         ArrayList<Grad> gradovi(){
            ArrayList<Grad> g = new ArrayList<>();
             try {
                 String query = "SELECT naziv, broj_stanovnika from grad order by broj_stanovnika desc";
                 PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet s = ps.executeQuery();
                 while(s.next()){
                    Grad temp = new Grad();
                    temp.setNaziv(s.getString(1));
                    temp.setBrojStanovnika(2);
                    g.add(temp);
                 }
             }
             catch (Exception e){
                 System.out.println("Greska u ArrayList<Grad> gradovi(): "+e);
             }
            return g;
         }

         void dodajGrad(Grad grad){
             try {
                 String query = "INSERT into grad(id, naziv, broj_stanovnika, drzava) values (?,?,?,?)";
                 PreparedStatement ps = conn.prepareStatement(query);
                 ps.setInt(1, grad.getId());
                 ps.setString(2, grad.getNaziv());
                 ps.setInt(3, grad.getBrojStanovnika());
                 ps.setInt(4, grad.getDrzava().getId());
                 ps.execute();
             }
             catch (Exception e){
                 System.out.println("Greska u dodajGrad(Grad grad): "+e);
             }
         }
         void dodajDrzavu(Drzava drzava){
            try {
                String query = "INSERT into drzava(id, naziv, glavni_grad) values (?,?,?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, drzava.getId());
                ps.setString(2, drzava.getNaziv());
                ps.setInt(3, drzava.getGlavniGrad().getId());
                ps.execute();
            }
            catch (Exception e){
                System.out.println("Greska u dodajGrad(Grad grad): "+e);
            }
        }
         void izmijeniGrad(Grad grad){
             try {
                 String query = "UPDATE grad set naziv=?, broj_stanovnika=?, drzava=?";
                 PreparedStatement ps = conn.prepareStatement(query);
                 ps.setString(1, grad.getNaziv());
                 ps.setInt(2, grad.getBrojStanovnika());
                 ps.setInt(3, grad.getDrzava().getId());
                 ps.executeUpdate();
             }
             catch (Exception e){
                 System.out.println("Greska u izmijeniGrad(Grad grad): "+e);
             }
         }
         Drzava nadjiDrzavu(String drzava){
            Drzava d;
             try {
                 String query = "SELECT id, naziv, glavni_grad from drzava where naziv = ?";
                 PreparedStatement ps = conn.prepareStatement(query);
                 ps.setString(1, drzava);
                 ResultSet s = ps.executeQuery();
                // System.out.println(s);
                 if(s.next()) {
                     d = new Drzava();
                     while (s.next()) {
                         d.setId(s.getInt(1));
                         d.setNaziv(s.getString(2));
                         query = "SELECT id, naziv, broj_stanovnika, drzava from grad where drzava = ?";
                         ps = conn.prepareStatement(query);
                         ps.setInt(1, s.getInt(1));
                         ResultSet s3 = ps.executeQuery();
                         Grad tempGrad = new Grad();
                         tempGrad.setId(s3.getInt(1));
                         tempGrad.setNaziv(s3.getString(2));
                         tempGrad.setBrojStanovnika(s3.getInt(3));
                         tempGrad.setDrzava(d);
                         d.setGlavniGrad(tempGrad);
                     }
                     return d;
                 }
                 else{
                         return null;
                     }
             }
             catch (Exception e){
                 System.out.println("Greska u glavniGrad(String drzava): "+e);
             }
             return null;
         }
    }
