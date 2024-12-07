package org.example.company.Models;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

public class Modeling {
    private double capital;
    private double startCapital;

    private int demandHome;
    private int demandTransport;
    private int demandHeal;

    private int demandNowHome;
    private int demandNowTransport;
    private int demandNowHeal;

    private int cntMouth;
    private int nowMouth;

    private int cntHappenHome;
    private int cntHappenTransport;
    private int cntHappenHeal;

    private double pHappenHome;
    private double pHappenTransport;
    private double pHappenHeal;

    private int cntHome;
    private int cntTransport;
    private int cntHeal;

    private double saleOfHome; // добавить поле для стоимости в месяц//тип оплаты и т.д
    private double saleOfTransport;
    private double saleOfHeal;

    private boolean isRedefiningHome = false;
    private boolean isRedefiningTransport = false;
    private boolean isRedefiningHeal = false;

    public ArrayList<Contract> contracts = new ArrayList<>();
    public HashMap<Contract, ContractType> contractsMap = new HashMap<>();

    public void addContact(ContractType type, TermsOfContract terms) {
        Contract contract = new Contract(type);
        contract.setTerms(terms);
        contracts.add(contract);
        contractsMap.put(contract,type);
        System.out.println("Добавлен контракт с ID: " + contract.getNumber() + " с типом: " + type + " длительность " + contract.getTerms().getMouthOfEnd());
    }
    public void setCntMouth(int mouth) {
        this.cntMouth = mouth;
        nowMouth = mouth;
    }
    public void setNowMouth(int mouth) {
        this.nowMouth = mouth;
    }

    public void setStartCapital(double startCapital) {
        this.startCapital = startCapital;
        capital += startCapital;
    }
    public void setSaleOfContracts(double saleOfHome, double saleOfTransport, double saleOfHeal) {
        this.saleOfHome = saleOfHome;
        this.saleOfTransport = saleOfTransport;
        this.saleOfHeal = saleOfHeal;
    }
    public void setP(double pHome, double pCar, double pHeal) {
        this.pHappenHome = pHome;
        this.pHappenTransport = pCar;
        this.pHappenHeal = pHeal;
    }

    public double getpHappenHeal() {
        return pHappenHeal;
    }
    public double getpHappenHome() {
        return pHappenHome;
    }
    public double getpHappenTransport() {
        return pHappenTransport;
    }

    public void paymentToState() {
        capital -= (capital / 100) * 9;
    }

    public void saleContact(Contract contract) {        // Каждый месяц, год и 3 месяца
        double summ;
        if (contract.getType() == ContractType.HOME) {
            contract.setSaleOfContract(saleOfHome);
            summ = saleOfHome;
        } else if (contract.getType() == ContractType.TRANSPORT) {
            contract.setSaleOfContract(saleOfTransport);
            summ = saleOfTransport;
        } else if (contract.getType() == ContractType.HEALHCARE) {
            contract.setSaleOfContract(saleOfHeal);
            summ = saleOfHeal;
        } else {
            return;
        }
        capital +=  summ;
    }
    public void paymentInsured(Contract contract) { // выплаты
        if (isHappen(contract)) {
            int franchise = contract.getTerms().getFranchise();
            int damage = (int) (Math.random() * contract.getTerms().getMaxSummConpens());
            int payment = damage - franchise;
            capital -= payment;
        }
    }

    public void isRedefiningUpdate() {
        if (cntHome == 0) isRedefiningHome = true;
        if (cntTransport == 0) isRedefiningTransport = true;
        if (cntHeal == 0) isRedefiningHeal = true;
    }

    public TermsOfContract redefiningCondition(int maxSummConpens,int validityPeriod, int mouthRegister, int franchise, ContributionType type) { // переопределние условий
        return new TermsOfContract(maxSummConpens,validityPeriod, mouthRegister, franchise, type);
    }

    public void genetateDataHappen() {
        cntHappenHome = (int) (Math.random() * 25) + 1;
        cntHappenTransport = (int) (Math.random() * 25) + 1;
        cntHappenHeal = (int) (Math.random() * 25) + 1;
    }
    public void updateCntContractTypes() {
        for (Contract key : contractsMap.keySet()) {
            if (contractsMap.get(key) == ContractType.HOME) {
                cntHome++;
            } else if (contractsMap.get(key) == ContractType.TRANSPORT) {
                cntTransport++;
            } else if (contractsMap.get(key) == ContractType.HEALHCARE) {
                cntHeal++;
            } else return;
        }
    }
    public void deleteContracts() {
        for (Contract contract : contracts) {
            if (nowMouth == contract.getTerms().getMouthOfEnd()) {
                contracts.remove(contract);
                contractsMap.remove(contract);
            }
        }
    }

    public boolean isHappen(Contract contract) {
        if (contract.getType() == ContractType.HOME) {
            if (Math.random() < pHappenHome && cntHappenHome > 0) {
                cntHappenHome--;
                return true;
            }
            return false;
        }
        else if (contract.getType() == ContractType.TRANSPORT) {
            if (Math.random() < pHappenTransport && cntHappenTransport > 0) {
                cntHappenTransport--;
                return true;
            }
            return false;
        }
        else {
            if (Math.random() < pHappenHeal && cntHappenHeal > 0) {
                cntHappenHeal--;
                return true;
            }
            return false;
        }
    }
}
