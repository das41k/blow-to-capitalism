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
}
