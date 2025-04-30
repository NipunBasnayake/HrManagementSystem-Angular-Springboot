package org.hrmanage.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hrmanage.dto.PayrollDto;
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
    public ResponseEntity<List<PayrollDto>> getAllPayrolls() {
        return ResponseEntity.ok(payrollService.getAllPayrolls());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollDto> getPayrollById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(payrollService.getPayrollById(id));
    }

    @PostMapping
    public ResponseEntity<PayrollDto> createPayroll(@Valid @RequestBody PayrollDto payrollDto) {
        return ResponseEntity.ok(payrollService.addPayroll(payrollDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollDto> updatePayroll(@PathVariable("id") Integer id, @Valid @RequestBody PayrollDto payrollDto) {
        return ResponseEntity.ok(payrollService.updatePayroll(id, payrollDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePayroll(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(payrollService.deletePayroll(id));
    }

    @GetMapping("/report")
    public void exportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=payrolls.csv");

        List<PayrollDto> payrollList = payrollService.getAllPayrolls();
        PrintWriter writer = response.getWriter();

        writer.println("ID,Employee ID,Pay Date,Basic Salary,Allowances,Deductions,Net Salary,Created At,Updated At");

        for (PayrollDto payroll : payrollList) {
            writer.println(String.format("%d,%d,%s,%.2f,%.2f,%.2f,%.2f,%s,%s",
                    payroll.getId(),
                    payroll.getEmployeeId(),
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
