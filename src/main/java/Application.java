import java.sql.*;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {

//    ЗАДАНИЕ 1.
//    настроить в классе подключение к созданной ранее базе данных skypro
        final String user = "postgres";
        final String password = "8258";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

//    Получить и вывести в консоль полные данные об одном из работников(имя, фамилия, пол, город) по id.
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


//    ЗАДАНИЕ 2.
//    Проверить корректность работы всех методов в классе Application
        EmployeeDao employeeDAO = new EmployeeDaoImpl();

//    Получение конкретного объекта Employee по id.
        Employee employee1 = employeeDAO.getEmployeeById(1);
        System.out.println(employee1);

//    Получение списка всех объектов Employee из базы.
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee employee : employees) {
            System.out.println("Employee ID: " + employee.getId());
            System.out.println("first_name: " + employee.getFirstName());
            System.out.println("last_name: " + employee.getLastName());
            System.out.println("gender: " + employee.getGender());
            System.out.println("age: " + employee.getAge());
            System.out.println("city_id: " + employee.getCityId());
        }

//    Создание объекта.
        employeeDAO.createEmployee(new Employee(7, "Иванка", "Иванкович", "woman", 19, 2));

//    Изменение конкретного объекта Employee в базе по id.
        employeeDAO.updateEmployee(5);

//    Удаление конкретного объекта Employee из базы по id.
        employeeDAO.deleteEmployee(5);

    }
}
