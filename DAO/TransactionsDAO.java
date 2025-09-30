package BankingSystem.DAO;

public interface TransactionsDAO{
    void addTransactions(String type,double amount,String date,int user_id);
    void getTransactions();
    void getUserTransactions(String username);
}
