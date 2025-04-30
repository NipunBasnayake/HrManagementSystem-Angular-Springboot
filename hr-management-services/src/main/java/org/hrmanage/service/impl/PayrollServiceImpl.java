package org.hrmanage.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrmanage.dto.PayrollDto;
import org.hrmanage.entity.PayrollEntity;
import org.hrmanage.repository.PayrollRepository;
import org.hrmanage.service.PayrollService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PayrollDto> getAllPayrolls() {
        List<PayrollEntity> entities = payrollRepository.findAll();
        List<PayrollDto> dtoList = new ArrayList<>();
        for (PayrollEntity entity : entities) {
            dtoList.add(modelMapper.map(entity, PayrollDto.class));
        }
        return dtoList;
    }

    @Override
    public PayrollDto getPayrollById(Integer id) {
        Optional<PayrollEntity> optional = payrollRepository.findById(id);
        return optional.map(entity -> modelMapper.map(entity, PayrollDto.class)).orElse(null);
    }

    @Override
    public PayrollDto addPayroll(PayrollDto payrollDto) {
        if (payrollDto.getId() == null) {
            PayrollEntity entity = modelMapper.map(payrollDto, PayrollEntity.class);
            PayrollEntity saved = payrollRepository.save(entity);
            return modelMapper.map(saved, PayrollDto.class);
        }
        return null;
    }

    @Override
    public PayrollDto updatePayroll(Integer id, PayrollDto payrollDto) {
        if (payrollRepository.existsById(id) && id.equals(payrollDto.getId())) {
            PayrollEntity entity = modelMapper.map(payrollDto, PayrollEntity.class);
            PayrollEntity updated = payrollRepository.save(entity);
            return modelMapper.map(updated, PayrollDto.class);
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
}
