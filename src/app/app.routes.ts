// app.routes.ts
import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { IncomeComponent } from './components/income/income.component';
import { ExpenseComponent } from './components/expense/expense.component';
import { UpdateExpenseComponent } from './components/update-expense/update-expense.component';
import { UpdateIncomeComponent } from './components/update-income/update-income.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { CategoryManagementComponent } from './components/category-management/category-management.component';
import { UserReportComponent } from './components/user-report/user-report.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'income', component: IncomeComponent, canActivate: [authGuard] },
  { path: 'expense', component: ExpenseComponent, canActivate: [authGuard] },
  { path: 'expense/:id/edit', component: UpdateExpenseComponent, canActivate: [authGuard] },
  { path: 'income/:id/edit', component: UpdateIncomeComponent, canActivate: [authGuard] },
  { path: 'reports', component: UserReportComponent, canActivate: [authGuard] },
  { path: 'admin/dashboard', component: AdminDashboardComponent, canActivate: [authGuard] },
  { path: 'admin/categories', component: CategoryManagementComponent, canActivate: [authGuard] },
];
