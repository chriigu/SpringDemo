import {Component, Input, OnInit, TemplateRef} from '@angular/core';
import {MatDrawer, MatDrawerContainer, MatSidenavContent} from '@angular/material/sidenav';
import {MatButton, MatFabAnchor} from '@angular/material/button';
import {RouterLink} from '@angular/router';
import {MatIcon} from '@angular/material/icon';
import {MatGridList, MatGridTile} from '@angular/material/grid-list';
import {UserSearchComponent} from '../../user-search/user-search.component';

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
    UserSearchComponent
  ],
  templateUrl: './side-nav-bar.component.html',
  standalone: true,
  styleUrl: './side-nav-bar.component.css'
})
export class SideNavBarComponent implements OnInit{
  @Input({required: true}) template: TemplateRef<any> | undefined;

  constructor() { }
  ngOnInit(): void {}
}
