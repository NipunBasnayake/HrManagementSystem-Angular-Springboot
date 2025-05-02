import { Employee } from './employee.model';

export interface Payroll {
  id?: number;
  employee: Employee;
  payDate: string;
  basicSalary: number;
  allowances?: number;
  deductions?: number;
  netSalary: number;
  createdAt?: string;
  updatedAt?: string;
}

export interface PayrollPost {
  employeeId: number;
  payDate: string;
  basicSalary: number;
  allowances?: number;
  deductions?: number;
  netSalary: number;
}
