package BankingSystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionsDAO_Implementation implements TransactionsDAO {
    Connection conn;

    public TransactionsDAO_Implementation(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addTransactions(String type,double amount,String date,int user_id) {
        String sql="INSERT INTO Transactions(type,amount,date,user_id) VALUES (?,?,?,?)";
        try(PreparedStatement stm= conn.prepareStatement(sql)){
            stm.setString(1,type);
            stm.setDouble(2,amount);
            stm.setString(3,date);
            stm.setInt(4,user_id);
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void getTransactions() {

    }

    @Override
    public void getUserTransactions(String username) {

    }
}
