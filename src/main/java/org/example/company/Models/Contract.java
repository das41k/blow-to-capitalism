package org.example.company.Models;

public class Contract {
    private static int numbers = 0;
    private final int number;
    private TermsOfContract terms;
    private double saleOfContract;
    private ContractType type;

    public Contract(ContractType type) {
        this.type = type;
        this.number = ++numbers;
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
    public int getNumber() {
        return number;
    }
}
