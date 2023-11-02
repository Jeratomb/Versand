package com.example.versand;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class HelloController {

    @FXML
    private TitledPane tpFrom;
    @FXML
    private TextField txtFname;
    @FXML
    private TextField txtFstr;
    @FXML
    private TextField txtFloc;
    @FXML
    private TextField txtFplz;
    @FXML
    private TextField txtFLname;
    @FXML
    private TextField txtFstrNr;
    @FXML
    private TitledPane tpIns;
    @FXML
    private CheckBox chckIns;
    @FXML
    private RadioButton rdb100;
    @FXML
    private RadioButton rdb500;
    @FXML
    private RadioButton rdbO500;
    @FXML
    private TextField txtAmount;
    @FXML
    private TitledPane tpCalc;
    @FXML
    private Button btnCalc;
    @FXML
    private TitledPane tpDel;
    @FXML
    private CheckBox chckExp;
    @FXML
    private RadioButton rdbLett;
    @FXML
    private RadioButton rdbPac;
    @FXML
    private RadioButton rdbBPac;
    @FXML
    private CheckBox chckAlt;
    @FXML
    private DatePicker dtpDel;
    @FXML
    private TitledPane tpTo;
    @FXML
    private TextField txtTname;
    @FXML
    private TextField txtTstr;
    @FXML
    private TextField txtTloc;
    @FXML
    private TextField txtTplz;
    @FXML
    private TextField txtTLname;
    @FXML
    private TextField txtTstrNr;
    @FXML
    private TextField txtDesc;
    @FXML
    private TextField txtID;
    @FXML
    private DatePicker dtpCom;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnLoad;
    @FXML
    private Label lblStatus;
    @FXML
    private Slider sldDisc;
    @FXML
    private Label lblAmount;
    ToggleGroup insToggleGroup = new ToggleGroup();
    ToggleGroup delToggleGroup = new ToggleGroup();
    @FXML
    private TextArea txtAlt;

    private TextField[] data;
    private Toggle[] toggles;
    private CheckBox[] checkBxs;


    @FXML
    public void initialize() {
        data = new TextField[]{
                txtID,
                txtFname,
                txtFLname,
                txtFstr,
                txtFstrNr,
                txtFplz,
                txtFloc,
                txtTname,
                txtTLname,
                txtTstr,
                txtTstrNr,
                txtTplz,
                txtTloc,
                txtDesc
        };
        checkBxs = new CheckBox[]{
                chckExp,
                chckAlt,
                chckIns
        };

        loadSlider();
        loadRdb();
        loadAlt();
        dtpCom.setValue(LocalDate.now());
    }

    @FXML
    public void onbtnCalc(ActionEvent actionEvent) {
        Toggle delToggle = delToggleGroup.getSelectedToggle();
        Toggle insToggle = insToggleGroup.getSelectedToggle();

        DecimalFormat f = new DecimalFormat("0.00");
        String insToggleData = "";
        String delToggleData = "";
        double discount = sldDisc.getValue() / 10;

        if (insToggle != null) {
            insToggleData = insToggle.getUserData().toString();
        }
        if (delToggle != null) {
            delToggleData = delToggle.getUserData().toString();
        }

        double preis = 0.0;
        double insuredAmount = 0.0;


        switch (delToggleData) {
            case "Letter" -> preis += 0.6;
            case "Package" -> preis += 3.2;
            case "BigPackage" -> preis += 5.5;
            default -> preis += 0;
        }

        if (chckExp.isSelected()) {
            if (!delToggleData.equals("Letter")) preis += 6;
            else preis += 4;
        }
        if (dtpDel.hasProperties()) preis += 0.5;

        if (chckIns.isSelected()) {
            if (!delToggleData.equals("Letter")) {
                switch (insToggleData) {
                    case "<100" -> preis += 1.2;
                    case "<500" -> preis += 2;
                    case "Over 500" -> {
                        if (!txtAmount.getText().isEmpty()) {
                            insuredAmount = Double.parseDouble(txtAmount.getText());
                        } else insuredAmount = 0;
                        preis += insuredAmount - (insuredAmount * 0.95);
                    }
                    default -> preis += 0;
                }
            }
        } else preis += 0;
        if (sldDisc.getValue() != 0) preis = preis - (preis * discount);
        String output = f.format(preis);
        lblAmount.setText(output);
    }


    @FXML
    public void onbtnSave(ActionEvent actionEvent) {
        String[] dataString = new String[data.length + 2 + checkBxs.length + 3];
        LocalDate selectedDate = dtpCom.getValue();
        String insuranceAmount;
        String altDate;
        if (dtpDel.getValue() != null) altDate = dtpDel.getValue().toString();
        else altDate = "";
        if (validateFields()) {
            dataString[0] = data[0].getText();
            dataString[1] = selectedDate != null ? selectedDate.toString() : "";


            for (int i = 2, k = 1; i < data.length + 1; i++, k++) {
                dataString[i] = data[k].getText();
            }

            for (int k = data.length + 1, i = 0; i < 3; k++, i++) {
                if (checkBxs[i].isSelected()) {
                    dataString[k] = checkBxs[i].getText() + ": Ja";
                    if (checkBxs[i] == chckAlt) {
                        k = k + 1;
                        dataString[k] = "Ablageort: " + txtAlt.getText().toString();
                    }
                } else dataString[k] = checkBxs[i].getText() + ": Nein";
            }

            if (insToggleGroup.getSelectedToggle().getUserData().toString() == "Over 500")
                insuranceAmount = txtAmount.getText().toString();
            else insuranceAmount = insToggleGroup.getSelectedToggle().getUserData().toString();
            dataString[data.length + checkBxs.length + 2] = "Versicherungshöhe: " + insuranceAmount;
            dataString[data.length + checkBxs.length + 3] = "Paketart: " + delToggleGroup.getSelectedToggle().getUserData().toString();

            dataString[data.length + checkBxs.length + 4] = altDate;

            Data data = new Data();
            System.out.println(dtpDel.getValue().getDayOfWeek());

            if (selectedDate.isBefore(LocalDate.now())) lblStatus.setText("Datum darf nicht in der Vergangenheit liegen!");
            else if (!altDate.isEmpty() && dtpDel.getValue().getDayOfWeek() == DayOfWeek.SUNDAY) lblStatus.setText("Wunschzustellung darf nicht an einem Sonntag sein!");
            else {
                boolean status = data.useData(dataString);
                if(status) lblStatus.setText("Erfolgreich gespeichert");
                else lblStatus.setText("Fehler beim einfügen!");
            }
        } else {
            lblStatus.setText("Fehler: Es sind nicht alle Pflichtfelder ausgefüllt.");
        }
    }

    @FXML
    public void onbtnLoad(ActionEvent actionEvent) {
        String ID = data[0].getText();
        LocalDate selectedDate = dtpCom.getValue();
        String dateString = selectedDate != null ? selectedDate.toString() : "";
        Versandobjekt object = Data.getData(dateString, ID);

        if (object != null) {
            txtID.setText(ID);
            dtpCom.setValue(selectedDate);
            txtFname.setText(object.getFrom().getvName());
            txtFLname.setText(object.getFrom().getlName());
            txtFstr.setText(object.getFrom().getStreet());
            txtFstrNr.setText(object.getFrom().getStreetNr());
            txtFplz.setText(object.getFrom().getPlz());
            txtFloc.setText(object.getFrom().getLoc());
            txtTname.setText(object.getTo().getvName());
            txtTLname.setText(object.getTo().getlName());
            txtTstr.setText(object.getTo().getStreet());
            txtTstrNr.setText(object.getTo().getStreetNr());
            txtTplz.setText(object.getTo().getPlz());
            txtTloc.setText(object.getTo().getLoc());
            txtDesc.setText(object.getDescription());
            chckExp.setSelected(object.getExpress().equalsIgnoreCase("Ja"));
            chckAlt.setSelected(object.getAltLocPlace().equalsIgnoreCase("Ja"));

            if (chckAlt.isSelected()) txtAlt.setText(object.getAltLoc());
            chckIns.setSelected(object.getInsured().equalsIgnoreCase("Ja"));
            if (chckIns.isSelected()) {

                if (object.getInsuranceType().equalsIgnoreCase("<100")) {
                    rdb100.setSelected(true);
                } else if (object.getInsuranceType().equalsIgnoreCase("<500")) {
                    rdb500.setSelected(true);
                } else {
                    rdbO500.setSelected(true);
                    txtAmount.setText(object.getInsuranceType());
                }
            }
            if (object.getAltDelDate() != null) dtpDel.setValue(object.getAltDelDate());
            else dtpDel.setValue(null);
        } else clear();
    }

    private boolean validateFields(){
        for (TextField field : data) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }
   @FXML
    private void clear() {
        txtID.setText("");
        dtpCom.setValue(null);
        txtFname.setText("");
        txtFLname.setText("");
        txtFstr.setText("");
        txtFstrNr.setText("");
        txtFplz.setText("");
        txtFloc.setText("");
        txtTname.setText("");
        txtTLname.setText("");
        txtTstr.setText("");
        txtTstrNr.setText("");
        txtTplz.setText("");
        txtTloc.setText("");
        txtDesc.setText("");
        chckExp.setSelected(false);
        chckAlt.setSelected(false);
        chckIns.setSelected(false);

    }

    @FXML
    private void loadSlider() {
        sldDisc.setMax(10);
        sldDisc.setMin(0);
        sldDisc.setShowTickMarks(true);
        sldDisc.setShowTickLabels(true);
        sldDisc.setMajorTickUnit(1);
        sldDisc.setMinorTickCount(1);
        sldDisc.setBlockIncrement(1);
    }


    @FXML
    private void loadRdb() {
        rdb100.setToggleGroup(insToggleGroup);
        rdb500.setToggleGroup(insToggleGroup);
        rdbO500.setToggleGroup(insToggleGroup);

        rdb100.setUserData("<100");
        rdb500.setUserData("<500");
        rdbO500.setUserData("Over 500");

        rdbLett.setToggleGroup(delToggleGroup);
        rdbPac.setToggleGroup(delToggleGroup);
        rdbBPac.setToggleGroup(delToggleGroup);

        rdbLett.setUserData("Letter");
        rdbPac.setUserData("Package");
        rdbBPac.setUserData("BigPackage");

        rdb100.setDisable(true);
        rdb500.setDisable(true);
        rdbO500.setDisable(true);
        txtAmount.setVisible(false);

        rdbLett.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                rdb100.setDisable(true);
                rdb500.setDisable(true);
                rdbO500.setDisable(true);
                rdb100.setSelected(false);
                rdb500.setSelected(false);
                rdbO500.setSelected(false);
                chckIns.setDisable(true);
                chckIns.setSelected(false);
            } else {
                chckIns.setDisable(false);
            }
        });

        rdbO500.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                txtAmount.setVisible(newValue);
            } else txtAmount.setVisible(false);
        });

        rdbPac.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (chckIns.isSelected()) {
                    rdb100.setDisable(false);
                    rdb500.setDisable(false);
                    rdbO500.setDisable(false);
                }
            }
        });

        rdbBPac.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (chckIns.isSelected()) {
                    rdb100.setDisable(false);
                    rdb500.setDisable(false);
                    rdbO500.setDisable(false);
                }
            }
        });

        chckIns.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                rdb100.setDisable(false);
                rdb500.setDisable(false);
                rdbO500.setDisable(false);
            } else {
                rdb100.setDisable(true);
                rdb500.setDisable(true);
                rdbO500.setDisable(true);
                rdb100.setSelected(false);
                rdb500.setSelected(false);
                rdbO500.setSelected(false);
            }
        });

    }

    @FXML
    private void loadAlt() {
        txtAlt.setDisable(true);
        chckAlt.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                txtAlt.setDisable(false);
            } else txtAlt.setDisable(true);
        });
    }
}