package org.example.company.Models;

public class ContractHeal extends Contract {
    private int ageClient;
    private TypeGender type;
    private boolean isDisease;

    public ContractHeal() {
        super();
        setSaleOfContract();
    }

    @Override
    public void generateData() {
        int minAge = 1;
        int maxAge = 80;
        ageClient = (int) (minAge + (maxAge - minAge) * Math.random());
        isDisease = Math.random() < 0.5;
    }

    @Override
    public void setSaleOfContract() {
        double sale;
        generateData();
        if (ageClient < 18) sale = 7500;
        else if (ageClient <= 50) sale = 14000;
        else sale = 8000;

        double p = Math.random();
        if (p < 0.5) {
            setType(TypeGender.FEMALE);
            sale -= (sale/ 100) * 15;
        }

        if (isDisease) sale -= (sale / 100) * 30;

        setSale(sale);
    }

    private void setType(TypeGender type) {
        type = this.type;
    }
}
