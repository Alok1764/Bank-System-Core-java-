package BankingSystem.DAO;

import java.sql.*;

import BankingSystem.Entities.User;
public class UserDAO_Implementation implements UserDAO {
    Connection conn;

    public UserDAO_Implementation(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addUsers(String name,String username,String pin,double balance) {
        String sql="INSERT INTO Users(name,username,pin,balance) VALUES(?,?,?,?)";
        try(PreparedStatement st=conn.prepareStatement(sql)){
            st.setString(1,name);
            st.setString(2,username);
            st.setString(3,pin);
            st.setDouble(4,balance);
            st.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    //in del first we need to remove FK then PK,if we remove PK first it will give me an error
    @Override
    public void deleteUsers(String username) {
        String getUserIdSQL = "SELECT user_id FROM Users WHERE username = ?";
        String deleteTransactionsSQL = "DELETE FROM Transactions WHERE user_id = ?";
        String deleteUserSQL = "DELETE FROM Users WHERE username = ?";

        try (
                PreparedStatement getUserStm = conn.prepareStatement(getUserIdSQL);
                PreparedStatement deleteTransactionsStm = conn.prepareStatement(deleteTransactionsSQL);
                PreparedStatement deleteUserStm = conn.prepareStatement(deleteUserSQL)
        ) {
            // get user_id
            getUserStm.setString(1, username);
            ResultSet rs = getUserStm.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");

                // Delete transactions
                deleteTransactionsStm.setInt(1, userId);
                deleteTransactionsStm.executeUpdate();

                // Delete user
                deleteUserStm.setString(1, username);
                deleteUserStm.executeUpdate();
                System.out.println("User removed: "+username);
            } else {
                System.out.println("User not found: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public User getUser(String username) {
        String sql="SELECT * FROM Users WHERE username=(?)";
        try(PreparedStatement stm=conn.prepareStatement(sql)){
            stm.setString(1,username);

            ResultSet rs= stm.executeQuery();
            while(rs.next()){
                String name=rs.getString("name");
                String newusername=rs.getString("username");
                String pin=rs.getString("pin");
                double balance=rs.getDouble("balance");
                User user=new User(name,newusername,pin,balance);
                return user;
            }


        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getUser_Id(String username) {
        String sql="SELECT user_id FROM Users WHERE username= ?";
        try(PreparedStatement stm= conn.prepareStatement(sql)){
            stm.setString(1,username);
            ResultSet rs=stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void getAllUsers(){
    String sql="SELECT user_id,name,username,balance FROM Users";
    try(Statement stm= conn.createStatement()){
        ResultSet rs=stm.executeQuery(sql);
        System.out.printf("%-10s %-15s %-15s %-15s%n", "User_id", "Name", "Username", "Balance");
        System.out.println("-------------------------------------------------------");
        while(rs.next()){
            System.out.printf("%-10d %-15s %-15s %-10.2f%n", rs.getInt("user_id"), rs.getString("name"), rs.getString("username"), rs.getDouble("balance"));
        }
        System.out.println();
        System.out.println();
    }catch(SQLException e){
        e.printStackTrace();
    }
    }



    @Override
    public void updateBalance(String username,double balance){
        String sql="UPDATE Users SET balance= ? WHERE username= ?";
        try(PreparedStatement stm=conn.prepareStatement(sql)){
            stm.setDouble(1,balance);
            stm.setString(2,username);
            stm.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
