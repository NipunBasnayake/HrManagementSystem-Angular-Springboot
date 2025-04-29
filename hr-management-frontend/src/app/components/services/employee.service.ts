import { Injectable } from "@angular/core";
import { environment } from "../../../environment/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Employee } from "../../models/employee.model";

@Injectable({
    providedIn: 'root'
})

export class EmployeeService {
    private apiUrl = `${environment.baseUrl}/employee`;

    constructor(private http: HttpClient) { }

    getAllEmployees(): Observable<Employee[]> {
        return this.http.get<Employee[]>(`${this.apiUrl}`);
    }
}