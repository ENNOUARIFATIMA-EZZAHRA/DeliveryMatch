import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('login', () => {
    it('should send POST request to login endpoint', () => {
      const credentials = { email: 'test@example.com', motDePass: 'password123' };
      const mockResponse = { token: 'mock-token', user: { id: 1, email: 'test@example.com' } };

      service.login(credentials).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/auth/login`);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(credentials);
      req.flush(mockResponse);
    });
  });

  describe('register', () => {
    it('should send POST request to register endpoint', () => {
      const userData = { 
        nom: 'Test', 
        prenom: 'User', 
        email: 'test@example.com', 
        motDePass: 'password123',
        role: 'CONDUCTEUR'
      };
      const mockResponse = { token: 'mock-token', user: { id: 1, email: 'test@example.com' } };

      service.register(userData).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/auth/register`);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(userData);
      req.flush(mockResponse);
    });
  });

  describe('isAuthenticated', () => {
    it('should return true when token exists in localStorage', () => {
      localStorage.setItem('jwt_token', 'mock-token');
      expect(service.isAuthenticated()).toBe(true);
    });

    it('should return false when token does not exist in localStorage', () => {
      expect(service.isAuthenticated()).toBe(false);
    });
  });

  describe('getCurrentUser', () => {
    it('should return user object when user exists in localStorage', () => {
      const mockUser = { id: 1, email: 'test@example.com', role: 'CONDUCTEUR' };
      localStorage.setItem('currentUser', JSON.stringify(mockUser));
      expect(service.getCurrentUser()).toEqual(mockUser);
    });

    it('should return null when user does not exist in localStorage', () => {
      expect(service.getCurrentUser()).toBeNull();
    });
  });

  describe('getUserRole', () => {
    it('should return user role when user exists', () => {
      const mockUser = { id: 1, email: 'test@example.com', role: 'CONDUCTEUR' };
      localStorage.setItem('currentUser', JSON.stringify(mockUser));
      expect(service.getUserRole()).toBe('CONDUCTEUR');
    });

    it('should return null when user does not exist', () => {
      expect(service.getUserRole()).toBeNull();
    });

    it('should return null when user has no role', () => {
      const mockUser = { id: 1, email: 'test@example.com' };
      localStorage.setItem('currentUser', JSON.stringify(mockUser));
      expect(service.getUserRole()).toBeNull();
    });
  });

  describe('logout', () => {
    it('should clear token and user from localStorage', () => {
      localStorage.setItem('jwt_token', 'mock-token');
      localStorage.setItem('currentUser', JSON.stringify({ id: 1 }));
      
      service.logout();
      
      expect(localStorage.getItem('jwt_token')).toBeNull();
      expect(localStorage.getItem('currentUser')).toBeNull();
    });
  });

  describe('updateCurrentUser', () => {
    it('should update user data in localStorage', () => {
      const mockUser = { id: 1, email: 'test@example.com' };
      const updatedUser = { id: 1, email: 'updated@example.com' };
      
      localStorage.setItem('currentUser', JSON.stringify(mockUser));
      service.updateCurrentUser(updatedUser);
      
      expect(JSON.parse(localStorage.getItem('currentUser') || '{}')).toEqual(updatedUser);
    });
  });
}); 