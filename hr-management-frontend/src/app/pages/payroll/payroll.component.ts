import { Component } from '@angular/core';
import { ViewPayrollsComponent } from "../../components/view-payrolls/view-payrolls.component";
import { NavBarComponent } from "../../components/nav-bar/nav-bar.component";

@Component({
  selector: 'app-payroll',
  imports: [ViewPayrollsComponent, NavBarComponent],
  templateUrl: './payroll.component.html',
  styleUrl: './payroll.component.css'
})
export class PayrollComponent {

}
