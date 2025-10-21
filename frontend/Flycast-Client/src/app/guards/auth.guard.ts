import {CanActivateFn, Router} from '@angular/router';
import {AuthenticationService} from '../integration/services/authentication/authentication.service';
import {inject} from '@angular/core';

export const authGuard: CanActivateFn = async (route, state) => {
  const authenticationService : AuthenticationService = inject(AuthenticationService);
  const router : Router = inject(Router);

  if (!authenticationService.isLoggedIn()) {
    await router.navigate(['/login']);
    return false;
  }
  return true;
};
