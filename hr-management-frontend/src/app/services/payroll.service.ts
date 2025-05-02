import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';
import { Payroll, PayrollPost } from '../models/payroll.model';

@Injectable({
    providedIn: 'root',
})
export class PayrollService {
    private apiUrl = `${environment.baseUrl}/payroll`;

    constructor(private http: HttpClient) { }

    getAllPayrolls(): Observable<Payroll[]> {
        return this.http.get<Payroll[]>(this.apiUrl);
    }

    getPayrollById(payrollId: number): Observable<Payroll> {
        return this.http.get<Payroll>(`${this.apiUrl}/${payrollId}`);
    }

    createPayroll(payroll: PayrollPost): Observable<Payroll> {
        return this.http.post<Payroll>(this.apiUrl, payroll);
    }

    updatePayroll(payroll: PayrollPost & { id: number }): Observable<Payroll> {
        return this.http.put<Payroll>(`${this.apiUrl}/${payroll.id}`, payroll);
    }

    deletePayroll(id: number): Observable<boolean> {
        return this.http.delete<boolean>(`${this.apiUrl}/${id}`);
    }
}
