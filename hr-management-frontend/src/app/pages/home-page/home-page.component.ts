import { Component } from '@angular/core';
import { ViewEmployeesComponent } from "../../components/view-employees/view-employees.component";

@Component({
  selector: 'app-home-page',
  imports: [ViewEmployeesComponent],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {

}
