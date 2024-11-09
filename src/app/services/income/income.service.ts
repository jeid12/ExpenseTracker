import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASIC_URL = 'https://expensetracker-gwmy.onrender.com/';

@Injectable({
  providedIn: 'root'
})
export class IncomeService {

  constructor(private http: HttpClient) { }

  postIncome(incomeDTO: any): Observable<any> {
    return this.http.post(BASIC_URL + 'api/income', incomeDTO);
  } 
  getAllIncome(): Observable<any> {
    return this.http.get(BASIC_URL + 'api/income/all');
  }

  deleteIncome(id:number):Observable<any>{
    return this.http.delete(BASIC_URL+`api/income/${id}` )
 }
 getIncomeById(id:number):Observable<any>{
   return this.http.get(BASIC_URL+`api/income/${id}`)
 }

 updateIncome(id:number,incomeDTO:any):Observable<any>{
   return this.http.put(BASIC_URL+`api/income/${id}`,incomeDTO)
 }
}