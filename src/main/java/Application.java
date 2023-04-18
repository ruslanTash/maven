import java.sql.*;
public class Application {
    public static void main(String[] args) throws SQLException {

        final String user = "postgres";
        final String password = "8258";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        try (final Connection connection =
                     DriverManager.getConnection(url, user, password)) {
            System.out.println("Соединение установлено!");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
            // Исключение для обработки возможных ошибок при подключении
        }
    }
}
