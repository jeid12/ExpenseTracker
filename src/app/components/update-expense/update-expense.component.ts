import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ExpenseService } from '../../services/expense/expense.service';
import { CommonModule } from '@angular/common';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';

@Component({
  selector: 'app-update-expense',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NzCardModule, NzFormModule, NzDatePickerModule, NzInputModule, NzSelectModule, NzIconModule],
  templateUrl: './update-expense.component.html',
  styleUrl: './update-expense.component.scss'
})
export class UpdateExpenseComponent {
  listofCategory: string[] = [
    'Food', 'Clothes', 'Entertainment', 'Travel', 'Education', 'Other'
  ];
  id!: number ; // Add id property
  expenses: any[] = []; // Ensure expenses is an array

  expenseForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private expenseService: ExpenseService,
    private message: NzMessageService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.expenseForm = this.fb.group({
      title: [null, Validators.required],
      amount: [null, Validators.required],
      date: [null, Validators.required],
      category: [null, Validators.required],
      decription: [null, Validators.required],
    });
    this.getExpenseById();
  }

  getExpenseById() {
    this.expenseService.getExpenseById(this.id).subscribe( (res) => {
      this.expenseForm.patchValue(res); // Populate the form with the response data
    }, (error) => {
      this.message.error('Error fetching expense details', { nzDuration: 5000 });
    }
  ); 
  } 
  submitForm() {
    this.expenseService.updateExpense(this.id, this.expenseForm.value).subscribe(
      (res) => {
        this.message.success('Expense updated successfully', { nzDuration: 5000 }); 
        this.expenseForm.reset();
        this.router.navigate(['/expense']); // Redirect to the expense page
      },
      (err) => {
        this.message.error('Error in updating expense', { nzDuration: 5000 });  
      }
    );
  }
}
