package org.example.company.Models;
import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseManageres  {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ManagersCompany";
    private static final String USER = "root";
    private static final String PASSWORD = "zx200526";

    public static void initialize() throws ClassNotFoundException, SQLException {
        // Загрузка драйвера JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Успешное соединение с сервером");

        // Создание базы данных
        createDatabase();

        // Создание таблиц
        createTables();
    }

    private static void createDatabase() throws ClassNotFoundException, SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS ManagersCompany";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("База данных успешно создана или уже существует!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables() throws ClassNotFoundException, SQLException {
        String createManagerTableSQL = "CREATE TABLE IF NOT EXISTS Managers (" +
                "id INT PRIMARY KEY, " +
                "authData DATETIME, " +
                "statistic TEXT)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            // Создаем новую таблицу для класса Manager
            statement.executeUpdate(createManagerTableSQL);
            System.out.println("Таблица Managers создана!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertOrUpdateManager(int id, LocalDateTime authData, String statistic) throws ClassNotFoundException, SQLException {
        String selectSQL = "SELECT * FROM Managers WHERE id = ?";
        String insertSQL = "INSERT INTO Managers (id, authData, statistic) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE Managers SET authData = ?, statistic = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
             PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
             PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {

            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Менеджер с таким ID уже существует, обновляем его данные
                updateStatement.setTimestamp(1, java.sql.Timestamp.valueOf(authData));
                updateStatement.setString(2, statistic);
                updateStatement.setInt(3, id);
                updateStatement.executeUpdate();
                System.out.println("Manager updated successfully.");
            } else {
                // Менеджер с таким ID не существует, вставляем нового менеджера
                insertStatement.setInt(1, id);
                insertStatement.setTimestamp(2, java.sql.Timestamp.valueOf(authData));
                insertStatement.setString(3, statistic);
                insertStatement.executeUpdate();
                System.out.println("Manager inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void printManagersTable() throws ClassNotFoundException, SQLException {
        String selectSQL = "SELECT * FROM Managers";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            System.out.println("Managers Table:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDateTime authData = resultSet.getTimestamp("authData").toLocalDateTime();
                String statistic = resultSet.getString("statistic");
                System.out.println("ID: " + id + ", AuthData: " + authData + ", Statistic: " + statistic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
