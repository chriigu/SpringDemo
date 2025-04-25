import {ApplicationConfig, DEFAULT_CURRENCY_CODE, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
import {routes} from './app.routes';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {MAT_TIMEPICKER_CONFIG} from '@angular/material/timepicker';
import {MomentDateAdapter} from '@angular/material-moment-adapter';
import {provideHttpClient} from '@angular/common/http';

export const MY_DATE_FORMATS = {
  parse: {
    dateInput: 'DD.MM.YYYY',
  },
  display: {
    dateInput: 'DD.MM.YYYY',
    monthYearLabel: 'MMMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};


export const appConfig: ApplicationConfig = {
  providers: [
    // 👇 Locale
    { provide: MAT_DATE_LOCALE, useValue: 'de-CH' },

    // 👇 Moment Date Adapter setup
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS },

    {provide: DEFAULT_CURRENCY_CODE, useValue: 'CHF'},
    {provide: MAT_TIMEPICKER_CONFIG, useValue: 'de-CH'},

    provideHttpClient(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes)]
};
