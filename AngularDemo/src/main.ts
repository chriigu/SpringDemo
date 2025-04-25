import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

import moment from 'moment';
import 'moment/locale/de-ch';

moment.locale('de-ch');
bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));

