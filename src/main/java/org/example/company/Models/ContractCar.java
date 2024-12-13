package org.example.company.Models;

public class ContractCar extends Contract {
    private double saleOfCar;
    private int experienceDriver;
    private boolean isHavePunishes;

    public ContractCar() {
        super();
        setSaleOfContract();
    }

    @Override
    public void generateData() {
        double minSale = 100000.0;
        double maxSale = 10000000.0;
        saleOfCar = minSale + (maxSale - minSale) * Math.random();

        isHavePunishes = Math.random() < 0.5;

        int minExperience = 1;
        int maxExperience = 50;
        experienceDriver = (int) (minExperience + (maxExperience - minExperience) * Math.random());
    }

    @Override
    public void setSaleOfContract() {
        double sale;
        generateData();
        if (saleOfCar <= 500000) sale = 10000;
        else if (saleOfCar >= 1000000 && saleOfCar <= 5000000) sale = 25000;
        else sale = 40000;

        if (isHavePunishes) sale += (sale / 100) * 50;

        if (experienceDriver <= 5) sale += (sale / 100) * 20;
        else if (experienceDriver >= 10 && experienceDriver <= 25) sale -= (sale / 100) * 20;
        else sale -= (sale / 100) * 40;

        setSale(sale);
    }
}
