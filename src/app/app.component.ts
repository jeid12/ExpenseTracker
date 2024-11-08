import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RouterModule, Routes } from '@angular/router'; // Import RouterModule and Routes
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzIconModule } from 'ng-zorro-antd/icon'; // Import NzIconModule
import { DashboardOutline, FundOutline, FallOutline } from '@ant-design/icons-angular/icons'; // Import specific icons

// Import your components
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { IncomeComponent } from './components/income/income.component';
import { ExpenseComponent } from './components/expense/expense.component';

// Define routes
const routes: Routes = [
  { path: '', component: DashboardComponent },  // Default route
  { path: 'income', component: IncomeComponent },
  { path: 'expense', component: ExpenseComponent }
];

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, NzLayoutModule, NzMenuModule, NzIconModule], // Include RouterModule and configure routes
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'expenseFront';
}
