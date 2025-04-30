package org.hrmanage.service.impl;

import org.hrmanage.dto.LeaveDto;
import org.hrmanage.entity.EmployeeEntity;
import org.hrmanage.entity.LeaveEntity;
import org.hrmanage.repository.LeaveRepository;
import org.hrmanage.util.LeaveStatus;
import org.hrmanage.util.LeaveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceImplTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    private LeaveEntity leaveEntity;
    private LeaveDto leaveDto;
    private EmployeeEntity employee;

    @BeforeEach
    void setUp() {
        employee = new EmployeeEntity();
        employee.setId(1);

        leaveEntity = new LeaveEntity(
                1,
                employee,
                LeaveType.ANNUAL,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                "Vacation",
                LeaveStatus.PENDING,
                LocalDate.now(),
                LocalDate.now()
        );

        leaveDto = new LeaveDto(
                1,
                1,
                LeaveType.ANNUAL,
                leaveEntity.getStartDate(),
                leaveEntity.getEndDate(),
                "Vacation",
                LeaveStatus.PENDING,
                leaveEntity.getCreatedAt(),
                leaveEntity.getUpdatedAt()
        );
    }

    @Test
    void getAllLeaves_success() {
        when(leaveRepository.findAll()).thenReturn(List.of(leaveEntity));
        when(modelMapper.map(leaveEntity, LeaveDto.class)).thenReturn(leaveDto);

        List<LeaveDto> result = leaveService.getAllLeaves();

        assertEquals(1, result.size());
        assertEquals(leaveDto, result.get(0));
    }

    @Test
    void getLeaveById_success() {
        when(leaveRepository.findById(1)).thenReturn(Optional.of(leaveEntity));
        when(modelMapper.map(leaveEntity, LeaveDto.class)).thenReturn(leaveDto);

        LeaveDto result = leaveService.getLeaveById(1);

        assertNotNull(result);
        assertEquals(leaveDto, result);
    }

    @Test
    void getLeaveById_notFound() {
        when(leaveRepository.findById(1)).thenReturn(Optional.empty());

        LeaveDto result = leaveService.getLeaveById(1);

        assertNull(result);
    }

    @Test
    void addLeave_success() {
        LeaveDto inputDto = new LeaveDto(
                null,
                1,
                LeaveType.ANNUAL,
                leaveEntity.getStartDate(),
                leaveEntity.getEndDate(),
                "Vacation",
                LeaveStatus.PENDING,
                null,
                null
        );

        LeaveEntity inputEntity = new LeaveEntity(
                null,
                employee,
                LeaveType.ANNUAL,
                inputDto.getStartDate(),
                inputDto.getEndDate(),
                "Vacation",
                LeaveStatus.PENDING,
                null,
                null
        );

        when(modelMapper.map(inputDto, LeaveEntity.class)).thenReturn(inputEntity);
        when(leaveRepository.save(inputEntity)).thenReturn(leaveEntity);
        when(modelMapper.map(leaveEntity, LeaveDto.class)).thenReturn(leaveDto);

        LeaveDto result = leaveService.addLeave(inputDto);

        assertNotNull(result);
        assertEquals(leaveDto, result);
    }

    @Test
    void addLeave_withId_shouldReturnNull() {
        LeaveDto inputWithId = new LeaveDto(
                10,
                1,
                LeaveType.SICK,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
                "Flu",
                LeaveStatus.PENDING,
                null,
                null
        );

        LeaveDto result = leaveService.addLeave(inputWithId);

        assertNull(result);
        verify(leaveRepository, never()).save(any());
    }

    @Test
    void updateLeave_success() {
        when(leaveRepository.existsById(1)).thenReturn(true);
        when(modelMapper.map(leaveDto, LeaveEntity.class)).thenReturn(leaveEntity);
        when(leaveRepository.save(leaveEntity)).thenReturn(leaveEntity);
        when(modelMapper.map(leaveEntity, LeaveDto.class)).thenReturn(leaveDto);

        LeaveDto result = leaveService.updateLeave(1, leaveDto);

        assertNotNull(result);
        assertEquals(leaveDto, result);
    }

    @Test
    void updateLeave_idMismatch_shouldReturnNull() {
        LeaveDto dto = new LeaveDto(2, 1, LeaveType.SICK, LocalDate.now(), LocalDate.now(), "Mismatch", LeaveStatus.PENDING, null, null);
        LeaveDto result = leaveService.updateLeave(1, dto);
        assertNull(result);
    }

    @Test
    void updateLeave_nonExisting_shouldReturnNull() {
        when(leaveRepository.existsById(1)).thenReturn(false);
        LeaveDto result = leaveService.updateLeave(1, leaveDto);
        assertNull(result);
    }

    @Test
    void deleteLeave_success() {
        when(leaveRepository.existsById(1)).thenReturn(true).thenReturn(false);
        Boolean result = leaveService.deleteLeave(1);
        assertTrue(result);
        verify(leaveRepository).deleteById(1);
    }

    @Test
    void deleteLeave_notExists() {
        when(leaveRepository.existsById(1)).thenReturn(false);
        Boolean result = leaveService.deleteLeave(1);
        assertFalse(result);
        verify(leaveRepository, never()).deleteById(any());
    }
}
