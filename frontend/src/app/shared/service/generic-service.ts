import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';


export class GenericService<T> {

    http: HttpClient;
    baseUrl: string = 'http://localhost:8080/api';
    options = {
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": localStorage.getItem('token')
        }
    }
    constructor(uri: string, http: HttpClient) {
        this.baseUrl += uri;
        this.http = http;
    }
    fetchAll(): Observable<any> {
        console.log('Request ' + this.baseUrl);
        console.log('Heaers');
        console.log(localStorage.getItem('token'))
        return this.http.get<any>(this.baseUrl, this.options);
    }
    register(data: T): Observable<T> {
        console.log('Request ' + this.baseUrl + " " + JSON.stringify(data));
        return this.http.post<any>(this.baseUrl, data, this.options);
    }
    update(id: number, data: T): Observable<T> {
        console.log('Request ' + this.baseUrl + " " + JSON.stringify(data));
        return this.http.put<T>(`${this.baseUrl}/${id}`, data, this.options);
    }
    delete(id: number) {
        console.log(`Request: ${this.baseUrl}/${id}`);
        return this.http.delete<any>(`${this.baseUrl}/${id}`, this.options);
    }

}