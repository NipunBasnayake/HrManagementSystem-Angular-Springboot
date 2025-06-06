# **HR Management System**

## **Overview**
The HR Management System is a comprehensive web application designed to streamline human resource operations. Built with Angular frontend and Spring Boot backend, it offers secure authentication via JWT and Spring Security, while providing a modern UI experience through Angular Material and Bootstrap CSS.

---

## **Features**

### **Employee Management**:
- Add, update, delete, and view employee records
- Department and role assignment
- Employee search and filtering capabilities
- Document management for employee files
- Generate employee reports

### **Payroll Management**:
- Salary processing and management
- Tax calculations and deductions
- Bonus and incentive management
- Salary history tracking
- Generate payroll reports

### **Leave Management**:
- Leave request submission and approval workflow
- Multiple leave type support (sick, vacation, personal)
- Leave balance tracking
- Calendar view of team availability
- Generate leave reports

### **Security Features**:
- JWT-based authentication and authorisation
- Role-based access control
- Secure password handling with encryption
- Session management
- API security with Spring Security

---

## **Technologies Used**

### **Backend**:
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT Authentication
- RESTful API design

### **Frontend**:
- Angular
- Angular Material
- Bootstrap CSS
- SweetAlert
- Responsive design

### **Database**:
- MySQL Database
- JPA/Hibernate ORM

---

## **System Architecture**

The application follows a modern microservices architecture:

1. **Frontend Layer**: Angular-based SPA with responsive design
2. **API Gateway**: Manages authentication and routes requests
3. **Service Layer**: RESTful APIs for different modules
4. **Data Access Layer**: JPA repositories for database operations
5. **Security Layer**: JWT token validation and role-based authorization

---

## **API Documentation**

The system provides RESTful APIs for all operations:

- **Authentication APIs**: `/api/auth/*`
- **Employee APIs**: `/api/employees/*`
- **Payroll APIs**: `/api/payroll/*`
- **Leave Management APIs**: `/api/leaves/*`

Detailed API documentation is available via Swagger UI at `/swagger-ui.html` when the application runs.

---

## **Screenshots**

![Dashboard](Screenshots/dashboard.png)
![Login](Screenshots/login.png)
![Employee Management](Screenshots/employee-management.png)
![Payroll System](Screenshots/payroll-system.png)
![Leave Management](Screenshots/leave-management.png)

---

## **License**

This project is licensed under the MIT License - see the LICENSE file for details.

---

## **Contact**

For questions or support, please contact:
- Email: [nipunsathsara1999@gmail.com](mailto:nipunsathsara1999@gmail.com)
- GitHub: [NipunBasnayake](https://github.com/NipunBasnayake)
