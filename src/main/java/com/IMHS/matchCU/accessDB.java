package com.IMHS.matchCU;

import java.sql.*;

// This is a simple command-line program intended to test access and modification
// of our MySQL database. It performs 3 operations:
// 1. Fetch a single account's username, password, and displayname
// 2. Fetch a list of accounts' username, password, and displayname
// 3. Insert an account into the user database table.

public class accessDB {
    static String dbUser = "root";  // make sure user/password matches mysql user/pass
    static String dbPass = "password";
    static String dbURL = "jdbc:mysql://localhost:3306/matchdb?";


    public static void main(String[] args) throws SQLException {

        Connection matchDBConn = null;
        try {
            // Establish connection
            System.out.println("Attempting to access database...");
            matchDBConn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            // Create statements
            Statement fetchOneAcc = matchDBConn.createStatement();
            Statement fetchAccounts = matchDBConn.createStatement();
            Statement insertAccount = matchDBConn.createStatement();

            // Execute queries
            ResultSet Account = fetchOneAcc.executeQuery("SELECT * FROM users;");
            ResultSet AccountsList = fetchAccounts.executeQuery("SELECT * FROM users;");

            String sql = "insert into users " + " (username, userdisplayname, password)" + " values ('Davidbrown', 'David', 'dv10642')";

            // Process ResultSet object
            System.out.println(Account.getString("username") + ", " + Account.getString("userdisplayname") + ", " + Account.getString("password"));
            while (AccountsList.next()) {
                System.out.println(AccountsList.getString("username") + ", " + AccountsList.getString("userdisplayname") + ", " + AccountsList.getString("password"));
            }

            insertAccount.executeUpdate(sql);
            System.out.println("Insertion complete.");
        }

        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            // Close connection
            matchDBConn.close();
        }
    }
}
