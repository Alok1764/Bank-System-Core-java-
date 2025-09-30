package BankingSystem.Services;

import BankingSystem.DAO.TransactionsDAO_Implementation;
import BankingSystem.DAO.UserDAO_Implementation;
import BankingSystem.Entities.Transactions;
import BankingSystem.Entities.User;



class AccountManager {
    UserDAO_Implementation userDAO;
    TransactionsDAO_Implementation transactionDAO;

    AccountManager(UserDAO_Implementation userDAO, TransactionsDAO_Implementation transactionsDAO) {
        this.userDAO=userDAO;
        this.transactionDAO= transactionsDAO;
    }

    public void registerUser(String name, String username, String pin, double initialdeposit) {
        User user =userDAO.getUser(username);
        if (user!=null) {
            System.out.println("Already have a Userid.");
            return;
        }
        User newuser = new User(name, username, pin, initialdeposit);
        userDAO.addUsers(name,username,pin,initialdeposit);
        System.out.println(" Your Account is created Successfully!!");
    }


    public User Login(String username, String pin){
        User user=userDAO.getUser(username);
        if (user != null && user.getPin().equals(pin)) {
            System.out.println("Login Successfully ("+user.getName()+")");
            System.out.println();
            return user;
        }
        System.out.println("Invalid Credentials");
        return null;
    }

    public boolean transfer(User sender, String receiverusername, double amount,UserDAO_Implementation userDAO, TransactionsDAO_Implementation transactionsDAO) {
        User receiver=userDAO.getUser(receiverusername);
        if (receiver==null) {
            System.out.println("Receiver username not found, please check the username.");
            return false;
        }
        if (sender.deduct(amount,userDAO)){
            receiver.amtAdded(amount,userDAO);
            sender.addTransaction("Debited->(Transferred to: "+receiver.getName()+")", amount);
            Transactions t1=sender.getLastTransactions();
            transactionsDAO.addTransactions("Debited->(Transferred to: "+receiver.getName()+")",amount,t1.getDate(),userDAO.getUser_Id(sender.getUsername()));

            receiver.addTransaction("Credited->(Transferred by: "+sender.getName()+")", amount);
            Transactions t2=receiver.getLastTransactions();
            transactionsDAO.addTransactions("Credited->(Transferred by: "+sender.getName()+")",amount,t2.getDate(),userDAO.getUser_Id(receiver.getUsername()));


        } else {
            System.out.println("Insufficient Funds.");
            return false;
        }

        return true;
    }



}
