package com.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


// This is a simple command-line program intended to test access and modification
// of our MySQL database. It performs 3 operations:
// 1. Fetch a single account's username, password, and displayname
// 2. Fetch a list of accounts' username, password, and displayname
// 3. Insert an account into the user database table.

public class matchJdbc implements User {
    private static final String GET_USER_STATEMENT =
            "SELECT username FROM users WHERE username = ?";
    private final Config config;
    public matchJdbc(final Config config) {
        this.config = config;
    }
}

@Override
public User getUser(final String username) {
    Connection connection;
    try {
        connection =
                DriverManager.getConnection(
                        config.getString("mysql.jdbc"),
                        config.getString("mysql.username")
                );
        PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_STATEMENT);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.first()) {
            return new UserBuilder()
                    .uid(resultSet.getInt("uid"))
                    .username(resultSet.getString("username"))
                    .build();
        } else {
            return null;
        }
    } catch (SQLException e) {
        throw new RuntimeException("error fetching user", e);
    }
}
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
            ResultSet Account = fetchOneAcc.executeQuery("SELECT username FROM users WHERE username = ;");
            ResultSet AccountsList = fetchAccounts.executeQuery("SELECT * FROM users;");

            String sql = "INSERT INTO users " + " VALUES ('Davidbrown', 'David', 'dv10642')";

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
