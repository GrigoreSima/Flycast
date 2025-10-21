import {Component, inject, Input, OnInit} from '@angular/core';
import {MenuItem, MenuItemCommandEvent, MessageService} from 'primeng/api';
import {ClientService} from '../integration/services/client/client.service';
import {AuthenticationService} from '../integration/services/authentication/authentication.service';
import {Router} from '@angular/router';
import {AppUseMotives, ClientDTO} from '../integration/domain/client-dto';
import {LoadingSpinnerStore} from '../loading/store/loading-spinner.store';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  providers: [MessageService]
})
export class NavbarComponent implements OnInit {

  clientDTO: ClientDTO;
  @Input() items: MenuItem[] = [];
  profileItems: MenuItem[] = [];

  private messageService: MessageService = inject(MessageService);

  private authService = inject(AuthenticationService);

  private clientService: ClientService = inject(ClientService);

  private loadingSpinnerStore: LoadingSpinnerStore = inject(LoadingSpinnerStore);

  private router: Router = inject(Router);

  constructor() {
    this.clientDTO = {
      id: "",
      username: "",
      profilePhoto: 1,
      email: "",
      motive: AppUseMotives.AppUseMotivesType.FORECAST_FOR_FLYING
    }

    this.profileItems = [
      {
        icon: "pi pi-user",
        label: "View profile",
        routerLink: "/profile"
      },
      {
        icon: "pi pi-history",
        label: "History",
        routerLink: "/history"
      },
      {
        separator: true
      },
      {
        icon: "pi pi-sign-out",
        label: "Log out",
        command: async (event: MenuItemCommandEvent) => {
          this.authService.signOut();
          await this.router.navigate(["/login"]);
        }
      },
    ]
  }

  async ngOnInit() {
    try {
      this.loadingSpinnerStore.update({loading: true})

      let id = localStorage.getItem("client-id") ?? ""
      this.clientDTO = (await this.clientService.getById(id));
    } catch (e) {
      this.messageService.add({
        severity: "error",
        summary: "Register failed",
        detail: "Please verify the info you provided!",
      });
    } finally {
      this.loadingSpinnerStore.update({loading: false})
    }
  }
}
