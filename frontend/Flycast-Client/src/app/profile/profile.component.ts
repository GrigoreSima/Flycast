import {Component, inject, OnInit, signal} from '@angular/core';
import {Router} from '@angular/router';
import {MenuItem, MenuItemCommandEvent, MessageService} from 'primeng/api';
import {AppUseMotives, ClientDTO} from '../integration/domain/client-dto';
import {ClientService} from '../integration/services/client/client.service';
import {SignUpDTO} from '../integration/domain/sign-up-dto';
import {LoadingSpinnerStore} from '../loading/store/loading-spinner.store';
import {EMAIL_FORMAT, PASSWORD_FORMAT} from '../utils/validation';

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
  providers: [MessageService]
})

export class ProfileComponent implements OnInit {

  items: MenuItem[] = [];

  clientDTO: SignUpDTO;

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

  private messageService: MessageService = inject(MessageService);

  private clientService: ClientService = inject(ClientService);

  private loadingSpinnerStore: LoadingSpinnerStore = inject(LoadingSpinnerStore);

  private router: Router = inject(Router);

  visible: boolean = false;
  editable = signal(false);

  constructor() {
    this.clientDTO = {
      id: "",
      username: "",
      password: "",
      profilePhoto: 1,
      email: "",
      motive: AppUseMotives.AppUseMotivesType.FORECAST_FOR_FLYING
    }

    for (let i = 1; i <= 3; i++) {
      this.profilePictures.push(
        {
          id: i,
          photo: "profile-" + i
        }
      );
    }

    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        route: "/",
      },
    ];
  }

  async ngOnInit() {
    try {
      this.loadingSpinnerStore.update({loading: true})

      let id = localStorage.getItem("client-id") ?? "";
      this.clientDTO = {
        ...await this.clientService.getById(id),
        password: ""
      };
    } catch (e) {
      this.messageService.add({
        severity: "error",
        summary: "Viewing profile failed",
        detail: "You are not logged in!",
      });
    } finally {
      this.loadingSpinnerStore.update({loading: false})
    }
  }

  chosePicture(item: any) {
    this.profilePicture = item
    this.clientDTO.profilePhoto = item.id;
    this.visible = false;
  }

  async SaveEditClick() {
    this.editable.set(!this.editable())

    if (!this.editable()) {
      if (!this.clientDTO.email.match(EMAIL_FORMAT) ||
        (this.clientDTO.password != "" && !this.clientDTO.password.match(PASSWORD_FORMAT))) {
        this.messageService.add({
          severity: "error",
          summary: "Saving profile failed",
          detail: "Your email or password do not meet the criteria!",
        });
        this.editable.set(!this.editable())
        return;
      }

      if (this.clientDTO.email == "") {
        this.messageService.add({
          severity: "error",
          summary: "Saving profile failed",
          detail: "Please verify the info you provided!",
        });
        this.editable.set(!this.editable())
        return;
      }

      try {
        this.loadingSpinnerStore.update({loading: true})

        this.clientDTO = {
          ...await this.clientService.update(this.clientDTO),
          password: ""
        };
      } catch (e) {
        this.messageService.add({
          severity: "error",
          summary: "Editing profile failed",
          detail: "Review the data you entered!",
        });
      } finally {
        this.loadingSpinnerStore.update({loading: false})
      }
    }
  }
}
