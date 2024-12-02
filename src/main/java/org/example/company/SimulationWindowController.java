package org.example.company;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class SimulationWindowController {
    @FXML
    private Label capital = new Label();
    @FXML
    private Label nowMonth = new Label();
    @FXML
    private Label timeSimulation;
    public void countedData(double capitalData, int month) {
        capital.setText(String.valueOf(capitalData));
        nowMonth.setText(String.valueOf(month));
    }
}
