package org.hrmanage.service;

import org.hrmanage.dto.PayrollDto;
import org.hrmanage.dto.PayrollSendDto;

import java.util.List;

public interface PayrollService {
    List<PayrollSendDto> getAllPayrolls();

    PayrollSendDto getPayrollById(Integer id);

    PayrollSendDto addPayroll(PayrollDto payrollDto);

    PayrollSendDto updatePayroll(Integer id, PayrollDto payrollDto);

    Boolean deletePayroll(Integer id);
}
