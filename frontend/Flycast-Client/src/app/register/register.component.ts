import {Component, inject, signal} from '@angular/core';
import {SignUpDTO} from '../integration/domain/sign-up-dto';
import {AuthenticationService} from '../integration/services/authentication/authentication.service';
import {AppUseMotives} from '../integration/domain/client-dto';
import {MenuItem, MenuItemCommandEvent, MessageService} from 'primeng/api';
import {Router} from '@angular/router';
import {LoadingSpinnerStore} from '../loading/store/loading-spinner.store';
import {EMAIL_FORMAT, PASSWORD_FORMAT} from '../utils/validation';


@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  providers: [MessageService]
})
export class RegisterComponent {
  protected signUpDTO: SignUpDTO;

  protected profilePicture: any = {
    id: 1,
    photo: "profile-" + 1
  };

  protected profilePictures: any[] = [];

  protected motives = [
    {text: 'I\'m a pilot with experience', value: AppUseMotives.AppUseMotivesType.FORECAST_FOR_FLYING},
    {text: 'I\'m learning METAR', value: AppUseMotives.AppUseMotivesType.LEARNING_METAR},
    {text: 'I just want to know the weather', value: AppUseMotives.AppUseMotivesType.WEATHER_CHECKING}
  ]

  private loadingSpinnerStore: LoadingSpinnerStore = inject(LoadingSpinnerStore);

  private messageService: MessageService = inject(MessageService);

  private router: Router = inject(Router);

  private authenticationService: AuthenticationService = inject(AuthenticationService);

  protected mandatoryMessageHidden = signal(true);
  visible: boolean = false;

  constructor() {
    for (let i = 1; i <= 3; i++) {
      this.profilePictures.push(
        {
          id: i,
          photo: "profile-" + i
        }
      );
    }

    this.signUpDTO = {
      id: "",
      username: "",
      password: "",
      profilePhoto: this.profilePicture.id,
      email: "",
      motive: AppUseMotives.AppUseMotivesType.FORECAST_FOR_FLYING
    }
  }


  async register() {
    if (!this.signUpDTO.email.match(EMAIL_FORMAT) ||
      !this.signUpDTO.password.match(PASSWORD_FORMAT)) {
      this.messageService.add({
        severity: "error",
        summary: "Register failed",
        detail: "Your email or password do not meet the criteria!",
      });
      return;
    }

    if (this.signUpDTO.username == "" ||
      this.signUpDTO.password == "" ||
      this.signUpDTO.email == ""
    ) {
      this.mandatoryMessageHidden.set(false);

      setTimeout(() => {
        this.mandatoryMessageHidden.set(true);
      }, 3500);

      this.messageService.add({
        severity: "error",
        summary: "Register failed",
        detail: "Please verify the info you provided!",
      });
      return;
    }

    try {
      this.loadingSpinnerStore.update({loading: true})

      await this.authenticationService.signUp(this.signUpDTO);
    } catch (e) {
      this.messageService.add({
        severity: "error",
        summary: "Register failed",
        detail: "Please verify the info you provided!",
      });
      return;
    } finally {
      this.loadingSpinnerStore.update({loading: false})
    }

    await this.router.navigate(["/"]);
  }

  chosePicture(item: any) {
    this.profilePicture = item
    this.signUpDTO.profilePhoto = item.id;
    this.visible = false;
  }
}
