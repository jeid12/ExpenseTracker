import { Component, ViewChild, ElementRef, AfterViewInit, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { StatsService } from '../../services/stats/stats.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';

import Chart from 'chart.js/auto';
import { CategoryScale } from 'chart.js';

Chart.register(CategoryScale);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NzCardModule,
    NzFormModule,
    NzDatePickerModule,
    NzInputModule,
    NzSelectModule,
    NzIconModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements AfterViewInit {

  stats: any;
  expenses: any;
  incomes: any;

  gridStyle = {
    width: '25%',
    textAlign: 'center',
  };

  @ViewChild('incomeLineChartRef') private incomeLineChartRef!: ElementRef;
  @ViewChild('expenseLineChartRef') private expenseLineChartRef!: ElementRef;

  constructor(
    private statsService: StatsService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  ngAfterViewInit() {
    if (isPlatformBrowser(this.platformId)) {
      // Only execute this in the browser
      this.getStats();
      this.getchartData();
    }
  }

  createLineChart() {
    if (this.incomeLineChartRef && this.incomeLineChartRef.nativeElement) {
      const incomectx = this.incomeLineChartRef.nativeElement.getContext('2d');
      new Chart(incomectx, {
        type: 'line',
        data: {
          labels: this.incomes.map((income: any) => income.date),
          datasets: [{
            label: 'Income',
            data: this.incomes.map((income: any) => income.amount),
            borderWidth: 1,
            backgroundColor: 'rgb(80,200,120)',
            borderColor: 'rgb(0,100,0)',
            
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });
    }

    if (this.expenseLineChartRef && this.expenseLineChartRef.nativeElement) {
      const expensectx = this.expenseLineChartRef.nativeElement.getContext('2d');
      new Chart(expensectx, {
        type: 'line',
        data: {
          labels: this.expenses.map((expense: any) => expense.date),
          datasets: [{
            label: 'Expense',
            data: this.expenses.map((expense: any) => expense.amount),
            borderWidth: 1,
            backgroundColor: 'rgb(255,99,132)',
            borderColor: 'rgb(255,0,0)'
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });
    }
  }

  getStats() {
    this.statsService.getStats().subscribe(
      (res) => {
        console.log(res);
        this.stats = res;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  getchartData() {
    this.statsService.getchart().subscribe(
      res => {
        if (res.expensesList != null && res.incomesList != null) {
          this.expenses = res.expensesList;
          this.incomes = res.incomesList;
          console.log(res);

          this.createLineChart();
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
