package org.hrmanage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrmanage.util.DepartmentType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull(message = "Name must be insert")
    @Size(max = 100, message = "Name cannot be exceed 100 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name can only contains alphabetic characters and spaces")
    String name;

    @Column(unique = true)
    @NotNull(message = "Email must be insert")
    @Pattern( regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email format")
    String email;

    @NotNull(message = "Department must be insert")
    DepartmentType departmentType;

    @Column(updatable = false)
    LocalDate createdAt;

    LocalDate updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
