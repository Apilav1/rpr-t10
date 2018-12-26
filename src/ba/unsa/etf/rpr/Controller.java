package ba.unsa.etf.rpr;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;

public class Controller {
    @FXML
    TextField tf1, tf2, tf3;
    @FXML
    Button btn;
    @FXML
    TableView<Grad> gradovi;
    @FXML
    TableView<Drzava> drzave;
    @FXML
    Label lb1, lb2, lb3;
    @FXML
    MenuItem save;
    public int trenutniGrad=1, trenutnaDrzava=1;
    public boolean gradIzmjena=false, drzavaIzmjena=false;
    public void initialize(){
        TableColumn naziv = new TableColumn("Naziv grada");
        TableColumn brojStanovnika = new TableColumn("broj stanovnika");
        TableColumn drzava = new TableColumn("Drzava");

        gradovi.getColumns().addAll(naziv, brojStanovnika, drzava);

        gradovi.setItems(GeografijaDAO.getInstance().getGradoviOList());

        naziv.setCellValueFactory(
                new PropertyValueFactory<Grad, String>("naziv")
        );
        brojStanovnika.setCellValueFactory(
                new PropertyValueFactory<Grad, Integer>("brojStanovnika")
        );
        drzava.setCellValueFactory(
                new PropertyValueFactory<Grad, String>("nazivDrzave")
        );

        TableColumn nazivDrzave = new TableColumn("Naziv drzave");
        TableColumn nazivGlavnogGrada = new TableColumn("Naziv Glavnog Grada");

        drzave.getColumns().addAll(nazivDrzave, nazivGlavnogGrada);

        drzave.setItems(GeografijaDAO.getInstance().getDrzaveOList());

        nazivDrzave.setCellValueFactory(
                new PropertyValueFactory<Grad, String>("naziv")
        );
        nazivGlavnogGrada.setCellValueFactory(
                new PropertyValueFactory<Grad, Integer>("nazivGlGrada")
        );
        btn.setOnAction(this::btnKliknut);
    }

    private void btnKliknut(ActionEvent actionEvent) {
            if(gradIzmjena) {
                Grad izmjenjeniGrad = new Grad();
                for (Grad g : GeografijaDAO.getInstance().getGradoviOList())
                    if (g.getId() == trenutniGrad) {
                        g.setNaziv(tf1.getText());
                        g.setBrojStanovnika(Integer.valueOf(tf2.getText()));
                        g.setNazivDrzave(tf3.getText());
                        g.setDrzava(GeografijaDAO.getInstance().nadjiDrzavu(tf3.getText()));
                    }
                izmjenjeniGrad.setId(trenutniGrad);
                izmjenjeniGrad.setNaziv(tf1.getText());
                izmjenjeniGrad.setBrojStanovnika(Integer.valueOf(tf2.getText()));
                izmjenjeniGrad.setNazivDrzave(tf3.getText());
                izmjenjeniGrad.setDrzava(GeografijaDAO.getInstance().nadjiDrzavu(tf3.getText()));
                GeografijaDAO.getInstance().izmijeniGrad(izmjenjeniGrad);//GeografijaDAO.getInstance().getGradoviOList().get(gradovi.getSelectionModel().getSelectedIndex()));
                gradovi.setItems(null);
                gradovi.setItems(GeografijaDAO.getInstance().getGradoviOList());
            }
            else if(drzavaIzmjena){
                Boolean nadjen = false;
                Drzava izmjenjenaDrzava = new Drzava();
                for (Drzava d : GeografijaDAO.getInstance().getDrzaveOList())
                    if (d.getId() == trenutnaDrzava) {
                        d.setNaziv(tf1.getText());
                        for(Grad g: GeografijaDAO.getInstance().gradovi())
                            if(g.getNaziv().equals(tf2.getText())){
                                d.setNazivGlGrada(tf2.getText());
                                d.setGlavniGrad(g);
                                nadjen = true;
                            }
                            if(!nadjen){
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Alert Dialog");
                                alert.setHeaderText("Nepostojeci grad");
                                alert.setContentText("Mozete promijeniti samo na vec postojeci grad!");
                                alert.showAndWait();
                            }
                    }
                izmjenjenaDrzava.setId(trenutniGrad);
                izmjenjenaDrzava.setNaziv(tf1.getText());
                izmjenjenaDrzava.setNazivGlGrada(tf2.getText());
                izmjenjenaDrzava.setGlavniGrad(GeografijaDAO.getInstance().nadjiGrad(tf2.getText()));
                drzave.setItems(null);
                drzave.setItems(GeografijaDAO.getInstance().getDrzaveOList());
            }
    }

    public void gradoviKliknut(MouseEvent mouseEvent) {
        if(!gradovi.getSelectionModel().isEmpty()) {
            trenutniGrad = gradovi.getSelectionModel().getSelectedIndex()+1;
            gradIzmjena = true;
            drzavaIzmjena = false;
            tf3.setVisible(true);
            lb3.setVisible(true);
            GeografijaDAO.getInstance().setTrenutniGrad(GeografijaDAO.getInstance().getGradoviOList().get(gradovi.getSelectionModel().getSelectedIndex()));
            tf1.setText(GeografijaDAO.getInstance().getTrenutniGrad().getNaziv());
            tf2.setText(String.valueOf(GeografijaDAO.getInstance().getTrenutniGrad().getBrojStanovnika()));
            tf3.setText(GeografijaDAO.getInstance().getTrenutniGrad().getNazivDrzave());
            lb1.setText("Naziv grada");
            lb2.setText("Broj stanovnika");
            lb3.setText("Drzava");
        }
    }

    public void drzaveKliknut(MouseEvent mouseEvent) {
        if(!drzave.getSelectionModel().isEmpty()) {
            trenutnaDrzava = drzave.getSelectionModel().getSelectedIndex()+1;
            drzavaIzmjena = true;
            gradIzmjena = false;
            GeografijaDAO.getInstance().setTrenutnaDrzava(GeografijaDAO.getInstance().getDrzaveOList().get(drzave.getSelectionModel().getSelectedIndex()));
            tf1.setText(GeografijaDAO.getInstance().getTrenutnaDrzava().getNaziv());
            tf2.setText(String.valueOf(GeografijaDAO.getInstance().getTrenutnaDrzava().getNazivGlGrada()));
            tf3.setVisible(false);
            lb3.setVisible(false);
            lb1.setText("Naziv drzave");
            lb2.setText("Naziv glavnog grada");
        }
    }

    public void stampajKnjige(ActionEvent actionEvent) {
        try {
            new PrintReport().showReport(GeografijaDAO.getInstance().getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }
}
