import java.util.List;

public interface EmployeeDAO {
    //    Создание (добавление) сущности Employee в таблицу.
    void createEmployee(Employee employee);

    //    Получение конкретного объекта Employee по id.
    Employee getEmployeeById(int id);

    //    Получение списка всех объектов Employee из базы.
    List<Employee> getAllEmployees();

    //    Изменение конкретного объекта Employee в базе по id.
    void updateEmployee(int id);

    //    Удаление конкретного объекта Employee из базы по id.
    void deleteEmployee(int id);
}
