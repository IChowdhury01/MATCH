package com.store;

import com.typesafe.config.Config;
import com.model.User;
import com.model.UserBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchJdbc implements UserStore {

    private static final String GET_USER_STATEMENT =
            "SELECT username FROM users WHERE username = ?";

    private final Config config;

    public MatchJdbc(final Config config) {
        this.config = config;
    }

    // getUser - Finds all info of a user with the specified username in the DB. If there is no user, returns null.
    @Override
    public User getUser(final String username) {
        Connection connection;
        try {
            connection =
                    DriverManager.getConnection(
                            config.getString("mysql.jdbc"),
                            config.getString("mysql.user"),
                            config.getString("mysql.password"));

            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_STATEMENT);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                return new UserBuilder()
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .displayName(resultSet.getString("displayName"))
                        .aboutMe(resultSet.getString("aboutMe"))
//                        .hobbyList(resultSet.getBoolean[]("hobbyList"))
                        .maxTravelDistance(resultSet.getInt("maxTravelDistance"))
                        .longitude(resultSet.getDouble("longitude"))
                        .latitude(resultSet.getDouble("latitude"))
                        .oldFriendCount(resultSet.getInt("oldFriendCount"))
//                        .availableHobbies(resultSet.getString[]("availableHobbies"))
//                        .friendsList(resultSet.getString[]("friendsList"))
                        .build();
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("error fetching user", e);
        }
    }
}

@NewTest
public User newUser(final String[] arg) {
        Connection conn = null;
        Statement stmt = null;
    try {
        Connection conn = DriverManager.getConnection(
            config.getString("mysql.jdbc"),
            config.getString("mysql.user"),
            config.getString("mysql.password));
        System.out.println("Connected to database successfully...");
        System.out.println("Inserting new record into table...");
        stmt = conn.createStatement();                     
        String sqlinsert = "INSERT INTO users (userid, username, userdisplayname, userpassword, userhobbylist, usermaxtraveldistance,userlatitude, userlongitude)" + "VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement psinsert = conn.prepareStatement(sqlinsert,Statement.RETURN_GENERATED_KEYS);
        psinsert.setInt(1,userid);
        psinsert.setString(2,username);
        psinsert.setString(3,userdisplayname);
        psinsert.setString(4,userpassword);
        psinsert.setString(5,userhobbylist);
        psinsert.setString(6,usermaxtraveldistance);
        psinsert.setString(7,userlatitude);
        psinsert.setString(8,userlongitude);
        stmt.executeUpdate(sqlinsert);                     
         }
           catch (SQLException se) {
               se.printStackTrace();
           }
}
                             
