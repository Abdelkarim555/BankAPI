package bank_API.model;

import jakarta.persistence.ElementCollection;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Account {

    @Id
    private Long id;
    private String name;
    private double balance;
    @ElementCollection
    private List<String> accountHolders;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<String> getAccountHolders() {
        return accountHolders;
    }

    public void setAccountHolders(List<String> accountHolders) {
        this.accountHolders = accountHolders;
    }
}

