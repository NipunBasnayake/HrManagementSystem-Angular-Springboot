package org.hrmanage.service;

import org.hrmanage.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto getAllEmployees();

    EmployeeDto getEmployeeById(Integer id);

    EmployeeDto addEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    EmployeeDto deleteEmployee(Integer id);
}
