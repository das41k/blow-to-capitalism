package org.example.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.company.Models.ContractType;
import org.example.company.Models.ContributionType;
import org.example.company.Models.Modeling;

import java.io.IOException;

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
    @FXML
    private Spinner<Double> saleContractHome;
    @FXML
    private Spinner<Double> saleContractCar;
    @FXML
    private Spinner<Double> saleContractHp;
    @FXML
    private ComboBox<ContributionType> typeContribHome;
    @FXML
    private ComboBox<ContributionType> typeContribCar;
    @FXML
    private ComboBox<ContributionType> typeContribHp;

    private boolean isSimulation = false;
    private Modeling model = new Modeling();
    private int nowMonth = 0;
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
        typeContribHome.getItems().addAll(ContributionType.values());
        typeContribCar.getItems().addAll(ContributionType.values());
        typeContribHp.getItems().addAll(ContributionType.values());
        typeContribHome.setValue(ContributionType.MOUNTH);
        typeContribCar.setValue(ContributionType.MOUNTH);
        typeContribHp.setValue(ContributionType.MOUNTH);
        saleContractHome.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(100, 10000, 300, 100));
        saleContractCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(100, 15000, 500, 100));
        saleContractHp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(100, 7000, 100, 50));
    }
    private void setDisable(boolean flag) {
        countMounth.setDisable(flag);
        startCapital.setDisable(flag);
        accordionItemHome.setDisable(flag);
        accordionItemCar.setDisable(flag);
        accordionItemHp.setDisable(flag);
    }

    private void CountedData() {
        model.setStartCapital(startCapital.getValue());
        model.setCntMouth(countMounth.getValue());
        model.genetateDataHappen();
        model.setP(pHome.getValue(),pCar.getValue(),pHeal.getValue());
    }

    public void startWorkCompany(ActionEvent actionEvent) throws IOException { // запретить менять поля
        setDisable(true);
        CountedData();
        nowMonth++;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/org/example/company/simulationWindow.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Подождите, компания работает!");
        SimulationWindowController controller = loader.getController();
        controller.countedData(startCapital.getValue(), nowMonth);
        stage.show();
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).hide();
    }
}