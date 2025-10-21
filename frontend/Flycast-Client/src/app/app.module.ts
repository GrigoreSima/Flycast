import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {providePrimeNG} from 'primeng/config';

import Nora from '@primeng/themes/aura';
import {jwtInterceptor} from './utils/interceptors/jwt.interceptor';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {LoginComponent} from './login/login.component';
import {FormsModule} from '@angular/forms';
import {Toast} from 'primeng/toast';
import {Card} from 'primeng/card';
import {Password} from 'primeng/password';
import {InputText} from 'primeng/inputtext';
import {FloatLabel} from 'primeng/floatlabel';
import {Button, ButtonDirective, ButtonLabel} from 'primeng/button';
import {Ripple} from 'primeng/ripple';
import {RegisterComponent} from './register/register.component';
import {Select} from 'primeng/select';
import {Message} from 'primeng/message';
import {ProfileComponent} from './profile/profile.component';
import {Menubar} from 'primeng/menubar';
import {Avatar} from 'primeng/avatar';
import {Badge} from 'primeng/badge';
import {HomeComponent} from './home/home.component';
import {NavbarComponent} from './navbar/navbar.component';
import {Menu} from 'primeng/menu';
import {HistoryComponent} from './history/history.component';
import {DataViewModule} from "primeng/dataview";
import {ForecastComponent} from './forecast/forecast.component';
import {Tag} from 'primeng/tag';
import {Dialog} from "primeng/dialog";
import {ProgressSpinner} from "primeng/progressspinner";
import {GoogleMap, MapAdvancedMarker, MapMarker} from '@angular/google-maps';
import {Tooltip} from "primeng/tooltip";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    HomeComponent,
    NavbarComponent,
    HistoryComponent,
    ForecastComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        Toast,
        Card,
        Password,
        InputText,
        FloatLabel,
        Button,
        ButtonDirective,
        Ripple,
        ButtonLabel,
        Select,
        Message,
        Menubar,
        Avatar,
        Badge,
        Menu,
        DataViewModule,
        Tag,
        Dialog,
        ProgressSpinner,
        GoogleMap,
        MapMarker,
        MapAdvancedMarker,
        Tooltip,
    ],
  providers: [
    provideHttpClient(
      withInterceptors([jwtInterceptor])
    ),
    provideAnimationsAsync(),
    providePrimeNG({
      ripple: true,
      theme: {
        preset: Nora,
        options: {
          darkModeSelector: false, //'system',
          cssLayer: {
            name: 'primeng',
            order: 'theme, base, primeng'
          }
        }
      }
    })
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
