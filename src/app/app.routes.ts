// app.routes.ts
import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { IncomeComponent } from './components/income/income.component';
import { ExpenseComponent } from './components/expense/expense.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent }, // Default route
  { path: 'income', component: IncomeComponent },
  { path: 'expense', component: ExpenseComponent }, // Expense route
];
