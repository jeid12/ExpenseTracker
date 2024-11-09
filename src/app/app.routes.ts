// app.routes.ts
import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { IncomeComponent } from './components/income/income.component';
import { ExpenseComponent } from './components/expense/expense.component';
import { UpdateExpenseComponent } from './components/update-expense/update-expense.component';
import { UpdateIncomeComponent } from './components/update-income/update-income.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent }, // Default route
  { path: 'income', component: IncomeComponent },
  { path: 'expense', component: ExpenseComponent }, // Expense route
  { path: 'expense/:id/edit', component: UpdateExpenseComponent }, // Expense update Route
  { path: 'income/:id/edit', component: UpdateIncomeComponent }, // Income update Route
];
