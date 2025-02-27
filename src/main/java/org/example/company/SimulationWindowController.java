package org.example.company;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.company.Models.*;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class SimulationWindowController {
    @FXML
    private Label capital = new Label();
    @FXML
    private Label nowMonth = new Label();
    @FXML
    private Label timeSimulation;
    @FXML
    private TableView<RowData> tableView;
    @FXML
    private TableColumn<RowData, String> operationColumn;
    @FXML
    private TableColumn<RowData, String> contractInfoColumn;
    @FXML
    private TableColumn<RowData, Double> amountColumn;
    private ObservableList<RowData> data = FXCollections.observableArrayList();
    @FXML
    private Label demandHome;
    @FXML
    private Label demandCar;
    @FXML
    private Label demandHeal;

    private TermsOfContract termsHome;
    private TermsOfContract termsCar;
    private TermsOfContract termsHp;

    private boolean isSimulationRunning = false;
    private boolean flagMonth = true;
    private long elapsedTime = 0;
    private Timeline simulationTimer;
    private Modeling model;
    private Manager manager;
    private int periodHappen = 10;
    private AnimationTimer timer;
    private final int period = 5;

    private SimulationWindowController simulationWindowController;

    public void initialize() {
        timeSimulation.setText("Время симуляции: " + "00:00:00");
        data.clear();
        operationColumn.setCellValueFactory(cellData -> cellData.getValue().operationProperty());
        contractInfoColumn.setCellValueFactory(cellData -> cellData.getValue().contractInfoProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        tableView.setItems(data);
    }

    private void setDemandWindow() {
        demandHome.setText(String.valueOf(model.getDemandHome()));
        demandCar.setText(String.valueOf(model.getDemandTransport()));
        demandHeal.setText(String.valueOf(model.getDemandHeal()));
    }

    public void countedData(double capitalData, int month, Modeling model, TermsOfContract termsHome, TermsOfContract termsCar, TermsOfContract termsHp, Manager manager) {
        capital.setText(String.valueOf(capitalData));
        nowMonth.setText(String.valueOf(month));
        this.model = model;
        this.termsHome = termsHome;
        this.termsCar = termsCar;
        this.termsHp = termsHp;
        this.manager = manager;
        model.setSimulationWindowController(this);
        setDemandWindow();
    }

    private void SimulationTime() {
        long hours = (elapsedTime / 3600);
        long minutes = (elapsedTime % 3600) / 60;
        long seconds = (elapsedTime % 60);
        String timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeSimulation.setText("Время симуляции: " + timeText);
        setDemandWindow();
    }

    @FXML
    private void startSimulation() {
        if (isSimulationRunning) return;
        data.clear();
        if (flagMonth) model.saleOfMonth();
        capital.setText(String.valueOf(roundToThreeDecimalPlaces(model.getCapital())));
        timer = new AnimationTimer() {
            private long lastUpdateHome= 0;
            private long lastUpdateCar = 0;
            private long lastUpdateHp = 0;
            int cntClientHome = 0;
            int cntClientCar = 0;
            int cntClientHp = 0;
            double pHome = model.getpHappenHome();
            double pCar = model.getpHappenTransport();
            double pHeal = model.getpHappenHeal();
            @Override
            public void handle(long now) {
                if (now - lastUpdateHome >= period * 1_000_000_000L) {
                    if (Math.random() <= pHome && cntClientHome < model.getDemandHome()) {
                        Contract contract = new ContractHome();
                        model.addContact(contract, termsHome);
                        capital.setText(String.valueOf(roundToThreeDecimalPlaces(model.getCapital())));
                        cntClientHome++;
                        model.updateDemand(contract);
                    }
                    lastUpdateHome = now;
                }
                if (now - lastUpdateCar >= period * 1_000_000_000L) {
                    if (Math.random() <= pCar && cntClientCar < model.getDemandTransport()) {
                        Contract contract = new ContractCar();
                        model.addContact(contract, termsCar);
                        capital.setText(String.valueOf(roundToThreeDecimalPlaces(model.getCapital())));
                        cntClientCar++;
                        model.updateDemand(contract);
                    }
                    lastUpdateCar = now;
                }
                if (now - lastUpdateHp >= period * 1_000_000_000L) {
                    if (Math.random() <= pHeal && cntClientHp < model.getDemandHeal()) {
                        Contract contract = new ContractHeal();
                        model.addContact(contract, termsHp);
                        capital.setText(String.valueOf(roundToThreeDecimalPlaces(model.getCapital())));
                        cntClientHp++;
                        model.updateDemand(contract);
                    }
                    lastUpdateHp = now;
                }
            }
        };
        timer.start();
        isSimulationRunning = true;
        startSimulationTimer();
    }

    @FXML
    private void stopSimulation() {
        if (!isSimulationRunning) return;
        flagMonth = false;
        simulationTimer.pause();
        simulationTimer = null;
        timer.stop();
        isSimulationRunning = false;
        System.out.println("Симуляция приостановлена!");
    }

    private void startSimulationTimer() {
        if (!isSimulationRunning) return;
        simulationTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            elapsedTime++;
            SimulationTime();
            if (elapsedTime % periodHappen == 0) {
                timer.stop();
                isSimulationRunning = false;
                model.paymentContracts();
                capital.setText(String.valueOf(roundToThreeDecimalPlaces(model.getCapital())));
                timer.start();
                isSimulationRunning = true;
            }
            if (elapsedTime == 20)  {
                elapsedTime = 0;
                isSimulationRunning = false;
                simulationTimer.stop();
                timer.stop();
                simulationTimer = null;
                model.paymentToState();
                capital.setText(String.valueOf(roundToThreeDecimalPlaces(model.getCapital())));
                model.updateCntContractTypes();
                model.redefiningUpdate();
                resultSimulation();
            }

            // Проверка капитала
            if (model.getCapital() <= 0) {
                timer.stop();
                simulationTimer.stop();
                simulationTimer = null;
                Platform.runLater(() -> {
                    try {
                        stopWorkProgram("Компания обанкротилась!");
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
        }));
        simulationTimer.setCycleCount(Timeline.INDEFINITE);
        simulationTimer.play();
    }

    private void resultSimulation() {
        String paymentToState = String.valueOf(model.getPaymentToState());
        Alert alertResults = new Alert(Alert.AlertType.INFORMATION);
        alertResults.setHeaderText("Результаты симуляции");
        alertResults.setContentText("Выплата государству составила: " + roundToThreeDecimalPlaces(model.getPaymentToState())  +"\n" + "Текущий капитал: " + capital.getText());
        alertResults.setOnCloseRequest(event -> {
            try {
                continuationWork();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        alertResults.show();
    }

    private void stopWorkProgram(String reason) throws SQLException, ClassNotFoundException {
        manager.setStatistic(roundToThreeDecimalPlaces(model.getIncome()), model.getCntSales(), model.getCntPayment());
        if (DatabaseManageres.doesManagerExist(manager.getId())) DatabaseManageres.updateManager(manager.getId(), manager.getAuthData(), manager.getStatistic());
        else DatabaseManageres.addManager(manager.getId(), manager.getAuthData(), manager.getStatistic());
        Alert alertResults = new Alert(Alert.AlertType.INFORMATION);
        alertResults.setHeaderText("Компания завершила работу!");
        alertResults.setContentText("Причина: " + reason);
        alertResults.setOnCloseRequest(event -> {
            Platform.exit(); // Закрыть приложение корректно
            System.exit(0);
        });
        alertResults.showAndWait();
    }

    private void continuationWork() throws SQLException, ClassNotFoundException {
        if (model.getNowMouth() == model.getCntMouth()) {
            String reason = "закончился срок работы, были отработаны все месяцы!";
            stopWorkProgram(reason);
            return;
        }
        model.refreshDemand();
        setDemandWindow();
        if (model.isRedefiningHome() || model.isRedefiningHeal() || model.isRedefiningTransport()
                || model.getDemandHeal() == 0 || model.getDemandHome() == 0 || model.getDemandTransport() == 0 ) {
            Stage stage = (Stage) capital.getScene().getWindow();
            stage.close();
            Stage primaryStage = HelloApplication.getPrimaryStage(); // Получаем ссылку на главное окно
            HelloController mainController = HelloApplication.getMainController();

            // Передаем значения в главное окно
            mainController.updateData(
                    model.getCapital(),
                    model.isRedefiningHome(),
                    model.isRedefiningTransport(),
                    model.isRedefiningHeal(),
                    model.getDemandHome(),
                    model.getDemandTransport(),
                    model.getDemandHeal()
            );
            model.setRedefining();
            primaryStage.show();
        } else {
            int mouth = Integer.parseInt(nowMonth.getText()) + 1;
            nowMonth.setText(String.valueOf(mouth));
            model.setNowMouth(mouth);
            termsHome.setMouthRegister(mouth);
            termsCar.setMouthRegister(mouth);
            termsHp.setMouthRegister(mouth);
        }
    }

    public void addDataTable(String operation, Contract contract, double sum) {
        if (operation.equals("sale")) {
            data.add(new RowData("Продажа", "ID договора: " + contract.getNumber() + " Тип договора: " + contract.getClass().getSimpleName()  + "\nЗаканчивается в " + contract.getTerms().getMouthOfEnd() + " месяце" + "\nТип оплаты: " + contract.getTerms().getType(), roundToThreeDecimalPlaces(sum)));
        } else {
            data.add(new RowData("Выплата страховки", "ID договора: " + contract.getNumber(), roundToThreeDecimalPlaces(sum)));
        }
    }

    public static double roundToThreeDecimalPlaces(double number) {
        double factor = Math.pow(10, 3);
        return Math.round(number * factor) / factor;
    }
}
