package org.example.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

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
    private Spinner<Double> pHome;
    @FXML
    private Spinner<Double> pCar;
    @FXML
    private Spinner<Double> pHeal;
    @FXML
    private AnchorPane accordionItemHome;
    @FXML
    private AnchorPane accordionItemCar;
    @FXML
    private AnchorPane accordionItemHp;
    @FXML
    private Spinner<Integer> conpensHome;
    @FXML
    private Spinner<Integer> franchiseHome;
    @FXML
    private Spinner<Integer> periodHome;
    @FXML
    private Spinner<Integer> conpensCar;
    @FXML
    private Spinner<Integer> franchiseCar;
    @FXML
    private Spinner<Integer> periodCar;
    @FXML
    private Spinner<Integer> conpensHp;
    @FXML
    private Spinner<Integer> franchiseHp;
    @FXML
    private Spinner<Integer> periodHp;

    private boolean isSimulation = false;

    @FXML
    public void initialize() {
        countMounth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(6,24,12));
        startCapital.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10000,100000,30000, 100));
        demandHome.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,25,15));
        demandCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,25,13));
        demandHp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,25,18));
        pHome.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,0.9, 0.5, 0.1));
        pCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,0.9, 0.4, 0.1));
        pHeal.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,0.9, 0.7, 0.1));
        conpensHome.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000, 7000, 5000, 100));
        conpensCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000, 4000, 3000, 100));
        conpensHp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 2500, 1000, 100));
        franchiseHome.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 2000, 1000, 100));
        franchiseCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 3000, 2500, 100));
        franchiseHp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 1000, 650, 100));
        periodHome.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 6));
        periodCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6, 3));
        periodHp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6, 4));
    }

    public void setDisable(boolean flag) {
        countMounth.setDisable(flag);
        startCapital.setDisable(flag);
        accordionItemHome.setDisable(flag);
        accordionItemCar.setDisable(flag);
        accordionItemHp.setDisable(flag);
    }

    // функция считывания данных и передачи в Modeling

    public void startWorkCompany(ActionEvent actionEvent) { // запретить менять поля
        setDisable(true);
    }
}