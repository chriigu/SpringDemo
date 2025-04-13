import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';

export interface Tile {
  cols: number;
  rows: number;
  text: string;
}

@Component({
  selector: 'app-home',
  imports: [RouterLink, MatIconModule, MatDividerModule, MatButtonModule, MatGridListModule],
  templateUrl: './home.component.html',
  standalone: true,
  styleUrl: './home.component.css'
})
export class HomeComponent {
  tiles: Tile[] = [
    {text: '<a mat-fab extended routerLink="." class="nav-link-button">\n' +
        '    <mat-icon>home</mat-icon>\n' +
        '    Home\n' +
        '  </a>', cols: 5, rows: 1},
    {text: 'Home', cols: 1, rows: 2},
    {text: 'Users', cols: 1, rows: 1},
    {text: '', cols: 2, rows: 1},
  ];
}
