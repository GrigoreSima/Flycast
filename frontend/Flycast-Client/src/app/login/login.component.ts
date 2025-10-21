import {Component, inject} from '@angular/core';
import {AuthenticationService} from '../integration/services/authentication/authentication.service';
import {SignInDTO} from '../integration/domain/sign-in-dto';
import {MessageService} from 'primeng/api';
import {Router} from '@angular/router';
import {LoadingSpinnerStore} from '../loading/store/loading-spinner.store';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers: [MessageService]
})
export class LoginComponent {

  protected signInDTO: SignInDTO;

  private messageService: MessageService = inject(MessageService);

  private loadingSpinnerStore: LoadingSpinnerStore = inject(LoadingSpinnerStore);

  private router: Router = inject(Router);

  private authenticationService: AuthenticationService = inject(AuthenticationService);

  constructor() {
    this.signInDTO = {username: "", password: ""}
  }


  async signIn() {
    if (this.signInDTO.username == "" || this.signInDTO.password == "") {
      this.messageService.add({
        severity: "error",
        summary: "Login failed",
        detail: "Please verify the credentials you provided!",
      });
      return;
    }
    try {
      this.loadingSpinnerStore.update({loading: true})

      await this.authenticationService.signIn(this.signInDTO);
    } catch (e) {
      this.messageService.add({
        severity: "error",
        summary: "Login failed",
        detail: "Please verify the credentials you provided!",
      });
    } finally {
      this.loadingSpinnerStore.update({loading: false})
    }

    await this.router.navigate([""]);
  }
}
