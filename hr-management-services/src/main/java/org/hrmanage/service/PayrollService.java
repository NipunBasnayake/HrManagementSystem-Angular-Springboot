package org.hrmanage.service;

import org.hrmanage.dto.PayrollDto;

import java.util.List;

public interface PayrollService {
    List<PayrollDto> getAllPayrolls();

    PayrollDto getPayrollById(Integer id);

    PayrollDto addPayroll(PayrollDto payrollDto);

    PayrollDto updatePayroll(Integer id, PayrollDto payrollDto);

    Boolean deletePayroll(Integer id);
}
