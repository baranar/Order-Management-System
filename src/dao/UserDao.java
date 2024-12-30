package dao;

import core.DbHelper;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private Connection connection;

    public UserDao() {
        this.connection = DbHelper.getInstance();
    }

    //is user in the Database?
    public User findByLogin(String mail, String password){
        User user = null;
        String query = "SELECT * FROM user WHERE user_mail = ? AND user_password = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = this.match(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    //create a user object to map to information from the database
    public User match(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setUserName(resultSet.getString("user_name"));
        user.setUserMail(resultSet.getString("user_mail"));
        user.setUserPassword(resultSet.getString("user_password"));
        return user;
    }

    //get all users
    public ArrayList<User> findAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM user");
            while (resultSet.next()){
                users.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
