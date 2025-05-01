package org.hrmanage.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrmanage.dto.EmployeeDto;
import org.hrmanage.dto.PayrollDto;
import org.hrmanage.dto.PayrollSendDto;
import org.hrmanage.entity.PayrollEntity;
import org.hrmanage.repository.PayrollRepository;
import org.hrmanage.service.EmployeeService;
import org.hrmanage.service.PayrollService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Override
    public List<PayrollSendDto> getAllPayrolls() {
        List<PayrollEntity> entities = payrollRepository.findAll();
        List<EmployeeDto> allEmployees = employeeService.getAllEmployees();
        List<PayrollSendDto> dtoList = new ArrayList<>();
        for (PayrollEntity entity : entities) {
            PayrollDto payrollDto = modelMapper.map(entity, PayrollDto.class);
            for (EmployeeDto employee : allEmployees) {
                if (employee.getId().equals(payrollDto.getEmployeeId())) {
                    dtoList.add(getPayrollSendDto(payrollDto, employee));
                }
            }
        }
        return dtoList;
    }

    @Override
    public PayrollSendDto getPayrollById(Integer id) {
        if (payrollRepository.existsById(id)) {
            PayrollDto payrollDto = modelMapper.map(payrollRepository.findById(id), PayrollDto.class);
            EmployeeDto employee = employeeService.getEmployeeById(payrollDto.getEmployeeId());
            return getPayrollSendDto(payrollDto, employee);
        }
        return null;
    }

    @Override
    public PayrollSendDto addPayroll(PayrollDto payrollDto) {
        if (payrollDto.getId() == null) {
            PayrollDto saved = modelMapper.map(payrollRepository.save(modelMapper.map(payrollDto, PayrollEntity.class)), PayrollDto.class);
            EmployeeDto employee = employeeService.getEmployeeById(payrollDto.getEmployeeId());
            return getPayrollSendDto(saved, employee);
        }
        return null;
    }

    @Override
    public PayrollSendDto updatePayroll(Integer id, PayrollDto payrollDto) {
        if (payrollRepository.existsById(id) && id.equals(payrollDto.getId())) {
            PayrollDto updated = modelMapper.map(payrollRepository.save(modelMapper.map(payrollDto, PayrollEntity.class)), PayrollDto.class);
            EmployeeDto employee = employeeService.getEmployeeById(payrollDto.getEmployeeId());
            return getPayrollSendDto(updated, employee);
        }
        return null;
    }

    @Override
    public Boolean deletePayroll(Integer id) {
        if (payrollRepository.existsById(id)) {
            payrollRepository.deleteById(id);
            return !payrollRepository.existsById(id);
        }
        return false;
    }

    private PayrollSendDto getPayrollSendDto(PayrollDto payrollDto, EmployeeDto employee) {
        return new PayrollSendDto(
                payrollDto.getId(),
                employee,
                payrollDto.getPayDate(),
                payrollDto.getBasicSalary(),
                payrollDto.getAllowances(),
                payrollDto.getDeductions(),
                payrollDto.getNetSalary(),
                payrollDto.getCreatedAt(),
                payrollDto.getUpdatedAt()
        );
    }
}
