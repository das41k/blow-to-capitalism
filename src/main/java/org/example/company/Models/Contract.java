package org.example.company.Models;

public class Contract {
    private int number;
    private TermsOfContract terms;
    private double saleOfContract;
    private ContractType type;

    public Contract(ContractType type) {
        this.type = type;
    }

    public ContractType getType() {
        return type;
    }

    public TermsOfContract getTerms() {
        return terms;
    }

    public void setTerms(TermsOfContract terms) {
        this.terms = terms;
    }

    public void setSaleOfContract(double sale) {
        this.saleOfContract = sale;
    }
}
