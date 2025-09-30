package BankingSystem.Services;


import BankingSystem.DAO.TransactionsDAO_Implementation;
import BankingSystem.DAO.UserDAO_Implementation;
import BankingSystem.DB_Config.DataBaseConfig;
import BankingSystem.Entities.User;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BankSystem {
    public static void handleRegistration(Scanner sc, AccountManager manager){
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Choose a username: ");
        String username = sc.nextLine();
        System.out.print("Set your password: ");
        String pin = sc.nextLine();
        System.out.print("Initial deposit: ");
        double deposit = sc.nextDouble();
        sc.nextLine();
        manager.registerUser(name,username,pin,deposit);
    }

    public static void handleLogin(Scanner sc,AccountManager manager,UserDAO_Implementation userDAO,TransactionsDAO_Implementation transactionsDAO){
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String pin = sc.nextLine();

         User you=manager.Login(username,pin);
         if(you!=null) loginMenu(you,sc,manager,userDAO,transactionsDAO);

    }

    public static void loginMenu(User you,Scanner sc,AccountManager manager,UserDAO_Implementation userDAO,TransactionsDAO_Implementation transactionsDAO){
        while(true){
            System.out.println("------ Account Menu ------");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice=sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1:
                    System.out.println("Account balance ₹"+you.getBalance());
                    System.out.println();
                    break;
                case 2:
                    System.out.print("Enter the amount to deposit: ");
                    double amount=sc.nextDouble();
                    sc.nextLine();
                    if(you.deposit(amount,userDAO,transactionsDAO)) System.out.println("your amount ₹"+amount+" is deposited to your account.");
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Enter your amount ot withdraw: ");
                    amount=sc.nextDouble();
                    sc.nextLine();
                    if(you.withdraw(amount,userDAO,transactionsDAO)) System.out.println("Amount is withdrawn from your account please check your transaction");
                    else System.out.println("Sorry you don't have the required amount.");
                    System.out.println();
                    break;
                case 4:
                    System.out.print("Enter the receiver's username: ");
                    String receiverusername=sc.nextLine();
                    System.out.print("Enter the amount: ");
                    amount=sc.nextDouble();
                    sc.nextLine();
                    if(amount>0 && manager.transfer(you,receiverusername,amount,userDAO,transactionsDAO)) System.out.println("Transaction Successful!");
                    else System.out.println("Re-choice and Enter the amount properly.");
                    System.out.println();
                    break;
                case 5:
                    you.showTransactions();
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option choose properly.");


            }
        }


    }
    public static void landingPage(Scanner sc,AccountManager manager,UserDAO_Implementation userDAO,TransactionsDAO_Implementation transactionsDAO){

        while(true){
            System.out.println();
            System.out.println("========== Welcome to Bank Management System ==========");
            System.out.println();
            System.out.println("1. Register -->(If you don't have and account)");
            System.out.println("2. Login -->(If you have an account in this Bank)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice=sc.nextInt();
            sc.nextLine();


            switch (choice) {
                case 1:
                    handleRegistration(sc, manager);
                    System.out.println();
                    break;
                case 2:
                    handleLogin(sc, manager,userDAO,transactionsDAO);
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println();
            }
        }


    }
    public static void administration(Scanner sc,UserDAO_Implementation userDAO){
        System.out.println();
        while(true){
            System.out.println("=====Administrator Panel=====");
            System.out.println();
            System.out.println("1. View Users");
            System.out.println("2. View Transactions");
            System.out.println("3. Remove User");
            System.out.println("4. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            int choice=sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1: userDAO.getAllUsers();
                    break;
                case 3:
                    System.out.print("Enter the username to delete from BANK_DB: ");
                    String username= sc.nextLine();
                    userDAO.deleteUsers(username);
                    break;
                case 4:
                    System.out.println("Exiting as an Administrator.");
                    return;
//                case 2:viewTransactions();
//                break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println();


            }

        }

    }


    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        UserDAO_Implementation userDAO;
        TransactionsDAO_Implementation transactionsDAO;
        Connection conn=null;
        try{
            conn= DataBaseConfig.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }
        if(conn!=null) {
             userDAO = new UserDAO_Implementation(conn);
             transactionsDAO = new TransactionsDAO_Implementation(conn);
        }else{
            System.out.println("Failed to established DATABASE Connection");
            sc.close();
            return;
        }
        AccountManager manager=new AccountManager(userDAO,transactionsDAO);
        System.out.println();
        while(true){
            System.out.println("========== Welcome to Bank ==========");
            System.out.println();
            System.out.println("1. SignIn as a Administrator.");
            System.out.println("2. Register/Login (User)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice=sc.nextInt();
            sc.nextLine();


            switch (choice) {
                case 1:
                    administration(sc,userDAO);
                    System.out.println();
                    break;
                case 2:
                    landingPage(sc, manager,userDAO,transactionsDAO);
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println();
            }
        }


    }
}
