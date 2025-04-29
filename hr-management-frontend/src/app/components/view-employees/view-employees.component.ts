import { Component } from '@angular/core';
import { EmployeeService } from '../services/employee.service';
import { Router } from '@angular/router';
import { Employee } from '../../models/employee.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-view-employees',
  imports: [CommonModule],
  templateUrl: './view-employees.component.html',
  styleUrl: './view-employees.component.css'
})
export class ViewEmployeesComponent {

  constructor(
    private employeeService : EmployeeService,
    private router: Router
  ) { }

  ngOnInit() {
    this.getAllEmployees();
  }

  employees: Employee[] = [];

  getAllEmployees() {
    this.employeeService.getAllEmployees().subscribe({
      next: (response) => {
        this.employees = response;        
      }
    })
  }

  updateEmployee(employee: Employee){

  }

  deleteEmployee(employeeId: number){

  }

}
