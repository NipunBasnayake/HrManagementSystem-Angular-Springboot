import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import Swal from 'sweetalert2';
import { faUsers, faFileAlt, faPlusCircle, faSearch, faEnvelope, faFilter, faEdit, faTrashAlt, faFolderOpen } from '@fortawesome/free-solid-svg-icons';

import { PayrollService } from '../../services/payroll.service';
import { Payroll, PayrollPost } from '../../models/payroll.model';

@Component({
  selector: 'app-view-payrolls',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './view-payrolls.component.html',
  styleUrls: ['./view-payrolls.component.css']
})
export class ViewPayrollsComponent {
  faUsers = faUsers;
  faFileAlt = faFileAlt;
  faPlusCircle = faPlusCircle;
  faSearch = faSearch;
  faEnvelope = faEnvelope;
  faFilter = faFilter;
  faEdit = faEdit;
  faTrashAlt = faTrashAlt;
  faFolderOpen = faFolderOpen;

  payrolls: Payroll[] = [];
  searchEmployeeId = '';
  searchMonth = '';
  searchYear = '';

  constructor(private payrollService: PayrollService, private router: Router) { }

  ngOnInit() {
    this.getAllPayrolls();
    this.configureSwalStyles();
  }

  configureSwalStyles() {
    Swal.mixin({
      customClass: {
        popup: 'swal-custom-popup swal-wide',
        confirmButton: 'swal-custom-confirm',
        cancelButton: 'swal-custom-cancel',
        input: 'swal-input'
      },
      buttonsStyling: false
    });
  }

  getAllPayrolls() {
    this.payrollService.getAllPayrolls().subscribe({
      next: (res) => this.payrolls = res,
      error: () => Swal.fire('Error', 'Failed to fetch payrolls', 'error')
    });
  }

  addPayroll() {
    Swal.fire({
      title: '<strong>Add New Payroll</strong>',
      html: `
        <div class="swal-form-group">
          <label for="swal-empId" class="swal-label">Employee ID</label>
          <input type="number" id="swal-empId" class="swal-input">
        </div>
        <div class="swal-form-group">
          <label for="swal-payDate" class="swal-label">Pay Date</label>
          <input type="date" id="swal-payDate" class="swal-input">
        </div>
        <div class="swal-form-group">
          <label for="swal-basic" class="swal-label">Basic Salary</label>
          <input type="number" id="swal-basic" class="swal-input">
        </div>
        <div class="swal-form-group">
          <label for="swal-allowances" class="swal-label">Allowances</label>
          <input type="number" id="swal-allowances" class="swal-input">
        </div>
        <div class="swal-form-group">
          <label for="swal-deductions" class="swal-label">Deductions</label>
          <input type="number" id="swal-deductions" class="swal-input">
        </div>
      `,
      showCancelButton: true,
      confirmButtonText: 'Add Payroll',
      preConfirm: () => {
        const employeeId = parseInt((document.getElementById('swal-empId') as HTMLInputElement).value);
        const payDate = (document.getElementById('swal-payDate') as HTMLInputElement).value;
        const basicSalary = parseFloat((document.getElementById('swal-basic') as HTMLInputElement).value);
        const allowances = parseFloat((document.getElementById('swal-allowances') as HTMLInputElement).value || '0');
        const deductions = parseFloat((document.getElementById('swal-deductions') as HTMLInputElement).value || '0');
        const netSalary = basicSalary + allowances - deductions;

        if (!employeeId || !payDate || isNaN(basicSalary)) {
          Swal.showValidationMessage('Please fill required fields');
          return;
        }

        return { employeeId, payDate, basicSalary, allowances, deductions, netSalary } as PayrollPost;
      }
    }).then((result) => {
      if (result.isConfirmed && result.value) {
        this.payrollService.createPayroll(result.value).subscribe({
          next: () => {
            Swal.fire('Success', 'Payroll created successfully', 'success');
            this.getAllPayrolls();
          },
          error: () => Swal.fire('Error', 'Failed to create payroll', 'error')
        });
      }
    });
  }

