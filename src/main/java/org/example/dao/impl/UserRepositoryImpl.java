package org.example.dao.impl;

import org.example.dao.UserRepository;
import org.example.exception.DeletionException;
import org.example.exception.InsertionException;
import org.example.exception.SearchException;
import org.example.exception.UpdateException;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final Connection connection;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM \"User\" WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setDateOfRegistration(resultSet.getDate("date_of_registration").toLocalDate());
            }
        } catch (SQLException e) {
            throw new SearchException("Can't find User");
        }

        return user;
    }

    @Override
    public User getUserById(Long userId) {
        final String query = "SELECT * FROM \"User\" WHERE User_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getLong("User_ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getDate("Date_Of_Registration").toLocalDate()
                );
            }
        } catch (SQLException e) {
            throw new SearchException("Can't find User");
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        final String query = "SELECT * FROM \"User\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("User_ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getDate("Date_Of_Registration").toLocalDate()
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new SearchException("Can't find Users");
        }
        return users;
    }

    @Override
    public void saveUser(User user) {
        final String query = "INSERT INTO \"User\" (Name, Email, Password, Date_Of_Registration) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setDate(4, java.sql.Date.valueOf(user.getDateOfRegistration()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new InsertionException("Can't save User");
        }
    }

    @Override
    public void updateUser(User user) {
        final String query = "UPDATE \"User\" SET Name = ?, Email = ?, Password = ?, Date_Of_Registration = ? WHERE User_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setDate(4, java.sql.Date.valueOf(user.getDateOfRegistration()));
            statement.setLong(5, user.getUserId());
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new UpdateException("User not found");
            }
        } catch (SQLException e) {
            throw new UpdateException("Can't update User");
        }
    }

    @Override
    public void deleteUser(Long userId) {
        final String query = "DELETE FROM \"User\" WHERE User_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new DeletionException("User not found");
            }
        } catch (SQLException e) {
            throw new DeletionException("Can't delete User");
        }
    }
}