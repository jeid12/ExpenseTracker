import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { IncomeService } from '../../services/income/income.service';

@Component({
  selector: 'app-income',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NzCardModule, NzFormModule, NzDatePickerModule, NzInputModule, NzSelectModule, NzIconModule], // Include NzIconModule
  templateUrl: './income.component.html',
  styleUrl: './income.component.scss'
})
export class IncomeComponent {
deleteIncome(arg0: any) {
throw new Error('Method not implemented.');
}
updateIncome(arg0: any) {
throw new Error('Method not implemented.');
}
incomes: any[] = []; // Ensure expenses is an array

incomeForm!: FormGroup;
listofCategory: any[] = ['salary', 'business', 'gift','loan','family','youtube', 'other'];

constructor(private fb: FormBuilder,
  private message: NzMessageService,
  private router: Router,
  private incomeService: IncomeService,
) {}

ngOnInit() {
  this.getAllIncome(); // Fetch expenses when the component initializes
  this.incomeForm = this.fb.group({
    title: [null, Validators.required],
    amount: [null, Validators.required],
    date: [null, Validators.required],
    category: [null, Validators.required],
    decription: [null, Validators.required],
  });
}
submitForm() {  
  if (this.incomeForm.invalid) {
    return; // Prevent submission if the form is invalid
  }
  this.incomeService.postIncome(this.incomeForm.value).subscribe(
    (res) => {
      this.message.success('Income added successfully', { nzDuration: 5000 });
      this.getAllIncome();
    },
    (error) => {
      this.message.error('Error adding income', { nzDuration: 5000 });
    }
  );  
}

getAllIncome() {
  this.incomeService.getAllIncome().subscribe(  
    (res) => {
      this.incomes= res;
    }, (error) => { 
      this.message.error('Error fetching income details', { nzDuration: 5000 });
    } 
  );
  }

}
