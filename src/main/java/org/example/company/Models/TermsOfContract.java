package org.example.company.Models;

import javax.xml.crypto.Data;

public class TermsOfContract {
    private int maxSummConpens;
    private Data validityPeriod;
    private Data dataRegistration;
    private int franchise;
    private ContributionType type;
    public TermsOfContract(int maxSummConpens,Data validityPeriod, Data dataRegistration, int franchise, ContributionType type) {
        this.maxSummConpens = maxSummConpens;
        this.validityPeriod = validityPeriod;
        this.dataRegistration = dataRegistration;
        this.franchise = franchise;
        this.type = type;
    }
}
