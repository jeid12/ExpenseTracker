import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { IncomeService } from '../../services/income/income.service';
import { CommonModule } from '@angular/common';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';

@Component({
  selector: 'app-update-income',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NzCardModule, NzFormModule, NzDatePickerModule, NzInputModule, NzSelectModule, NzIconModule],
  templateUrl: './update-income.component.html',
  styleUrl: './update-income.component.scss'
})
export class UpdateIncomeComponent {

id!: number ; // Add id property

  incomeForm!: FormGroup;
listofCategory: any[] = ['salary', 'business', 'gift','loan','family','youtube', 'other'];

constructor(private fb: FormBuilder,
  private message: NzMessageService,
  private router: Router,
  private incomeService: IncomeService,
  private activatedRoute: ActivatedRoute,
) {}

ngOnInit() {
  this.id = this.activatedRoute.snapshot.params['id'];
  this.incomeForm = this.fb.group({
    title: [null, Validators.required],
    amount: [null, Validators.required],
    date: [null, Validators.required],
    category: [null, Validators.required],
    decription: [null, Validators.required],
  });
  this.getIncomeById(); 
}


getIncomeById() {
  this.incomeService.getIncomeById(this.id).subscribe( (res) => {
    this.incomeForm.patchValue(res); // Populate the form with the response data
  }, (error) => {
    this.message.error('Error fetching income details', { nzDuration: 5000 });
  }
); 
}
submitForm() {
this.incomeService.updateIncome(this.id, this.incomeForm.value).subscribe(
  (res) => {
    this.message.success('Income updated successfully', { nzDuration: 5000 });
    this.router.navigate(['/income']);
  },
  (err) => {
    this.message.error('Error updating income', { nzDuration: 5000 });
  }
);
}

}
