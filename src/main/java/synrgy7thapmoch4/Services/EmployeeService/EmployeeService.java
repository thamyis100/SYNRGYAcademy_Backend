package synrgy7thapmoch4.Services.EmployeeService;

import synrgy7thapmoch4.Entity.Employee;

import java.util.Map;

public interface EmployeeService {
    Map save(Employee request);
    Map edit(Employee request);
    Map delete(Employee request);
    Map list();

    Map getById(Long id);

    Map getAllData();

    Map getByName(String name);

    Map pagination(int page, int size);

    Map paginationByName(int page, int size, String name);
//
//    Map getById(Long id);
//    Map pagination(int page, int size);
//
//    Map paginationByName(int page, int size, String name);
}
