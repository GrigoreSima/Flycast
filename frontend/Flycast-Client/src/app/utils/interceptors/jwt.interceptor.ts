import {HttpInterceptorFn} from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  let token = localStorage.getItem('token');

  if (token) {
    req.headers.set('Authorization', `Bearer ${token}`);
  }

  return next(req);
};
