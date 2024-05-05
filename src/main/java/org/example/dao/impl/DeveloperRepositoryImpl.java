package org.example.dao.impl;

import org.example.dao.DeveloperRepository;
import org.example.exception.DeletionException;
import org.example.exception.InsertionException;
import org.example.exception.SearchException;
import org.example.exception.UpdateException;
import org.example.model.Developer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {

    private final Connection connection;

    public DeveloperRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Developer getDeveloperById(long developerId) {
        final String query = "SELECT Developer_ID, Title, Website FROM Developer WHERE Developer_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, developerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Developer(
                            resultSet.getLong("Developer_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Website")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SearchException("Developer not found");
        }
        return null;
    }

    @Override
    public List<Developer> getAllDevelopers() {
        List<Developer> developers = new ArrayList<>();
        final String query = "SELECT * FROM Developer";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Developer developer = new Developer(
                        resultSet.getLong("Developer_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Website")
                );
                developers.add(developer);
            }
        } catch (SQLException e) {
            throw new SearchException("Developer not found");
        }
        return developers;
    }

    @Override
    public void saveDeveloper(Developer developer) {
        final String query = "INSERT INTO Developer (Title, Website) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, developer.getTitle());
            statement.setString(2, developer.getWebsite());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new InsertionException("Cant save");
        }
    }

    @Override
    public void updateDeveloper(Developer developer) {
        final String query = "UPDATE Developer SET Title = ?, Website = ? WHERE Developer_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, developer.getTitle());
            statement.setString(2, developer.getWebsite());
            statement.setLong(3, developer.getDeveloperId());
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new UpdateException("Developer not found");
            }
        } catch (SQLException e) {
            throw new UpdateException("Cant update");
        }
    }

    @Override
    public void deleteDeveloper(Long developerId) {
        final String query = "DELETE FROM Developer WHERE Developer_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, developerId);
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new DeletionException("Developer not found");
            }
        } catch (SQLException e) {
            throw new DeletionException("Cant delete");
        }
    }

    @Override
    public List<Developer> findDevelopersByName(String name) {
        List<Developer> developers = new ArrayList<>();
        final String query = "SELECT Developer_ID, Title, Website FROM Developer WHERE Title ILIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Developer developer = new Developer(
                            resultSet.getLong("Developer_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Website")
                    );
                    developers.add(developer);
                }
            }
        } catch (SQLException e) {
            throw new SearchException("Error searching for developer by name", e);
        }
        return developers;

    }
}
