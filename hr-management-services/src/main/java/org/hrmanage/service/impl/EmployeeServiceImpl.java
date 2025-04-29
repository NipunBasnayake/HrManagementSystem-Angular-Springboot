package org.hrmanage.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrmanage.dto.EmployeeDto;
import org.hrmanage.entity.EmployeeEntity;
import org.hrmanage.repository.EmployeeRepository;
import org.hrmanage.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employeeRepositoryAll = employeeRepository.findAll();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        employeeRepositoryAll.forEach(employeeEntity -> employeeDtoList.add(modelMapper.map(employeeEntity, EmployeeDto.class)));
        return employeeDtoList;
    }

    @Override
    public EmployeeDto getEmployeeById(Integer id) {
        if (employeeRepository.existsById(id)) {
            return modelMapper.map(employeeRepository.findById(id), EmployeeDto.class);
        }
        return null;
    }

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getId() == null) {
            EmployeeEntity savedEntity = employeeRepository.save(modelMapper.map(employeeDto, EmployeeEntity.class));
            return modelMapper.map(savedEntity, EmployeeDto.class);
        }
        return null;
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsById(employeeDto.getId())) {
            EmployeeEntity savedEntity = employeeRepository.save(modelMapper.map(employeeDto, EmployeeEntity.class));
            return modelMapper.map(savedEntity, EmployeeDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteEmployee(Integer id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return !employeeRepository.existsById(id);
        }
        return false;
    }
}
