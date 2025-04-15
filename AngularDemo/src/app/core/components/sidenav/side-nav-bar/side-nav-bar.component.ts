import {Component, Input, OnInit, TemplateRef} from '@angular/core';
import {
  MatDrawer,
  MatDrawerContainer,
  MatDrawerContent,
  MatSidenav, MatSidenavContainer,
  MatSidenavContent
} from '@angular/material/sidenav';
import {MatButton, MatFabAnchor} from '@angular/material/button';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MatIcon} from '@angular/material/icon';
import {MatGridList, MatGridTile} from '@angular/material/grid-list';
import {UserSearchComponent} from '../../user-search/user-search.component';
import {NgTemplateOutlet} from '@angular/common';

@Component({
  selector: 'app-side-nav-bar',
  imports: [
    MatDrawerContainer,
    MatButton,
    MatDrawer,
    MatFabAnchor,
    MatIcon,
    RouterLink,
    MatGridList,
    MatGridTile,
    MatSidenavContent,
    NgTemplateOutlet,
    RouterOutlet,
    MatDrawerContent,
    MatSidenav,
    MatSidenavContainer
  ],
  templateUrl: './side-nav-bar.component.html',
  standalone: true,
  styleUrl: './side-nav-bar.component.css'
})
export class SideNavBarComponent implements OnInit{

  constructor() { }
  ngOnInit(): void {}
}
