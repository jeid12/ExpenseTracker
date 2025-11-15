import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AdminStats {
  totalUsers: number;
  activeUsers: number;
  inactiveUsers: number;
  totalSystemIncome: number;
  totalSystemExpense: number;
  systemBalance: number;
  totalExpenseRecords: number;
  totalIncomeRecords: number;
  totalCategories: number;
  activeCategories: number;
}

export interface Report {
  totalIncome: number;
  totalExpense: number;
  balance: number;
  incomeCount: number;
  expenseCount: number;
  averageIncome: number;
  averageExpense: number;
  incomeByCategory: { [key: string]: number };
  expenseByCategory: { [key: string]: number };
  reportPeriod: string;
}

export interface ReportRequest {
  startDate: string;
  endDate: string;
}

export interface Category {
  id?: number;
  name: string;
  type: string;
  description?: string;
  isActive: boolean;
  createdAt?: string;
  updatedAt?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient) {}

  // Stats
  getAdminStats(): Observable<AdminStats> {
    return this.http.get<AdminStats>(`${this.apiUrl}/stats`);
  }

  // Reports
  getSystemReport(request: ReportRequest): Observable<Report> {
    return this.http.post<Report>(`${this.apiUrl}/reports/system`, request);
  }

  getPersonalReport(request: ReportRequest): Observable<Report> {
    return this.http.post<Report>(`${this.apiUrl}/reports/personal`, request);
  }

  // User Management
  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/users`);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/users/${id}`);
  }

  toggleUserStatus(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/users/${id}/toggle-status`, {});
  }

  // Category Management
  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/categories`);
  }

  getCategory(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.apiUrl}/categories/${id}`);
  }

  createCategory(category: Category): Observable<any> {
    return this.http.post(`${this.apiUrl}/categories`, category);
  }

  updateCategory(id: number, category: Category): Observable<any> {
    return this.http.put(`${this.apiUrl}/categories/${id}`, category);
  }

  deleteCategory(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/categories/${id}`);
  }

  toggleCategoryStatus(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/categories/${id}/toggle-status`, {});
  }
}
