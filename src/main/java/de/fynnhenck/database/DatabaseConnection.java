package de.fynnhenck.database;

import de.fynnhenck.util.Chat;
import de.fynnhenck.util.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private static String database = "ntb";
    private static String host = "localhost";
    private static String username = "root";
    private static String password = "If,dm100Pus!"; //TODO: CONFIG FILE
    private static String connectionUrl = "jdbc:mysql://" + host + ":3306/" + database;

    static Connection conn = null;


    public User loginUser(String user, String pass){
        openConnection();
        String sql = "SELECT id,role_id FROM users WHERE user=? AND pass=? AND active=1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                //if in here, user has been found
                User resUser = new User(rs.getInt("id"), user, rs.getInt("role_id"));
                closeConnection();
                return resUser;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return null;
    }

    public List<Chat> getActiveChats(User user){
        openConnection();
        ArrayList<Chat> result = new ArrayList<>();

        String sql = "SELECT * FROM chat_users WHERE user_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                result.add(new Chat(rs.getInt("chat_id"),
                        rs.getString("chat_user"),
                        rs.getString("chat_pass")));
            }

            closeConnection();
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    private void openConnection(){
        try {
            conn = DriverManager.getConnection(connectionUrl, username, password);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
