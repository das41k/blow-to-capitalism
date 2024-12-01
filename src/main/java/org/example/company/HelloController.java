package org.example.company;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class HelloController {
    @FXML
    private Spinner<Integer> countMounth;
    @FXML
    private Spinner<Double> startCapital;
    @FXML
    private Spinner<Integer> demandHome;
    @FXML
    private Spinner<Integer> demandCar;
    @FXML
    private  Spinner<Integer> demandHp;
    @FXML
    public void initialize() {
        countMounth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(6,24,12));
        startCapital.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10000,100000,30000));
    }

}