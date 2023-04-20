import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class EmployeeDaoImpl implements EmployeeDao {

    private static Connection getConnection() throws SQLException {
        final String user = "postgres";
        final String password = "your_password";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        return DriverManager.getConnection(url, user, password);
    }


    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = new Employee();
        try (Connection connection = EmployeeDaoImpl.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE id = " + id)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employee.setId(resultSet.getInt("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setAge(resultSet.getInt("age"));
                employee.setCityId(resultSet.getInt("city_id"));
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        Collection<Employee> employees = new ArrayList<>();

        try (Connection connection = EmployeeDaoImpl.getConnection()) {
            String sql = "SELECT * FROM employee";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String gender = resultSet.getString(4);
                int age = resultSet.getInt(5);
                int cityId = resultSet.getInt(6);
                Employee employee = new Employee(id, firstName, lastName, gender, age, cityId);
                employees.add(employee);
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
        return (List<Employee>) employees;
    }


    @Override
    public void updateEmployee(int id) {
        String sql = "UPDATE employee SET first_name = (?), last_name = (?), gender = (?), age = (?), city_id = (?) WHERE id = " + id;
        try (Connection connection = EmployeeDaoImpl.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите имя");
            String firstName = scanner.nextLine();
            System.out.println("Введите фамилию");
            String lastName = scanner.nextLine();
            System.out.println("Введите пол");
            String gender = scanner.nextLine();
            System.out.println("Введите возраст");
            int age = scanner.nextInt();
            System.out.println("Введите id города");
            int cityId = scanner.nextInt();

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, gender);
            statement.setInt(4, age);
            statement.setInt(5, cityId);
            statement.executeUpdate();
            System.out.println("Данные сотрудника ID = " + id + " обновлены");
        } catch (
                SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(int id) throws SQLException {
        try (final Connection connection = EmployeeDaoImpl.getConnection()) {
            String sql = "DELETE * FROM employee WHERE id = (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Сотрудник ID = " + id + " удален");
        }
    }

    @Override
    public void createEmployee(Employee employee) {
        try (Connection connection = EmployeeDaoImpl.getConnection()) {
            String sql = "INSERT INTO employee (id, first_name, last_name, gender, age, city_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setString(4, employee.getGender());
            statement.setInt(5, employee.getAge());
            statement.setInt(6, employee.getCityId());
            statement.executeUpdate();
            System.out.println("Сотрудник ID = " + employee.getId() + " удален");
        } catch (
                SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
    }


}
