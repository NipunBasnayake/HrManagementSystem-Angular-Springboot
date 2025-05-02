import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import Swal from 'sweetalert2';
import { faUsers, faFileAlt, faPlusCircle, faSearch, faEnvelope, faFilter, faEdit, faTrashAlt, faFolderOpen } from '@fortawesome/free-solid-svg-icons';

import { PayrollService } from '../../services/payroll.service';
import { Payroll, PayrollPost } from '../../models/payroll.model';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../models/employee.model';
import { Leave, LeaveGet } from '../../models/leave.model';
import { LeaveService } from '../../services/leave.service';

@Component({
  selector: 'app-view-leaves',
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './view-leaves.component.html',
  styleUrl: './view-leaves.component.css'
})
export class ViewLeavesComponent {
  faUsers = faUsers;
  faFileAlt = faFileAlt;
  faPlusCircle = faPlusCircle;
  faSearch = faSearch;
  faEnvelope = faEnvelope;
  faFilter = faFilter;
  faEdit = faEdit;
  faTrashAlt = faTrashAlt;
  faFolderOpen = faFolderOpen;

  leaves: LeaveGet[] = [];
  employees: Employee[] = [];
  searchEmployeeId = '';
  startDate = '';
  endDate = '';

  constructor(
    private leaveService: LeaveService,
    private employeeService: EmployeeService,
    private router: Router
  ) { }

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
    this.leaveService.getAllLeaves().subscribe({
      next: (res) => this.leaves = res,
      error: () => Swal.fire('Error', 'Failed to fetch payrolls', 'error')
    });
    this.employeeService.getAllEmployees().subscribe({
      next: (res) => this.employees = res,
      error: () => Swal.fire('Error', 'Failed to fetch employees', 'error')
    });
  }

  // addPayroll() {
  //   const employeeOptions = this.employees
  //     .map(emp => `<option value="${emp.id}">${emp.id} - ${emp.name}</option>`)
  //     .join('');
  
  //   Swal.fire({
  //     title: '<strong>Add New Payroll</strong>',
  //     html: `
  //       <div class="swal-form-group">
  //         <label for="swal-empId" class="swal-label">Employee</label>
  //         <select id="swal-empId" class="swal-select">${employeeOptions}</select>
  //       </div>
  //       <div class="swal-form-group">
  //         <label for="swal-payDate" class="swal-label">Pay Date</label>
  //         <input type="date" id="swal-payDate" class="swal-input">
  //       </div>
  //       <div class="swal-form-group">
  //         <label for="swal-basic" class="swal-label">Basic Salary</label>
  //         <input type="number" id="swal-basic" class="swal-input">
  //       </div>
  //       <div class="swal-form-group">
  //         <label for="swal-allowances" class="swal-label">Allowances</label>
  //         <input type="number" id="swal-allowances" class="swal-input">
  //       </div>
  //       <div class="swal-form-group">
  //         <label for="swal-deductions" class="swal-label">Deductions</label>
  //         <input type="number" id="swal-deductions" class="swal-input">
  //       </div>
  //     `,
  //     showCancelButton: true,
  //     confirmButtonText: 'Add Payroll',
  //     preConfirm: () => {
  //       const employeeId = parseInt((document.getElementById('swal-empId') as HTMLSelectElement).value);
  //       const payDate = (document.getElementById('swal-payDate') as HTMLInputElement).value;
  //       const basicSalary = parseFloat((document.getElementById('swal-basic') as HTMLInputElement).value);
  //       const allowances = parseFloat((document.getElementById('swal-allowances') as HTMLInputElement).value || '0');
  //       const deductions = parseFloat((document.getElementById('swal-deductions') as HTMLInputElement).value || '0');
  //       const netSalary = basicSalary + allowances - deductions;
  
  //       if (!employeeId || !payDate || isNaN(basicSalary)) {
  //         Swal.showValidationMessage('Please fill required fields');
  //         return;
  //       }
  
  //       return { employeeId, payDate, basicSalary, allowances, deductions, netSalary } as PayrollPost;
  //     }
  //   }).then((result) => {
  //     if (result.isConfirmed && result.value) {
  //       this.leaveService.createPayroll(result.value).subscribe({
  //         next: () => {
  //           Swal.fire('Success', 'Payroll created successfully', 'success');
  //           this.getAllPayrolls();
  //         },
  //         error: () => Swal.fire('Error', 'Failed to create payroll', 'error')
  //       });
  //     }
  //   });
  // }  

  updateLeave(leave: LeaveGet) {
    // Swal.fire({
    //   title: '<strong>Update Payroll</strong>',
    //   html: `
    //     <div class="swal-form-group">
    //       <label for="swal-empId" class="swal-label">Employee</label>
    //       <select id="swal-empId" class="swal-select" disabled>
    //         <option value="${payroll.employee.id}">${payroll.employee.id} - ${payroll.employee.name}</option>
    //       </select>
    //     </div>
    //     <div class="swal-form-group">
    //       <label for="swal-payDate" class="swal-label">Pay Date</label>
    //       <input type="date" id="swal-payDate" class="swal-input" value="${payroll.payDate}">
    //     </div>
    //     <div class="swal-form-group">
    //       <label for="swal-basic" class="swal-label">Basic Salary</label>
    //       <input type="number" id="swal-basic" class="swal-input" value="${payroll.basicSalary}">
    //     </div>
    //     <div class="swal-form-group">
    //       <label for="swal-allowances" class="swal-label">Allowances</label>
    //       <input type="number" id="swal-allowances" class="swal-input" value="${payroll.allowances || 0}">
    //     </div>
    //     <div class="swal-form-group">
    //       <label for="swal-deductions" class="swal-label">Deductions</label>
    //       <input type="number" id="swal-deductions" class="swal-input" value="${payroll.deductions || 0}">
    //     </div>
    //   `,
    //   showCancelButton: true,
    //   confirmButtonText: 'Update Payroll',
    //   preConfirm: () => {
    //     const payDate = (document.getElementById('swal-payDate') as HTMLInputElement).value;
    //     const basicSalary = parseFloat((document.getElementById('swal-basic') as HTMLInputElement).value);
    //     const allowances = parseFloat((document.getElementById('swal-allowances') as HTMLInputElement).value || '0');
    //     const deductions = parseFloat((document.getElementById('swal-deductions') as HTMLInputElement).value || '0');
    //     const netSalary = basicSalary + allowances - deductions;
  
    //     if (!payDate || isNaN(basicSalary)) {
    //       Swal.showValidationMessage('Please fill required fields');
    //       return;
    //     }
  
    //     return {
    //       id: payroll.id,
    //       employeeId: payroll.employee.id,
    //       payDate,
    //       basicSalary,
    //       allowances,
    //       deductions,
    //       netSalary
    //     } as PayrollPost;
    //   }
    // }).then((result) => {
    //   if (result.isConfirmed && result.value && payroll.id !== undefined) {
    //     this.leaveService.updatePayroll(result.value, payroll.id).subscribe({
    //       next: () => {
    //         Swal.fire('Success', 'Payroll updated successfully', 'success');
    //         this.getAllPayrolls();
    //       },
    //       error: () => Swal.fire('Error', 'Failed to update payroll', 'error')
    //     });
    //   }
    // });
    return null;
  }
  
  deleteLeave(leave: LeaveGet) {
    // Swal.fire({
    //   title: 'Delete this payroll?',
    //   html: `
    //     <p><strong>Employee:</strong> ${payroll.employee.name}</p>
    //     <p><strong>Date:</strong> ${payroll.payDate}</p>
    //     <p><strong>Net Salary:</strong> ${payroll.netSalary}</p>
    //   `,
    //   icon: 'warning',
    //   showCancelButton: true,
    //   confirmButtonText: 'Yes, delete',
    //   cancelButtonText: 'Cancel'
    // }).then((result) => {
    //   if (result.isConfirmed && payroll.id) {
    //     this.leaveService.deletePayroll(payroll.id).subscribe({
    //       next: () => {
    //         Swal.fire('Deleted', 'Payroll deleted', 'success');
    //         this.getAllPayrolls();
    //       },
    //       error: () => Swal.fire('Error', 'Failed to delete payroll', 'error')
    //     });
    //   }
    // });
  }

  filteredLeaves(): LeaveGet[] {
    return this.leaves.filter(p =>
      (this.searchEmployeeId === '' || p.employee.id.toString().includes(this.searchEmployeeId)) &&
      (this.startDate === '' || new Date(p.startDate).toLocaleDateString().includes(this.startDate)) &&
      (this.endDate === '' || new Date(p.endDate).getFullYear().toString().includes(this.endDate))
    );
  }

  exportReports(): void {
    this.leaveService.exportPayrollReport().subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'payroll_report.csv';
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }
}

