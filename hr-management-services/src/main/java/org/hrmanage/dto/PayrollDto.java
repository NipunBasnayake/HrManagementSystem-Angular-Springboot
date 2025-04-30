package org.hrmanage.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollDto {

    private Integer id;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @NotNull(message = "Pay date is required")
    private LocalDate payDate;

    @NotNull(message = "Basic salary is required")
    @DecimalMin(value = "0.00", message = "Basic salary must be non-negative")
    private BigDecimal basicSalary;

    @DecimalMin(value = "0.00", message = "Allowances must be non-negative")
    private BigDecimal allowances = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "Deductions must be non-negative")
    private BigDecimal deductions = BigDecimal.ZERO;

    @NotNull(message = "Net salary is required")
    @DecimalMin(value = "0.00", message = "Net salary must be non-negative")
    private BigDecimal netSalary;

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
