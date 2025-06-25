import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  constructor() { }

  // Handle HTTP errors
  handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unexpected error occurred';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Client error: ${error.error.message}`;
    } else {
      // Server-side error
      switch (error.status) {
        case 400:
          errorMessage = this.handleBadRequest(error);
          break;
        case 401:
          errorMessage = 'Unauthorized access. Please login again.';
          break;
        case 403:
          errorMessage = 'You do not have permission to access this resource.';
          break;
        case 404:
          errorMessage = 'The requested resource was not found.';
          break;
        case 409:
          errorMessage = 'There is a conflict in the data.';
          break;
        case 422:
          errorMessage = this.handleValidationErrors(error);
          break;
        case 429:
          errorMessage = 'Too many requests. Please try again later.';
          break;
        case 500:
          errorMessage = 'Server error. Please try again later.';
          break;
        default:
          errorMessage = `Server error: ${error.status}`;
      }
    }

    console.error('HTTP Error:', error);
    return throwError(() => new Error(errorMessage));
  }

  // Handle bad request errors (400)
  private handleBadRequest(error: HttpErrorResponse): string {
    if (error.error && error.error.message) {
      return error.error.message;
    }
    if (error.error && error.error.errors) {
      const errors = Object.values(error.error.errors).join(', ');
      return `Data errors: ${errors}`;
    }
    return 'Invalid data';
  }

  // Handle validation errors (422)
  private handleValidationErrors(error: HttpErrorResponse): string {
    if (error.error && error.error.errors) {
      const errors = Object.values(error.error.errors).join(', ');
      return `Validation errors: ${errors}`;
    }
    return 'Invalid data';
  }

  // Show error message to user
  showError(message: string): void {
    // Can use toast or alert
    alert(message);
  }

  // Show success message to user
  showSuccess(message: string): void {
    // Can use toast or alert
    alert(message);
  }

  // Handle network errors
  handleNetworkError(): string {
    return 'Network connection error. Please check your internet connection.';
  }

  // Handle timeout errors
  handleTimeoutError(): string {
    return 'Request timeout. Please try again.';
  }
} 