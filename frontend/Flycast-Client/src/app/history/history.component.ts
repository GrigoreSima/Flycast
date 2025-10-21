import {Component, inject, OnInit} from '@angular/core';
import {MenuItem, MessageService} from 'primeng/api';
import {ForecastDTO, ReviewOptions} from '../integration/domain/forecast-dto';
import {ClientService} from '../integration/services/client/client.service';
import ReviewOptionsType = ReviewOptions.ReviewOptionsType;
import {LoadingSpinnerStore} from '../loading/store/loading-spinner.store';

@Component({
  selector: 'app-history',
  standalone: false,
  templateUrl: './history.component.html',
  styleUrl: './history.component.css',
  providers: [MessageService]
})
export class HistoryComponent implements OnInit {

  items: MenuItem[] = [];

  forecasts: ForecastDTO[] = [];

  private messageService: MessageService = inject(MessageService);

  private clientService: ClientService = inject(ClientService);

  private loadingSpinnerStore: LoadingSpinnerStore = inject(LoadingSpinnerStore);

  constructor() {
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
      this.forecasts = await this.clientService.getForecastsByClientId(id);
    } catch (e) {
      this.messageService.add({
        severity: "error",
        summary: "Viewing history failed",
        detail: "You are not logged in!",
      });
    } finally {
      this.loadingSpinnerStore.update({loading: false})
    }
  }

  getSeverity(item: ForecastDTO) {
    switch (item.review) {
      case ReviewOptionsType.WORST:
        return "danger";
      case ReviewOptionsType.BAD:
        return "warn";
      case ReviewOptionsType.GOOD:
        return "info";
      case ReviewOptionsType.BEST:
        return "success";
      default:
        return "";
    }
  }
}
