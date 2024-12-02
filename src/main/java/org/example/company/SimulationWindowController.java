package org.example.company;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class SimulationWindowController {
    @FXML
    private Label capital;
    @FXML
    private Label nowMonth;
    @FXML
    private Label timeSimulation;
    @FXML
    public void initialize() {
    }
    public void countedData(double capital, int month) {
        this.capital.setText("");
        this.nowMonth.setText("");
    }
}
