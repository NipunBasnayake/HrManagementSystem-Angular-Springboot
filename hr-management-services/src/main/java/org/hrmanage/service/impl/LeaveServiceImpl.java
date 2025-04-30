package org.hrmanage.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrmanage.dto.LeaveDto;
import org.hrmanage.entity.LeaveEntity;
import org.hrmanage.repository.LeaveRepository;
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
    private final ModelMapper modelMapper;

    @Override
    public List<LeaveDto> getAllLeaves() {
        List<LeaveEntity> allLeaves = leaveRepository.findAll();
        List<LeaveDto> leaveDtoList = new ArrayList<>();
        allLeaves.forEach(leave -> leaveDtoList.add(modelMapper.map(leave, LeaveDto.class)));
        return leaveDtoList;
    }

    @Override
    public LeaveDto getLeaveById(Integer id) {
        Optional<LeaveEntity> leaveOpt = leaveRepository.findById(id);
        return leaveOpt.map(leave -> modelMapper.map(leave, LeaveDto.class)).orElse(null);
    }

    @Override
    public LeaveDto addLeave(LeaveDto leaveDto) {
        if (leaveDto.getId() == null) {
            LeaveEntity saved = leaveRepository.save(modelMapper.map(leaveDto, LeaveEntity.class));
            return modelMapper.map(saved, LeaveDto.class);
        }
        return null;
    }

    @Override
    public LeaveDto updateLeave(Integer id, LeaveDto leaveDto) {
        if (leaveRepository.existsById(id) && id.equals(leaveDto.getId())) {
            LeaveEntity updated = leaveRepository.save(modelMapper.map(leaveDto, LeaveEntity.class));
            return modelMapper.map(updated, LeaveDto.class);
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
