import java.sql.*;

public class testMySQL {
    static String dbUser = "root";
    static String dbPass = "password";
    static String dbURL = "jdbc:mysql//localhost:3306/matchdb";


    public static void main(String[] args) throws SQLException {

        Connection conn = null;
        try {
            // Establish connection
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            // Create statement
            Statement fetchAccount = conn.createStatement();
            Statement fetchAccounts = conn.createStatement();
            Statement insertAccount = conn.createStatement();

            // Execute query
            ResultSet Account = fetchAccount.executeQuery("SELECT * FROM users;");
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
            conn.close();
        }
    }
}
