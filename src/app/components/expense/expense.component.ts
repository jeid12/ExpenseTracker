// component.ts

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms'; 
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { ExpenseService } from '../../services/expense/expense.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzIconModule } from 'ng-zorro-antd/icon'; 

@Component({
  selector: 'app-expense',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NzCardModule, NzFormModule, NzDatePickerModule, NzInputModule, NzSelectModule, NzIconModule], // Include NzIconModule
  templateUrl: './expense.component.html',
  styleUrls: ['./expense.component.scss']
})
export class ExpenseComponent implements OnInit {
onDelete(_t81: any) {
throw new Error('Method not implemented.');
}
onEdit(_t81: any) {
throw new Error('Method not implemented.');
}
  listofCategory: string[] = [
    'Food', 'Clothes', 'Entertainment', 'Travel', 'Education', 'Other'
  ];
  expenses: any[] = []; // Ensure expenses is an array

  expenseForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private expenseService: ExpenseService,
    private message: NzMessageService
  ) {}

  ngOnInit() {
    this.getAllExpenses(); // Fetch expenses when the component initializes
    this.expenseForm = this.fb.group({
      title: [null, Validators.required],
      amount: [null, Validators.required],
      date: [null, Validators.required],
      category: [null, Validators.required],
      decription: [null, Validators.required],
    });
  }

  submitForm() {
    if (this.expenseForm.invalid) {
      return; // Prevent submission if the form is invalid
    }

    this.expenseService.postExpense(this.expenseForm.value).subscribe(
      (res) => {
        this.message.success('Expense added successfully');
        this.expenseForm.reset(); // Clear the form after submission
        this.getAllExpenses(); // Refresh the expenses list
      },
      (err) => {
        this.message.error('Error in saving expense', { nzDuration: 5000 });
      }
    );
  }

  getAllExpenses() {
    this.expenseService.getAllExpense().subscribe(
      (res) => {
        this.expenses = res; // Assign fetched expenses to `this.expenses`
      },
      (err) => {
        this.message.error('Error in fetching expenses', { nzDuration: 5000 });
      }
    );
  }
}
