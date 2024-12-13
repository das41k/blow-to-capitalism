package org.example.company.Models;

public abstract class Contract {
    private static int numbers = 0;
    private final int number;
    private TermsOfContract terms;
    private double saleOfContract;

    public Contract() {
        this.number = ++numbers;
    }

    public TermsOfContract getTerms() {
        return terms;
    }

    public abstract void setSaleOfContract();
    public abstract  void generateData();

    public void setSale(double saleOfContract) {
        this.saleOfContract = saleOfContract;
    }
    public void setTerms(TermsOfContract terms) {
        this.terms = terms;
    }
    public double getSaleOfContract() {
        return saleOfContract;
    }

    public int getNumber() {
        return number;
    }
}
