package com.example.versand;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.shape.Line;

import java.time.LocalDate;

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
    public void initialize() {
        loadSlider();
        loadRdb();
        dtpCom.setValue(LocalDate.now());
    }

    @FXML
    public void onbtnCalc(ActionEvent actionEvent) {

        Toggle insToggle = this.insToggleGroup.getSelectedToggle();
        Toggle delToggle = this.delToggleGroup.getSelectedToggle();

        if (insToggle != null && delToggle != null) {

            String insToggleData = insToggle.getUserData().toString();
            String delToggleData = delToggle.getUserData().toString();

            double preis = 0.0;

            switch (delToggleData) {
                case "Lett" -> preis += 0.6;
                case "Pac" -> preis += 3.2;
                case "BPac" -> preis += 5.5;
                default -> preis += 0;
            }

            if (chckExp.isSelected()) {
                if (!delToggleData.equals("Lett")) preis += 6;
                else preis += 4;
            }
            if (dtpDel.hasProperties()) preis += 0.5;

            if (chckIns.isSelected()) {
                if (!delToggle.equals("Lett")) {
                    switch (insToggleData) {
                        case "100" -> preis += 1.2;
                        case "500" -> preis += 2;
                        case "501" -> preis += (preis / 2);
                    }
                }
            }
            lblAmount.setText(Double.toString(preis));
        }
    }


    @FXML
    public void onbtnSave(ActionEvent actionEvent) {
    }

    @FXML
    public void onbtnLoad(ActionEvent actionEvent) {
    }

    private void loadSlider() {
        sldDisc.setMax(10);
        sldDisc.setMin(0);
        sldDisc.setShowTickMarks(true);
        sldDisc.setShowTickLabels(true);
        sldDisc.setMajorTickUnit(1);
        sldDisc.setMinorTickCount(0);
        sldDisc.setBlockIncrement(1);
    }

    private void loadRdb() {
        rdb100.setToggleGroup(insToggleGroup);
        rdb500.setToggleGroup(insToggleGroup);
        rdbO500.setToggleGroup(insToggleGroup);

        rdb100.setUserData("100");
        rdb500.setUserData("500");
        rdbO500.setUserData("501");

        rdbLett.setToggleGroup(delToggleGroup);
        rdbPac.setToggleGroup(delToggleGroup);
        rdbBPac.setToggleGroup(delToggleGroup);

        rdbLett.setUserData("Lett");
        rdbPac.setUserData("Pac");
        rdbBPac.setUserData("BPac");


        rdb100.setDisable(true);
        rdb500.setDisable(true);
        rdbO500.setDisable(true);
        chckIns.setDisable(true);


        chckIns.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (rdbLett.isSelected()) {

                chckIns.setSelected(false);
            } else {
                rdb100.setDisable(!newValue);
                rdb500.setDisable(!newValue);
                rdbO500.setDisable(!newValue);
            }
        });

        rdbLett.selectedProperty().addListener((observable, oldValue, newValue) -> {
            rdb100.setDisable(true);
            rdb500.setDisable(true);
            rdbO500.setDisable(true);
            chckIns.setDisable(!newValue);
        });


        rdbPac.selectedProperty().addListener((observable, oldValue, newValue) -> {
            chckIns.setDisable(!newValue);
        });
        rdbBPac.selectedProperty().addListener((observable, oldValue, newValue) -> {
            chckIns.setDisable(!newValue);
        });
    }



}