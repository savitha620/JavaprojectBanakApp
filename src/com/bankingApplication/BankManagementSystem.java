package com.bankingApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BankManagementSystem {
	static Connection connection;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb", "root", "Savitha29@");

            createTable();

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Bank Management System Menu");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Check Balance");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        checkBalance();
                        break;
                }
            } while (choice != 5);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS accounts (accountNumber INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), balance DOUBLE)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    static void createAccount() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account number: ");
           int accountNumber = scanner.nextInt();
            System.out.print("Enter account holder's name: ");
            String name = scanner.next();
            System.out.print("Enter initial balance: ");
            double balance = scanner.nextDouble();
            String query = "INSERT INTO accounts (accountNumber,name, balance) VALUES (?,?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, balance);
            preparedStatement.executeUpdate();
            System.out.println("Account created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void deposit() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            String query = "UPDATE accounts SET balance = balance + ? WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountNumber);
            preparedStatement.executeUpdate();
            System.out.println("Amount deposited successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void withdraw() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            String query = "UPDATE accounts SET balance = balance - ? WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountNumber);
            preparedStatement.executeUpdate();
            System.out.println("Amount withdrawn successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void checkBalance() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            String query = "SELECT balance FROM accounts WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                System.out.println("Account Balance: " + balance);
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



