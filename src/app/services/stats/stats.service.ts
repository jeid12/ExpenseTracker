import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASIC_URL = 'https://expensetracker-gwmy.onrender.com/';

@Injectable({
  providedIn: 'root'
})
export class StatsService {

  constructor(private http: HttpClient) { }

  getStats(): Observable<any> {
    return this.http.get(BASIC_URL + 'api/stats');
  }
  getchart(): Observable<any> {
    return this.http.get(BASIC_URL + 'api/stats/chart');
  }
}
