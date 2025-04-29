import { Component } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { Router } from '@angular/router';
import { Employee, EmployeePost } from '../../models/employee.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { Observable } from 'rxjs';




@Component({
  selector: 'app-view-employees',
  imports: [CommonModule, FormsModule],
  templateUrl: './view-employees.component.html',
  styleUrl: './view-employees.component.css'
})
export class ViewEmployeesComponent {

  employees: Employee[] = [];

  searchName: string = '';
  searchEmail: string = '';
  searchDepartment: string = '';

  departmentTypes: string[] = ['HR', 'IT', 'FINANCE', 'OPERATIONS'];

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) { }

  ngOnInit() {
    this.getAllEmployees();
  }

  getAllEmployees() {
    this.employeeService.getAllEmployees().subscribe({
      next: (response) => {
        this.employees = response;
      }
    })
  }

  addEmployee() {
    Swal.fire({
      title: 'Add New Employee',
      html: `
        <div class="mb-3">
            <div class="input-group">
              <input type="text" id="swal-name" class="form-control" placeholder="Enter full name">
            </div>
          </div>
          <div class="mb-3">
            <div class="input-group">
              <input type="email" id="swal-email" class="form-control" placeholder="Enter email address">
            </div>
          </div>
          <div class="mb-3">
            <div class="input-group">
              <select id="swal-dept" class="form-select">
                <option value="" disabled selected>Select Department</option>
                <option value="HR">HR</option>
                <option value="IT">IT</option>
                <option value="FINANCE">FINANCE</option>
                <option value="OPERATIONS">OPERATIONS</option>
              </select>
            </div>
          </div>
      `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Add Employee',
      preConfirm: () => {
        const name = (document.getElementById('swal-name') as HTMLInputElement).value.trim();
        const email = (document.getElementById('swal-email') as HTMLInputElement).value.trim();
        const department = (document.getElementById('swal-dept') as HTMLSelectElement).value;
  
        if (!name || !email || !department) {
          Swal.showValidationMessage('Please fill all fields');
          return;
        }
  
        return { name, email, departmentType: department };
      }
    }).then((result) => {
      if (result.isConfirmed && result.value) {

        if (this.validateEmployee(result.value)){
          console.log(result.value);

          const employee: EmployeePost = result.value;
          this.employeeService.createEmployee(employee).subscribe({
            next: (response) => {
              Swal.fire('Success!', 'Employee has been added.', 'success');
              this.getAllEmployees();
            }
          });
        } else{
          Swal.fire('Error!', 'Invalid employee data.', 'error');
        }
      }
    });
  }

  validateEmployee(employee: EmployeePost): boolean {

    if (employee.name.length > 100) {
      return false;
    }
  
    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    if (!emailRegex.test(employee.email)) {
      return false;
    }
  
    const validDepartments = ['HR', 'IT', 'FINANCE', 'OPERATIONS'];
    if (!validDepartments.includes(employee.departmentType)) {
      return false;
    }
  
    return true;
  }
  
  updateEmployee(employee: Employee) {
    Swal.fire({
      title: 'Update Employee',
      html: `
        <div class="mb-3">
            <div class="input-group">
              <input type="text" id="swal-name" class="form-control" placeholder="Enter full name" value="${employee.name}">
            </div>
          </div>
          <div class="mb-3">
            <div class="input-group">
              <input type="email" id="swal-email" class="form-control" placeholder="Enter email address" value="${employee.email}">
            </div>
          </div>
          <div class="mb-3">
            <div class="input-group">
              <select id="swal-dept" class="form-select">
                <option value="" disabled>Select Department</option>
                <option value="HR" ${employee.departmentType === 'HR' ? 'selected' : ''}>HR</option>
                <option value="IT" ${employee.departmentType === 'IT' ? 'selected' : ''}>IT</option>
                <option value="FINANCE" ${employee.departmentType === 'FINANCE' ? 'selected' : ''}>FINANCE</option>
                <option value="OPERATIONS" ${employee.departmentType === 'OPERATIONS' ? 'selected' : ''}>OPERATIONS</option>
              </select>
            </div>
          </div>
      `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Update Employee',
      preConfirm: () => {
        const name = (document.getElementById('swal-name') as HTMLInputElement).value.trim();
        const email = (document.getElementById('swal-email') as HTMLInputElement).value.trim();
        const department = (document.getElementById('swal-dept') as HTMLSelectElement).value;
  
        if (!name || !email || !department) {
          Swal.showValidationMessage('Please fill all fields');
          return;
        }
  
        return { id: employee.id, name, email, departmentType: department };
      }
    }).then((result) => {
      if (result.isConfirmed && result.value) {
        if (this.validateEmployee(result.value)) {
          const employee: Employee = result.value;
          this.employeeService.updateEmployee(employee).subscribe({
            next: () => {
              Swal.fire('Success!', 'Employee updated successfully.', 'success');
              this.getAllEmployees();
            },
            error: () => {
              Swal.fire('Error!', 'Failed to update employee.', 'error');
            }
          });
        } else {
          Swal.fire('Error!', 'Invalid employee data.', 'error');
        }
      }
    });
  }

  deleteEmployee(employee: Employee) {
    Swal.fire({
      title: 'Are you sure to delete this employee?',
      html: `
        <p><strong>Name:</strong> ${employee.name}</p>
        <p><strong>Email:</strong> ${employee.email}</p>
        <p><strong>Department:</strong> ${employee.departmentType}</p>
      `,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'Cancel'
    }).then((result) => {
      if (result.isConfirmed) {
        this.employeeService.deleteEmployee(employee.id).subscribe(() => {
          Swal.fire('Deleted!', 'Employee has been deleted.', 'success');
          this.getAllEmployees();
        });
      }
    });
  }

  filteredEmployees(): Employee[] {
    return this.employees.filter(emp =>
      (this.searchName === '' || emp.name.toLowerCase().includes(this.searchName.toLowerCase())) &&
      (this.searchEmail === '' || emp.email.toLowerCase().includes(this.searchEmail.toLowerCase())) &&
      (this.searchDepartment === '' || emp.departmentType === this.searchDepartment)
    );
  }

  exportReposrts(): void {
    this.employeeService.exportEmployeeReport().subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'employees_report.csv';
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

}
