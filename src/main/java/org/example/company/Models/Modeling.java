package org.example.company.Models;

import javax.xml.crypto.Data;
import java.util.ArrayList;

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

    private double saleOfHome;
    private double saleOfTransport;
    private double saleOfHeal;
    public ArrayList<Contract> contracts = new ArrayList<>();
    public ArrayList<Contract> contractsEnd = new ArrayList<>();
    public void addContact(Contract contract) {
        contracts.add(contract);
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
    public void paymentToState() {
        capital -= (capital / 100) * 9;
    }

    public void saleContact(Contract contract) {
        double summ;
        if (contract.getType() == ContractType.HOME) {
            contract.setSaleOfContract(saleOfHome);
            summ = saleOfHome;
        } else if (contract.getType() == ContractType.TRANSPORT) {
            contract.setSaleOfContract(saleOfTransport);
            summ = saleOfTransport;
        } else {
            contract.setSaleOfContract(saleOfHeal);
            summ = saleOfHeal;
        }
        capital +=  summ;
    }
    public void paymentInsured(Contract contract) { // выплаты

    }
    public void redefiningCondition(Contract contract, int maxSummConpens,Data validityPeriod, Data dataRegistration, int franchise, ContributionType type) { // переопределние условий
        TermsOfContract terms = new TermsOfContract(maxSummConpens,validityPeriod,dataRegistration, franchise, type);
        contract.setTerms(terms);
    }
    public void endContractsAdd() { //месяц
        for (Contract contract : contracts) {
            // if () контракт истек
            contractsEnd.add(contract);
        }
    }
}
