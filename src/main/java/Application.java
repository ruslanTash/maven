import java.sql.*;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {

        final String user = "postgres";
        final String password = "8258";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee JOIN city ON employee.city_id = city.city_id WHERE id = 1")) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Имя: " + resultSet.getString("first_name"));
                System.out.println("Фамилия: " + resultSet.getString("last_name"));
                System.out.println("Id сотрудника: " + resultSet.getInt("id"));
                System.out.println("Пол: " + resultSet.getString("gender"));
                System.out.println("Город проживания: " + resultSet.getString("city_name"));
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
        EmployeeDAO employeeDAO = new EmployeeDaoImpl();
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee employee : employees){
            System.out.println("Employee ID: " + employee.getId());
            System.out.println("first_name: " + employee.getFirst_name());
            System.out.println("last_name: " + employee.getLast_name());
            System.out.println("gender: " + employee.getGender());
            System.out.println("age: " + employee.getAge());
            System.out.println("city_id: " + employee.getCity_id());
        }
    }
}
