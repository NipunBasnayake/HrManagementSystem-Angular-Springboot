package org.hrmanage.service.impl;

import org.hrmanage.dto.EmployeeDto;
import org.hrmanage.dto.PayrollDto;
import org.hrmanage.dto.PayrollSendDto;
import org.hrmanage.entity.EmployeeEntity;
import org.hrmanage.entity.PayrollEntity;
import org.hrmanage.repository.PayrollRepository;
import org.hrmanage.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PayrollServiceImplTest {

    @Mock
    private PayrollRepository payrollRepository;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private PayrollServiceImpl payrollService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private PayrollDto createPayrollDto() {
        return new PayrollDto(
                null,
                1,
                LocalDate.now(),
                new BigDecimal("50000"),
                new BigDecimal("5000"),
                new BigDecimal("2000"),
                new BigDecimal("53000"),
                null,
                null
        );
    }

    private PayrollEntity createEntity() {
        PayrollEntity entity = new PayrollEntity();
        entity.setId(1);
        entity.setEmployee(new EmployeeEntity());
        entity.setPayDate(LocalDate.now());
        entity.setBasicSalary(new BigDecimal("50000"));
        entity.setAllowances(new BigDecimal("5000"));
        entity.setDeductions(new BigDecimal("2000"));
        entity.setNetSalary(new BigDecimal("53000"));
        return entity;
    }

    @Test
    void testGetAllPayrolls_EmptyList() {
        when(payrollRepository.findAll()).thenReturn(List.of());
        List<PayrollSendDto> result = payrollService.getAllPayrolls();
        assertTrue(result.isEmpty());
    }

    @Test
    void testAddPayroll_Success() {
        PayrollDto dto = createPayrollDto();
        PayrollEntity entity = createEntity();

        when(employeeService.getEmployeeById(1)).thenReturn(new EmployeeDto());
        when(payrollRepository.save(any(PayrollEntity.class))).thenReturn(entity);

        PayrollSendDto result = payrollService.addPayroll(dto);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(new BigDecimal("53000"), result.getNetSalary());
    }

    @Test
    void testAddPayroll_EmployeeNotFound() {
        PayrollDto dto = createPayrollDto();
        when(employeeService.getEmployeeById(1)).thenReturn(null);
        PayrollSendDto result = payrollService.addPayroll(dto);
        assertNull(result);
    }

    @Test
    void testGetPayrollById_Found() {
        PayrollEntity entity = createEntity();
        when(payrollRepository.findById(1)).thenReturn(Optional.of(entity));
        PayrollSendDto result = payrollService.getPayrollById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetPayrollById_NotFound() {
        when(payrollRepository.findById(1)).thenReturn(Optional.empty());
        PayrollSendDto result = payrollService.getPayrollById(1);
        assertNull(result);
    }

    @Test
    void testUpdatePayroll_Success() {
        PayrollDto dto = createPayrollDto();
        PayrollEntity entity = createEntity();

        when(payrollRepository.findById(1)).thenReturn(Optional.of(entity));
        when(employeeService.getEmployeeById(1)).thenReturn(new EmployeeDto());
        when(payrollRepository.save(any(PayrollEntity.class))).thenReturn(entity);

        PayrollSendDto result = payrollService.updatePayroll(1, dto);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testUpdatePayroll_NotFound() {
        PayrollDto dto = createPayrollDto();
        when(payrollRepository.findById(1)).thenReturn(Optional.empty());
        PayrollSendDto result = payrollService.updatePayroll(1, dto);
        assertNull(result);
    }

    @Test
    void testDeletePayroll_Exists() {
        when(payrollRepository.findById(1)).thenReturn(Optional.of(createEntity()));
        Boolean result = payrollService.deletePayroll(1);
        assertTrue(result);
        verify(payrollRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeletePayroll_NotExists() {
        when(payrollRepository.findById(1)).thenReturn(Optional.empty());
        Boolean result = payrollService.deletePayroll(1);
        assertFalse(result);
    }
}
