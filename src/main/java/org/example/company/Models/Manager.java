package org.example.company.Models;

import java.time.LocalDateTime;
import java.util.Map;

public class Manager {
    private int id;
    private LocalDateTime authData;
    private String statistic;

    public Manager(int id, LocalDateTime authData) {
        this.id = id;
        this.authData = authData;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getAuthData() {
        return authData;
    }

    public String getStatistic() {
        return statistic;
    }

    public void setStatistic(double income, Map<String, Integer> cntSales, Map<String, Integer> cntPayments) {
        statistic = "Доход компании: " + income + "\nКоличество проданных справок каждого типа:\n";
        for (String key : cntSales.keySet()) {
            statistic +=  key + " " + cntSales.get(key);
        }
        statistic += "\nКоличество выплат по страховым случаям:\n";
        for (String key : cntPayments.keySet()) {
            statistic +=  key + " " + cntSales.get(key);
        }
    }

}
