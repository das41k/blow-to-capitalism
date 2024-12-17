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
import org.example.company.Models.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
    private ComboBox<ContributionType> typeContribHome;
    @FXML
    private ComboBox<ContributionType> typeContribCar;
    @FXML
    private ComboBox<ContributionType> typeContribHp;
    @FXML
    private TableView<RowData> tableView;
    @FXML
    private Spinner<Integer> managerID;

    private TermsOfContract termsHome;
    private TermsOfContract termsCar;
    private TermsOfContract termsHp;

    private boolean isSimulation = false;
    private Modeling model = new Modeling();
    private int nowMonth = 0;
    private Manager manager;

    private boolean flagFisrtSimulation = false;

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        countMounth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2,24,12));
        startCapital.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10000,100000,30000, 100));
        demandHome.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,25,15));
        demandCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,25,13));
        demandHp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,25,18));
        pHome.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,0.9, 0.5, 0.1));
        pCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,0.9, 0.4, 0.1));
        pHeal.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,0.9, 0.7, 0.1));
        conpensHome.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10000, 30000, 15000, 250));
        conpensCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(25000, 60000, 30000, 500));
        conpensHp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(7000, 25000, 10000, 250));
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
        managerID.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1));
        DatabaseManageres.initialize();
    }
    private void setDisable(boolean flag) {
        countMounth.setDisable(flag);
        startCapital.setDisable(flag);
        accordionItemHome.setDisable(flag);
        accordionItemCar.setDisable(flag);
        accordionItemHp.setDisable(flag);
        managerID.setDisable(flag);
    }
    public void updateData(double capital, boolean isRedefiningHome, boolean isRedefiningCar, boolean isRedefiningHp, int demandHome, int demandCar, int demandHp) {
        startCapital.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10000,100000,roundToThreeDecimalPlaces(capital), 100));
        accordionItemHome.setDisable(!isRedefiningHome);
        accordionItemCar.setDisable(!isRedefiningCar);
        accordionItemHp.setDisable(!isRedefiningHp);
        this.demandHome.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,25,demandHome));
        this.demandCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,25,demandCar));
        this.demandHp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,25,demandHp));
    }

    private void CountedData() {
        model.setCntMouth(countMounth.getValue(), nowMonth);
        model.genetateDataHappen();
        model.setDemand(demandHome.getValue(), demandCar.getValue(),demandHp.getValue());
        model.setP(pHome.getValue(),pCar.getValue(),pHeal.getValue());
        if (!flagFisrtSimulation) {
            model.initMapCntPayment();
            model.initMapCntSales();
            flagFisrtSimulation = true;
            manager = new Manager(managerID.getValue(), LocalDateTime.now());
            model.setStartCapital(startCapital.getValue());
        }
    }

    public static double roundToThreeDecimalPlaces(double number) {
        double factor = Math.pow(10, 3);
        return Math.round(number * factor) / factor;
    }

    public void startWorkCompany(ActionEvent actionEvent) throws IOException { // запретить менять поля
        setDisable(true);
        nowMonth++;
        CountedData();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/org/example/company/simulationWindow.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Stage simulationStage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Подождите, компания работает!");
        SimulationWindowController controller = loader.getController();
        setTermsOfContracts();
        controller.countedData(startCapital.getValue(), nowMonth, model, termsHome, termsCar, termsHp, manager);
        simulationStage.setOnHidden(event -> {
            Stage primaryStage = HelloApplication.getPrimaryStage();
            primaryStage.show(); // Показать главное окно
        });
        stage.show();
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).hide();
    }
    public void setTermsOfContracts() {
        termsHome = new TermsOfContract(conpensHome.getValue(), periodHome.getValue(),nowMonth, franchiseHome.getValue(),typeContribHome.getValue());
        termsCar = new TermsOfContract(conpensCar.getValue(), periodCar.getValue(),nowMonth, franchiseCar.getValue(),typeContribCar.getValue());
        termsHp = new TermsOfContract(conpensHp.getValue(), periodHp.getValue(), nowMonth, franchiseHp.getValue(),typeContribHp.getValue());
    }

    public void printManagerBD(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        DatabaseManageres.printManagersTable();
    }
}