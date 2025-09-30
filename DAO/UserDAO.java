package BankingSystem.DAO;
import BankingSystem.Entities.User;

public interface UserDAO {
    void addUsers(String name,String username,String pin,double balance);
    void deleteUsers(String username);
    User getUser(String username);
    int getUser_Id(String username);
    void getAllUsers();
    void updateBalance(String username,double balance);

}
