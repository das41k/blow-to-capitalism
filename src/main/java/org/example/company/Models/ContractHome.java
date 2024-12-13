package org.example.company.Models;

public class ContractHome extends  Contract{
    private TypeHousing type;
    private double saleOfHome;
    private int ageOfHome;
    private boolean isSystemSecurity;

    public ContractHome() {
        super();
        setSaleOfContract();
    }

    @Override
    public void generateData() {
        double minSale = 5000000;
        double maxSale = 15000000.0;
        saleOfHome = minSale + (maxSale - minSale) * Math.random();
        isSystemSecurity = Math.random() < 0.5;

        int minAge = 1;
        int maxAge = 80;
        ageOfHome = (int) (minAge + (maxAge - minAge) * Math.random());
    }

    @Override
    public void setSaleOfContract() {
        double sale;
        generateData();
        if (saleOfHome <= 10000000) sale = 10000;
        else sale = 15000;

        double p = Math.random();
        if (p <= 0.5) setType(TypeHousing.APARTMENT);
         else if (p <= 0.8) {
             setType(TypeHousing.HOUSE);
             sale *= 1.5;
        }
        else {
            setType(TypeHousing.PENTHOUSE);
            sale *= 3;
        }

        if (isSystemSecurity) sale -= (sale / 100) * 40;

        if (ageOfHome <= 20) sale -= (sale / 100) * 20;
        else if (ageOfHome >= 50) sale += (sale / 100) * 10;

        setSale(sale);
    }

    private void setType(TypeHousing type) {
        this.type = type;
    }
}
