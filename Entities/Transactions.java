package BankingSystem.Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transactions{
    private final String type;
    private final double amount;
    private final String date;

    public Transactions(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    }

    public String getType() {
        return type;
    }

   public double getAmount() {
        return amount;
    }

   public String getDate() {
        return date;
    }

}
