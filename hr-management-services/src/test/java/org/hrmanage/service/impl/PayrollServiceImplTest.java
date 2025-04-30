package org.hrmanage.service.impl;

import org.hrmanage.dto.PayrollDto;
import org.hrmanage.entity.EmployeeEntity;
import org.hrmanage.entity.PayrollEntity;
import org.hrmanage.repository.PayrollRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PayrollServiceImplTest {

    private PayrollRepository payrollRepository;
    private ModelMapper modelMapper;
    private PayrollServiceImpl payrollService;

    @BeforeEach
    void setUp() {
        payrollRepository = mock(PayrollRepository.class);
        modelMapper = new ModelMapper();
        payrollService = new PayrollServiceImpl(payrollRepository, modelMapper);
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
        entity.setCreatedAt(LocalDate.now());
        entity.setUpdatedAt(LocalDate.now());
        return entity;
    }

    private PayrollDto createDto() {
        PayrollDto dto = new PayrollDto();
        dto.setId(1);
        dto.setEmployeeId(1);
        dto.setPayDate(LocalDate.now());
        dto.setBasicSalary(new BigDecimal("50000"));
        dto.setAllowances(new BigDecimal("5000"));
        dto.setDeductions(new BigDecimal("2000"));
        dto.setNetSalary(new BigDecimal("53000"));
        dto.setCreatedAt(LocalDate.now());
        dto.setUpdatedAt(LocalDate.now());
        return dto;
    }

    @Test
    void testGetAllPayrolls() {
        List<PayrollEntity> entities = List.of(createEntity());
        when(payrollRepository.findAll()).thenReturn(entities);

        List<PayrollDto> result = payrollService.getAllPayrolls();

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("53000"), result.get(0).getNetSalary());
    }

    @Test
    void testGetPayrollById_Found() {
        PayrollEntity entity = createEntity();
        when(payrollRepository.findById(1)).thenReturn(Optional.of(entity));

        PayrollDto result = payrollService.getPayrollById(1);

        assertNotNull(result);
        assertEquals(new BigDecimal("53000"), result.getNetSalary());
    }

    @Test
    void testGetPayrollById_NotFound() {
        when(payrollRepository.findById(1)).thenReturn(Optional.empty());

        PayrollDto result = payrollService.getPayrollById(1);

        assertNull(result);
    }

    @Test
    void testAddPayroll_Success() {
        PayrollDto dto = createDto();
        dto.setId(null);
        PayrollEntity entity = createEntity();
        entity.setId(1);

        when(payrollRepository.save(any(PayrollEntity.class))).thenReturn(entity);

        PayrollDto result = payrollService.addPayroll(dto);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testAddPayroll_WithId_ReturnsNull() {
        PayrollDto dto = createDto();
        dto.setId(1);

        PayrollDto result = payrollService.addPayroll(dto);

        assertNull(result);
        verify(payrollRepository, never()).save(any());
    }

    @Test
    void testUpdatePayroll_Success() {
        PayrollDto dto = createDto();
        when(payrollRepository.existsById(1)).thenReturn(true);
        when(payrollRepository.save(any())).thenReturn(createEntity());

        PayrollDto result = payrollService.updatePayroll(1, dto);

        assertNotNull(result);
        assertEquals(new BigDecimal("53000"), result.getNetSalary());
    }

    @Test
    void testUpdatePayroll_IdMismatchOrNotExist() {
        PayrollDto dto = createDto();
        dto.setId(2);
        when(payrollRepository.existsById(1)).thenReturn(true);

        PayrollDto result = payrollService.updatePayroll(1, dto);

        assertNull(result);
    }

    @Test
    void testDeletePayroll_Success() {
        when(payrollRepository.existsById(1)).thenReturn(true).thenReturn(false);

        Boolean result = payrollService.deletePayroll(1);

        assertTrue(result);
        verify(payrollRepository).deleteById(1);
    }

    @Test
    void testDeletePayroll_NotFound() {
        when(payrollRepository.existsById(1)).thenReturn(false);

        Boolean result = payrollService.deletePayroll(1);

        assertFalse(result);
        verify(payrollRepository, never()).deleteById(any());
    }
}
