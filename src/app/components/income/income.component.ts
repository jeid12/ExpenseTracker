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

@Component({
  selector: 'app-income',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NzCardModule, NzFormModule, NzDatePickerModule, NzInputModule, NzSelectModule, NzIconModule], // Include NzIconModule
  templateUrl: './income.component.html',
  styleUrl: './income.component.scss'
})
export class IncomeComponent {
postIncome() {
throw new Error('Method not implemented.');
}
incomeForm!: FormGroup;
listofCategory: any[] = ['salary', 'business', 'gift','loan','family','youtube', 'other'];

constructor(private fb: FormBuilder,
  private message: NzMessageService,
  private router: Router
) {}

ngOnInit() {
  this.incomeForm = this.fb.group({
    title: [null, Validators.required],
    amount: [null, Validators.required],
    date: [null, Validators.required],
    category: [null, Validators.required],
    decription: [null, Validators.required],
  });
}

}
