package org.hrmanage.service;

import org.hrmanage.dto.LeaveDto;

import java.util.List;

public interface LeaveService {
    List<LeaveDto> getAllLeaves();

    LeaveDto getLeaveById(Integer id);

    LeaveDto addLeave(LeaveDto leaveDto);

    LeaveDto updateLeave(Integer id, LeaveDto leaveDto);

    Boolean deleteLeave(Integer id);
}
