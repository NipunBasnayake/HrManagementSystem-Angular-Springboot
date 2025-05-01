package org.hrmanage.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrmanage.dto.EmployeeDto;
import org.hrmanage.dto.LeaveDto;
import org.hrmanage.dto.LeaveSendDto;
import org.hrmanage.entity.LeaveEntity;
import org.hrmanage.repository.LeaveRepository;
import org.hrmanage.service.EmployeeService;
import org.hrmanage.service.LeaveService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Override
    public List<LeaveSendDto> getAllLeaves() {
        List<LeaveEntity> allLeaves = leaveRepository.findAll();
        List<LeaveSendDto> leaveSendDtos = new ArrayList<>();

        for (LeaveEntity leave : allLeaves) {
            LeaveSendDto sendDto = modelMapper.map(leave, LeaveSendDto.class);
            EmployeeDto employeeDto = employeeService.getEmployeeById(leave.getId());
            sendDto.setEmployee(employeeDto);
            leaveSendDtos.add(sendDto);
        }

        return leaveSendDtos;
    }

    @Override
    public LeaveSendDto getLeaveById(Integer id) {
        Optional<LeaveEntity> leaveOpt = leaveRepository.findById(id);
        if (leaveOpt.isPresent()) {
            LeaveSendDto sendDto = modelMapper.map(leaveOpt.get(), LeaveSendDto.class);
            EmployeeDto employeeDto = employeeService.getEmployeeById(leaveOpt.get().getId());
            sendDto.setEmployee(employeeDto);
            return sendDto;
        }
        return null;
    }

    @Override
    public LeaveSendDto addLeave(LeaveDto leaveDto) {
        if (leaveDto.getId() == null) {
            LeaveEntity saved = leaveRepository.save(modelMapper.map(leaveDto, LeaveEntity.class));
            LeaveSendDto sendDto = modelMapper.map(saved, LeaveSendDto.class);
            EmployeeDto employeeDto = employeeService.getEmployeeById(saved.getId());
            sendDto.setEmployee(employeeDto);
            return sendDto;
        }
        return null;
    }

    @Override
    public LeaveSendDto updateLeave(Integer id, LeaveDto leaveDto) {
        if (leaveRepository.existsById(id) && id.equals(leaveDto.getId())) {
            LeaveEntity updated = leaveRepository.save(modelMapper.map(leaveDto, LeaveEntity.class));
            LeaveSendDto sendDto = modelMapper.map(updated, LeaveSendDto.class);
            EmployeeDto employeeDto = employeeService.getEmployeeById(updated.getId());
            sendDto.setEmployee(employeeDto);
            return sendDto;
        }
        return null;
    }

    @Override
    public Boolean deleteLeave(Integer id) {
        if (leaveRepository.existsById(id)) {
            leaveRepository.deleteById(id);
            return !leaveRepository.existsById(id);
        }
        return false;
    }
}
