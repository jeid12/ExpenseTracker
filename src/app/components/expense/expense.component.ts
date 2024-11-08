import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms'; 
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select'; // Import NzSelectModule


@Component({
  selector: 'app-expense',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NzCardModule, NzFormModule, NzDatePickerModule, NzInputModule, NzSelectModule], // Include NzSelectModule
  templateUrl: './expense.component.html',
  styleUrls: ['./expense.component.scss']
})
export class ExpenseComponent {
  listofCategory: any[] = [
    'Food', 'Clothes', 'Entertainment', 'Travel', 'Education','Other',
  ];
submitForm() {
throw new Error('Method not implemented.');
}
  expenseForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.expenseForm = this.fb.group({
      title: [null, Validators.required],
      amount: [null, Validators.required],
      date: [null, Validators.required],
      category: [null, Validators.required],
      decription: [null, Validators.required], 
    });
  }
}
