package org.example.company;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.company.Models.ContractType;
import org.example.company.Models.Modeling;
import org.example.company.Models.TermsOfContract;

public class SimulationWindowController {
    @FXML
    private Label capital = new Label();
    @FXML
    private Label nowMonth = new Label();
    @FXML
    private Label timeSimulation;

    private TermsOfContract termsHome;
    private TermsOfContract termsCar;
    private TermsOfContract termsHp;

    private boolean isSimulationRunning = false;
    private boolean flagMonth = true;
    private long elapsedTime = 0;
    private Timeline simulationTimer;
    private Modeling model;
    private int periodHappen = 10;
    private AnimationTimer timer;
    private final int period = 5;
    public void initialize() {
        timeSimulation.setText("Время симуляции: " + "00:00:00");
    }

    public void countedData(double capitalData, int month, Modeling model, TermsOfContract termsHome, TermsOfContract termsCar, TermsOfContract termsHp) {
        capital.setText(String.valueOf(capitalData));
        nowMonth.setText(String.valueOf(month));
        this.model = model;
        this.termsHome = termsHome;
        this.termsCar = termsCar;
        this.termsHp = termsHp;
    }
    private void SimulationTime() {
        long hours = (elapsedTime / 3600);
        long minutes = (elapsedTime % 3600) / 60;
        long seconds = (elapsedTime % 60);
        String timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeSimulation.setText("Время симуляции: " + timeText);
    }

    @FXML
    private void startSimulation() {
        if (isSimulationRunning) return;
        if (flagMonth) model.saleOfMonth();
        capital.setText(String.valueOf(model.getCapital()));
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
                        model.addContact(ContractType.HOME, termsHome);
                        capital.setText(String.valueOf(model.getCapital()));
                        cntClientHome++;
                    }
                    lastUpdateHome = now;
                }
                if (now - lastUpdateCar >= period * 1_000_000_000L) {
                    if (Math.random() <= pCar && cntClientCar < model.getDemandTransport()) {
                        model.addContact(ContractType.TRANSPORT, termsCar);
                        capital.setText(String.valueOf(model.getCapital()));
                        cntClientCar++;
                    }
                    lastUpdateCar = now;
                }
                if (now - lastUpdateHp >= period * 1_000_000_000L) {
                    if (Math.random() <= pHeal && cntClientHp < model.getDemandHeal()) {
                        model.addContact(ContractType.HEALHCARE, termsHp);
                        capital.setText(String.valueOf(model.getCapital()));
                        cntClientHp++;
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
                capital.setText(String.valueOf(model.getCapital()));
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
                capital.setText(String.valueOf(model.getCapital()));
                model.updateCntContractTypes();
                model.redefiningUpdate();
                resultSimulation();
            }
        }));
        simulationTimer.setCycleCount(Timeline.INDEFINITE);
        simulationTimer.play();
    }
    private void resultSimulation() {
        String paymentToState = String.valueOf(model.getPaymentToState());
        Alert alertResults = new Alert(Alert.AlertType.INFORMATION);
        alertResults.setHeaderText("Результаты симуляции");
        alertResults.setContentText("Выплата государству составила: " + paymentToState +"\n" + "Текущий капитал: " + capital.getText());
        alertResults.setOnCloseRequest(event -> {
            continuationWork();
        });
        alertResults.show();
    }
    private void continuationWork() {
        if (model.isRedefiningHome() || model.isRedefiningHeal() || model.isRedefiningTransport()) {
            Stage stage = (Stage) capital.getScene().getWindow();
            stage.close();
            Stage primaryStage = HelloApplication.getPrimaryStage(); // Получаем ссылку на главное окно
            HelloController mainController = HelloApplication.getMainController();

            // Передаем значения в главное окно
            mainController.updateData(
                    model.getCapital(),
                    model.isRedefiningHome(),
                    model.isRedefiningTransport(),
                    model.isRedefiningHeal()
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
}
