import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReportService, Report, ReportRequest } from '../../services/report/report.service';

@Component({
  selector: 'app-user-report',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-report.component.html',
  styleUrl: './user-report.component.scss'
})
export class UserReportComponent {
  report: Report | null = null;
  loading = false;
  error = '';
  currentDate = new Date();
  
  startDate: string = '';
  endDate: string = '';

  constructor(private reportService: ReportService) {
    // Set default dates (last 30 days)
    const today = new Date();
    const lastMonth = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
    
    this.endDate = this.formatDate(today);
    this.startDate = this.formatDate(lastMonth);
  }

  generateReport(): void {
    if (!this.startDate || !this.endDate) {
      this.error = 'Please select both start and end dates';
      return;
    }

    if (new Date(this.startDate) > new Date(this.endDate)) {
      this.error = 'Start date must be before end date';
      return;
    }

    this.loading = true;
    this.error = '';

    const request: ReportRequest = {
      startDate: this.startDate,
      endDate: this.endDate
    };

    this.reportService.generateReport(request).subscribe({
      next: (data) => {
        this.report = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to generate report';
        this.loading = false;
        console.error('Error generating report:', error);
      }
    });
  }

  getObjectKeys(obj: any): string[] {
    return obj ? Object.keys(obj) : [];
  }

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
