import { Component } from '@angular/core';
import { ViewEmployeesComponent } from "../../components/view-employees/view-employees.component";

@Component({
  selector: 'app-employee',
  imports: [ViewEmployeesComponent],
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.css'
})
export class EmployeeComponent {

}
