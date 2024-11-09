import { Component } from '@angular/core';
import { StatsService } from '../../services/stats/stats.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NzCardModule, NzFormModule, NzDatePickerModule, NzInputModule, NzSelectModule, NzIconModule], // Include NzIconModule
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  stats:any;
gridStyle={ width:'25%',
  textAlign: 'center',
 }
     constructor(private statsService: StatsService) { 
      this.getStats();
     }

     getStats() {
       this.statsService.getStats().subscribe(
         (res) => {
           console.log(res);
            this.stats = res;
         },
         (error) => {
           console.log(error);
         }
       );
     }
}
