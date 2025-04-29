package org.hrmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrmanage.util.DepartmentType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    Integer id;
    String name;
    String email;
    DepartmentType departmentType;
    LocalDate createdAt;
    LocalDate updatedAt;
}
