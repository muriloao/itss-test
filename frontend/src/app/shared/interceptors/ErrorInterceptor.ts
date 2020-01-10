import { Injectable } from "@angular/core";
import {
  HttpEvent,
  HttpRequest,
  HttpHandler,
  HttpInterceptor,
  HttpErrorResponse
} from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError, retry } from "rxjs/operators";
import { Router } from '@angular/router';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private router: Router) { }
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      retry(2),
      catchError((error: HttpErrorResponse) => {

        if (error.status == 401) {
          console.log('sem sessão')
          // 401 handled in auth.interceptor
          this.router.navigate(['/']);
          localStorage.setItem('token', '');
        } else {
          return throwError(error);
        }
      })
    );
  }
}