  updatePayroll(payroll: Payroll) {
    Swal.fire({
      title: '<strong>Update Payroll</strong>',
      html: `
        <div class="swal-form-group">
          <label for="swal-empId" class="swal-label">Employee ID</label>
          <input type="number" id="swal-empId" class="swal-input" value="${payroll.employee.id}" readonly>
        </div>
        <div class="swal-form-group">
          <label for="swal-payDate" class="swal-label">Pay Date</label>
          <input type="date" id="swal-payDate" class="swal-input" value="${payroll.payDate}">
        </div>
        <div class="swal-form-group">
          <label for="swal-basic" class="swal-label">Basic Salary</label>
          <input type="number" id="swal-basic" class="swal-input" value="${payroll.basicSalary}">
        </div>
        <div class="swal-form-group">
          <label for="swal-allowances" class="swal-label">Allowances</label>
          <input type="number" id="swal-allowances" class="swal-input" value="${payroll.allowances || 0}">
        </div>
        <div class="swal-form-group">
          <label for="swal-deductions" class="swal-label">Deductions</label>
          <input type="number" id="swal-deductions" class="swal-input" value="${payroll.deductions || 0}">
        </div>
      `,
      showCancelButton: true,
      confirmButtonText: 'Update Payroll',
      preConfirm: () => {
        const payDate = (document.getElementById('swal-payDate') as HTMLInputElement).value;
        const basicSalary = parseFloat((document.getElementById('swal-basic') as HTMLInputElement).value);
        const allowances = parseFloat((document.getElementById('swal-allowances') as HTMLInputElement).value || '0');
        const deductions = parseFloat((document.getElementById('swal-deductions') as HTMLInputElement).value || '0');
        const netSalary = basicSalary + allowances - deductions;

        if (!payDate || isNaN(basicSalary)) {
          Swal.showValidationMessage('Please fill required fields');
          return;
        }

        return {
          id: payroll.id,
          employeeId: payroll.employee.id,
          payDate,
          basicSalary,
          allowances,
          deductions,
          netSalary
        } as PayrollPost;
      }
    }).then((result) => {
      if (result.isConfirmed && result.value && payroll.id !== undefined) {
        this.payrollService.updatePayroll(result.value, payroll.id).subscribe({
          next: () => {
            Swal.fire('Success', 'Payroll updated successfully', 'success');
            this.getAllPayrolls();
          },
          error: () => Swal.fire('Error', 'Failed to update payroll', 'error')
        });
      }
    });
  }

  deletePayroll(payroll: Payroll) {
    Swal.fire({
      title: 'Delete this payroll?',
      html: `
        <p><strong>Employee:</strong> ${payroll.employee.name}</p>
        <p><strong>Date:</strong> ${payroll.payDate}</p>
        <p><strong>Net Salary:</strong> ${payroll.netSalary}</p>
      `,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete',
      cancelButtonText: 'Cancel'
    }).then((result) => {
      if (result.isConfirmed && payroll.id) {
        this.payrollService.deletePayroll(payroll.id).subscribe({
          next: () => {
            Swal.fire('Deleted', 'Payroll deleted', 'success');
            this.getAllPayrolls();
          },
          error: () => Swal.fire('Error', 'Failed to delete payroll', 'error')
        });
      }
    });
  }

  filteredPayrolls(): Payroll[] {
    return this.payrolls.filter(p =>
      (this.searchEmployeeId === '' || p.employee.id.toString().includes(this.searchEmployeeId)) &&
      (this.searchMonth === '' || new Date(p.payDate).toLocaleDateString().includes(this.searchMonth)) &&
      (this.searchYear === '' || new Date(p.payDate).getFullYear().toString().includes(this.searchYear))
    );
  }

  exportReports(): void {
    this.payrollService.exportPayrollReport().subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'payroll_report.csv';
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }
}
