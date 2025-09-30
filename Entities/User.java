package BankingSystem.Entities;

import BankingSystem.DAO.TransactionsDAO_Implementation;

import BankingSystem.DAO.UserDAO_Implementation;

import java.util.ArrayList;

public class User{
   private final String name;
   private final String username;
   private final String pin;
   private double balance;
   private ArrayList<Transactions> transactions;

   public User(String name,String username,String pin,double balance){
       this.name = name;
       this.username = username;
       this.pin = pin;
       this.balance = balance;
       this.transactions = new ArrayList<>();

   }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }
    public void setName(){

    }

    public boolean deposit(double amt, UserDAO_Implementation userDAO, TransactionsDAO_Implementation transDAO){
       if(amt>0){
           balance+=amt;
           userDAO.updateBalance(username,balance);
       }
       else{
           System.out.println("Enter the proper amount sir/ma'am");
           return false;
       }
       transactions.add(new Transactions("Deposited",amt));
       Transactions t=transactions.getLast();
       transDAO.addTransactions("Deposited",amt,t.getDate(),userDAO.getUser_Id(username));
       return  true;
    }
    public void amtAdded(double amt,UserDAO_Implementation userDAO){
       balance+=amt;
       userDAO.updateBalance(username,balance);
   }

    public boolean withdraw(double amt,UserDAO_Implementation userDAO, TransactionsDAO_Implementation transDAO){
       if(balance>=amt){
           balance-=amt;
           transactions.add(new Transactions("Withdrawal",amt));
           Transactions t=transactions.getLast();
           transDAO.addTransactions("Withdrawal",amt,t.getDate(),userDAO.getUser_Id(username));
           return true;
       }
       return false;
    }
    public boolean deduct(double amt, UserDAO_Implementation userDAO){
        if(balance>=amt){
            balance-=amt;
            userDAO.updateBalance(username,balance);
            return true;
        }
        return false;
    }
    public void addTransaction(String type, double amount) {
        transactions.add(new Transactions(type, amount));
    }
    public Transactions getLastTransactions(){
       return transactions.getLast();
    }

    public void showTransactions(){
        System.out.println("Transaction history of Sir/Ma'am "+ name+":");
        for(Transactions t:transactions){
            System.out.println(t.getDate());
            System.out.println("-----------------------------------------------");
            System.out.println("Amount: "+t.getAmount()+" "+t.getType());
            System.out.println("-----------------------------------------------");
        }
    }
}
