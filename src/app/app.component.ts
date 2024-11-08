import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NzLayoutModule } from 'ng-zorro-antd/layout';  // Import the NzLayoutModule

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NzLayoutModule],  // Add NzLayoutModule here
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']  // Corrected styleUrl to styleUrls for proper Angular syntax
})
export class AppComponent {
  title = 'expenseFront';
}
