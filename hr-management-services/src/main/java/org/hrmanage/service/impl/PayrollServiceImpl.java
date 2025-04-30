package org.hrmanage.service.impl;

import org.hrmanage.dto.PayrollDto;
import org.hrmanage.service.PayrollService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {
    @Override
    public List<PayrollDto> getAllPayrolls() {
        return List.of();
    }

    @Override
    public PayrollDto getPayrollById(Integer id) {
        return null;
    }

    @Override
    public PayrollDto addPayroll(PayrollDto payrollDto) {
        return null;
    }

    @Override
    public PayrollDto updatePayroll(Integer id, PayrollDto payrollDto) {
        return null;
    }

    @Override
    public Boolean deletePayroll(Integer id) {
        return null;
    }
}
