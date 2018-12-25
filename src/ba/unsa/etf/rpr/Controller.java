package ba.unsa.etf.rpr;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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

    }

    public void gradoviKliknut(MouseEvent mouseEvent) {
        if(!gradovi.getSelectionModel().isEmpty()) {
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
            GeografijaDAO.getInstance().setTrenutnaDrzava(GeografijaDAO.getInstance().getDrzaveOList().get(drzave.getSelectionModel().getSelectedIndex()));
            tf1.setText(GeografijaDAO.getInstance().getTrenutnaDrzava().getNaziv());
            tf2.setText(String.valueOf(GeografijaDAO.getInstance().getTrenutnaDrzava().getNazivGlGrada()));
            tf3.setVisible(false);
            lb3.setVisible(false);
            lb1.setText("Naziv drzave");
            lb2.setText("Naziv glavnog grada");
        }
    }
}
