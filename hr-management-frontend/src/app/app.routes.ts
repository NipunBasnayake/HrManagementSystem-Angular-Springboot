import { Routes } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { EmployeeComponent } from './pages/employee/employee.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: '', component: HomePageComponent },
    { path: 'login', component: LoginComponent },
    { path: 'home', component: HomePageComponent },
    { path: 'employee', component: EmployeeComponent, canActivate: [AuthGuard] },
    { path: '**', redirectTo: '/home' } 
];
