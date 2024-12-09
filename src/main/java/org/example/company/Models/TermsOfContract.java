package org.example.company.Models;

import javax.xml.crypto.Data;

public class TermsOfContract {
    private int maxSummConpens;
    private int validityPeriod;
    private int mouthRegister;
    private int franchise;
    private ContributionType type;
    public TermsOfContract(int maxSummConpens,int validityPeriod,int mouthRegister ,int franchise, ContributionType type) {
        this.maxSummConpens = maxSummConpens;
        this.validityPeriod = validityPeriod;
        this.mouthRegister = mouthRegister;
        this.franchise = franchise;
        this.type = type;
    }

    public TermsOfContract(TermsOfContract original) {
        this.maxSummConpens = original.maxSummConpens;
        this.validityPeriod = original.validityPeriod;
        this.mouthRegister = original.mouthRegister;
        this.franchise = original.franchise;
        this.type= original.type;
    }

    public int getMaxSummConpens() {
        return maxSummConpens;
    }
    public int getFranchise() {
        return franchise;
    }

    public int getMonthRegister() {
        return mouthRegister;
    }
    public double calculateCost(double monthlyContribution) {
        switch (type) {
            case MOUNTH:
                return monthlyContribution * validityPeriod;
            case QUARTERLY:
                return monthlyContribution * 3 * validityPeriod;
            case YEAR:
                return monthlyContribution * 12 * validityPeriod;
            default:
                return 0;
        }
    }
    public int getMouthOfEnd() {
        return (mouthRegister + validityPeriod);
    }
    public int getPeriod(int nowMonth) {
        return (mouthRegister + validityPeriod) - nowMonth;
    }
    public ContributionType getType() {
        return type;
    }

    public void setMouthRegister(int mouthRegister) {
        this.mouthRegister = mouthRegister;
    }
}
