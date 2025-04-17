import {Component} from '@angular/core';
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from '@angular/material/sidenav';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {MatFabAnchor} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-side-nav-bar',
  imports: [
    MatSidenavContainer,
    MatSidenavContent,
    MatFabAnchor,
    RouterLink,
    RouterLinkActive,
    MatIcon,
    MatSidenav,
    RouterOutlet
  ],
  templateUrl: './side-nav-bar.component.html',
  standalone: true,
  styleUrl: './side-nav-bar.component.css'
})
export class SideNavBarComponent {
}
