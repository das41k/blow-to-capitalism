package org.example.company.Models;

import org.example.company.SimulationWindowController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Modeling {
    private double capital;
    private double startCapital;

    private int demandHome;
    private int demandTransport;
    private int demandHeal;

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

    private double costHome;
    private double costCar;
    private  double costHp;

    private double saleOfHome;
    private double saleOfTransport;
    private double saleOfHeal;

    private boolean isRedefiningHome = false;
    private boolean isRedefiningTransport = false;
    private boolean isRedefiningHeal = false;

    public ArrayList<Contract> contracts = new ArrayList<>();
    public HashMap<Contract, ContractType> contractsMap = new HashMap<>();

    private SimulationWindowController simulationWindowController;

    public double getCapital() {
        return capital;
    }

    public void addContact(ContractType type, TermsOfContract terms) {
        Contract contract = new Contract(type);
        TermsOfContract newTerms = new TermsOfContract(terms);
        contract.setTerms(newTerms);
        contracts.add(contract);
        contractsMap.put(contract,type);
        System.out.println("Добавлен контракт с ID: " + contract.getNumber() + " с типом: " + type + " оканчивается в  " + contract.getTerms().getMouthOfEnd() + " месяце");
        saleContact(contract);
        System.out.println("Контракт с ID: " + contract.getNumber() + " был оплачен в этом месяце");
        givenDataTable("sale", contract, contract.getSaleOfContract());
    }
    public void setCntMouth(int mouth, int nowMouth) {
        this.cntMouth = mouth;
        this.nowMouth = nowMouth;
    }
    public int getCntMouth() {
        return cntMouth;
    }
    public void setNowMouth(int nowMouth) {
        this.nowMouth = nowMouth;
    }

    public int getNowMouth() {
        return nowMouth;
    }

    public void setStartCapital(double startCapital) {
        this.startCapital = startCapital;
        capital += startCapital;
    }

    public void setDemand(int demandHome, int demandTransport, int demandHeal) {
        this.demandHome = demandHome;
        this.demandTransport = demandTransport;
        this.demandHeal = demandHeal;
    }

    public int getDemandHome() {
        return demandHome;
    }

    public int getDemandTransport() {
        return demandTransport;
    }

    public int getDemandHeal() {
        return demandHeal;
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
    public double getPaymentToState() {
        return (capital / 100) * 9;
    }
    public void saleOfMonth() {
        Iterator<Contract> iterator = contracts.iterator();
        while (iterator.hasNext()) {
            Contract contract = iterator.next();
            if (contract.getTerms().getType() == ContributionType.MOUNTH) {
                if (contract.getTerms().getMouthOfEnd() > nowMouth) {
                    saleContact(contract);
                    System.out.println("Контракт с ID: " + contract.getNumber() + " тип оплаты " + contract.getTerms().getType() + " был оплачен в этом месяце");
                    givenDataTable("sale", contract, contract.getSaleOfContract());
                } else if (contract.getTerms().getMouthOfEnd() <= nowMouth) {
                    saleContact(contract);
                    System.out.println("Контракт с ID: " + contract.getNumber() + " тип оплаты " + contract.getTerms().getType() + " полностью выплачен");
                    givenDataTable("sale", contract, contract.getSaleOfContract());
                    iterator.remove();
                    contractsMap.remove(contract);
                }
            }
            else if (contract.getTerms().getType() == ContributionType.QUARTERLY) {
                if (contract.getTerms().getMouthOfEnd() > nowMouth && nowMouth % 3 == 0) {
                    saleContact(contract);
                    System.out.println("Контракт с ID: " + contract.getNumber() + " тип оплаты " + contract.getTerms().getType() + " был оплачен в этом месяце");
                    givenDataTable("sale", contract, contract.getSaleOfContract());
                } else if (contract.getTerms().getMouthOfEnd() <= nowMouth) {
                    saleContact(contract);
                    System.out.println("Контракт с ID: " + contract.getNumber() + " тип оплаты " + contract.getTerms().getType() + " полностью выплачен");
                    givenDataTable("sale", contract, contract.getSaleOfContract());
                    iterator.remove();
                    contractsMap.remove(contract);
                }
            }
            else if (contract.getTerms().getType() == ContributionType.YEAR) {
                if (contract.getTerms().getMouthOfEnd() > nowMouth && nowMouth % 12 == 0) {
                    saleContact(contract);
                    System.out.println("Контракт с ID: " + contract.getNumber() + " тип оплаты " + contract.getTerms().getType() + " был оплачен в этом месяце");
                    givenDataTable("sale", contract,contract.getSaleOfContract());
                } else if (contract.getTerms().getMouthOfEnd() <= nowMouth) {
                    saleContact(contract);
                    System.out.println("Контракт с ID: " + contract.getNumber() + " тип оплаты " + contract.getTerms().getType() + " полностью выплачен");
                    givenDataTable("sale", contract,contract.getSaleOfContract());
                    iterator.remove();
                    contractsMap.remove(contract);
                }
            }
        }
    }

    private void saleContact(Contract contract) {        // Каждый месяц, год и 3 месяца
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
    public void paymentContracts() {
        for (Contract contract : contracts) {
            paymentInsured(contract);
        }
    }

    public void paymentInsured(Contract contract) { // выплаты
        if (isHappen(contract)) {
            double k = Math.random() + Double.MIN_VALUE;
            while (k == 0) k = Math.random() + Double.MIN_VALUE;
            if (k > 1.0) k = 1.0;
            double payment = contract.getTerms().getMaxSummConpens() * k;
            if (contract.getTerms().getFranchise() <= payment) {
                capital -= payment;
                System.out.println("Компания оплатила ущерб по договору: " + contract.getNumber() + " на сумму " + payment);
                givenDataTable("payment", contract,payment);
            }
        }
    }


    public void setSimulationWindowController(SimulationWindowController controller) {
        this.simulationWindowController = controller;
    }

    private void givenDataTable(String operation, Contract contract, double sum) {
        if (simulationWindowController != null) {
            simulationWindowController.addDataTable(operation, contract, sum);
        }
    }

    public void redefiningUpdate() {
        if (cntHome == 0) isRedefiningHome = true;
        if (cntTransport == 0) isRedefiningTransport = true;
        if (cntHeal == 0) isRedefiningHeal = true;
    }

    public void setRedefining() {
        isRedefiningHome = false;
        isRedefiningTransport = false;
        isRedefiningHeal = false;
    }
    public boolean isRedefiningHeal() {
        return isRedefiningHeal;
    }

    public boolean isRedefiningHome() {
        return isRedefiningHome;
    }

    public boolean isRedefiningTransport() {
        return isRedefiningTransport;
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
            }
        }
    }
    public void deleteContracts(Contract contract) {
        contracts.remove(contract);
        contractsMap.remove(contract);
    }

    public boolean isHappen(Contract contract) {
        if (contract.getType() == ContractType.HOME) {
            if (Math.random() <= pHappenHome && cntHappenHome > 0) {
                cntHappenHome--;
                return true;
            }
            return false;
        }
        else if (contract.getType() == ContractType.TRANSPORT) {
            if (Math.random() <= pHappenTransport && cntHappenTransport > 0) {
                cntHappenTransport--;
                return true;
            }
            return false;
        }
        else if (contract.getType() == ContractType.HEALHCARE) {
            if (Math.random() <= pHappenHeal && cntHappenHeal > 0) {
                cntHappenHeal--;
                return true;
            }
            return false;
        }
        return false;
    }

    public void contactsTypesSetCost() {
        for (Contract contract : contracts) {
            if (contract.getType() == ContractType.HOME) {
                double cost = (contract.getSaleOfContract() * contract.getTerms().getPeriod(nowMouth)) / contract.getTerms().getMaxSummConpens();
                costHome += cost;
            } else if (contract.getType() == ContractType.TRANSPORT) {
                double cost = (contract.getSaleOfContract() * contract.getTerms().getPeriod(nowMouth)) / contract.getTerms().getMaxSummConpens();
                costCar += cost;
            } else if (contract.getType() == ContractType.HEALHCARE) {
                double cost = (contract.getSaleOfContract() * contract.getTerms().getPeriod(nowMouth)) / contract.getTerms().getMaxSummConpens();
                costHp += cost;
            }
        }
    }

    public void refreshDemand() {
        contactsTypesSetCost();
        if (cntHome != 0) {
            demandHome = (int) costHome / cntHome;
        }
        if (cntTransport != 0) {
            demandTransport = (int) costCar / cntTransport;
        }
        if (cntHeal != 0) {
            demandHeal = (int) costHp / cntHeal;
        }
    }
}
