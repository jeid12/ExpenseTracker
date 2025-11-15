import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private apiUrl = 'http://localhost:8080/api/stats';

  constructor(private http: HttpClient) {}

  generateReport(request: ReportRequest): Observable<Report> {
    return this.http.post<Report>(`${this.apiUrl}/reports`, request);
  }
}
