import {Component, inject, OnInit} from '@angular/core';
import {updatePreset, updatePrimaryPalette, updateSurfacePalette} from '@primeng/themes';
import {LoadingSpinnerQuery} from './loading/query/loading-spinner.query';
import {Observable} from 'rxjs';
import {LoadingSpinnerState, LoadingSpinnerStore} from './loading/store/loading-spinner.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'Flycast-Client';

  public loading$: Observable<LoadingSpinnerState>;
  private loadingSpinnerStore = inject(LoadingSpinnerStore);

  public constructor(
    private loadingSpinnerQuery: LoadingSpinnerQuery
  ) {
    this.loading$ = this.loadingSpinnerQuery.select();
    this.loadingSpinnerStore.update({ loading: false})
  }


  ngOnInit() {
    updatePreset({
      semantic: {
        colorScheme: {
          light: {
            primary: {
              accentColor: '{rose.500}',
              color: '{sky.500}',
              hoverColor: '{sky.400}',
              activeColor: '{sky.500}'
            },
          }
        }
      }
    });

    updatePrimaryPalette({
      50: '{sky.50}',
      100: '{sky.100}',
      200: '{sky.200}',
      300: '{sky.300}',
      400: '{sky.400}',
      500: '{sky.500}',
      600: '{sky.600}',
      700: '{sky.700}',
      800: '{sky.800}',
      900: '{sky.900}',
      950: '{sky.950}',
    });

    updateSurfacePalette({
      50: '{slate.50}',
      100: '{slate.100}',
      200: '{slate.200}',
      300: '{slate.300}',
      400: '{slate.400}',
      500: '{slate.500}',
      600: '{slate.600}',
      700: '{slate.700}',
      800: '{slate.800}',
      900: '{slate.900}',
      950: '{slate.950}',
    });
  }
}
