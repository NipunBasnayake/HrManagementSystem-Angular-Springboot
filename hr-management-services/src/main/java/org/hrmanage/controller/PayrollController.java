package org.hrmanage.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hrmanage.dto.PayrollDto;
import org.hrmanage.dto.PayrollSendDto;
import org.hrmanage.service.PayrollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    @GetMapping
    public ResponseEntity<List<PayrollSendDto>> getAllPayrolls() {
        return ResponseEntity.ok(payrollService.getAllPayrolls());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollSendDto> getPayrollById(@PathVariable("id") Integer id) {
        PayrollSendDto payrollSendDto = payrollService.getPayrollById(id);
        if (payrollSendDto != null) {
            return ResponseEntity.ok(payrollSendDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PayrollSendDto> createPayroll(@Valid @RequestBody PayrollDto payrollDto) {
        PayrollSendDto payrollSendDto = payrollService.addPayroll(payrollDto);
        return ResponseEntity.ok(payrollSendDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollSendDto> updatePayroll(@PathVariable("id") Integer id, @Valid @RequestBody PayrollDto payrollDto) {
        PayrollSendDto updatedPayroll = payrollService.updatePayroll(id, payrollDto);
        if (updatedPayroll != null) {
            return ResponseEntity.ok(updatedPayroll);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePayroll(@PathVariable("id") Integer id) {
        Boolean isDeleted = payrollService.deletePayroll(id);
        if (isDeleted) {
            return ResponseEntity.ok(isDeleted);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/report")
    public void exportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=payrolls.csv");

        List<PayrollSendDto> payrollList = payrollService.getAllPayrolls();
        PrintWriter writer = response.getWriter();

        writer.println("ID,Employee Name,Pay Date,Basic Salary,Allowances,Deductions,Net Salary,Created At,Updated At");

        for (PayrollSendDto payroll : payrollList) {
            writer.println(String.format("%d,%s,%s,%.2f,%.2f,%.2f,%.2f,%s,%s",
                    payroll.getId(),
                    payroll.getEmployee().getName(),
                    payroll.getPayDate(),
                    payroll.getBasicSalary(),
                    payroll.getAllowances(),
                    payroll.getDeductions(),
                    payroll.getNetSalary(),
                    payroll.getCreatedAt(),
                    payroll.getUpdatedAt()
            ));
        }

        writer.flush();
        writer.close();
    }
}
