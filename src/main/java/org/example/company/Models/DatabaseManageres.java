package org.example.company.Models;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DatabaseManageres  {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ManagersCompany";
    private static final String USER = "root";
    private static final String PASSWORD = "zx200526";
    private static ArrayList<String> data = new ArrayList<>();

    public static ArrayList<String> getData() {
        return data;
    }

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

    public static boolean doesManagerExist(int id) throws SQLException {
        String sql = "SELECT 1 FROM Managers WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addManager(int id, LocalDateTime authData, String statistic) throws SQLException {
        String sql = "INSERT INTO Managers (id, authData, statistic) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(authData));
            preparedStatement.setString(3, statistic);

            preparedStatement.executeUpdate();
            System.out.println("Новый менеджер успешно добавлен!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateManager(int id, LocalDateTime authData, String statistic) throws SQLException {
        String sql = "UPDATE Managers SET authData = ?, statistic = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(authData));
            preparedStatement.setString(2, statistic);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные менеджера с ID " + id + " успешно обновлены!");
            } else {
                System.out.println("Менеджер с ID " + id + " не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void infoManagersTable() throws ClassNotFoundException, SQLException {
        String selectSQL = "SELECT * FROM Managers";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            System.out.println("Managers Table:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDateTime authData = resultSet.getTimestamp("authData").toLocalDateTime();
                String statistic = resultSet.getString("statistic");
                data.add("ID: " + id + ", AuthData: " + authData + ", Statistic: " + statistic + "\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
