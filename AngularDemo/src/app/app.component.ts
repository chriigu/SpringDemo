import {Component} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {filter} from 'rxjs';
import {HomeComponent} from './core/components/home/home.component';
import {SideNavBarComponent} from './core/components/sidenav/side-nav-bar.component';
import {HeaderComponent} from './core/components/header/header.component';

@Component({
  selector: 'app-root',
  imports: [HomeComponent, SideNavBarComponent, HeaderComponent],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'AngularDemo';
  showHomepage = true;


  constructor(private router: Router) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        let urlAfterRedirects = event.urlAfterRedirects;
        console.log('URL changed to:', urlAfterRedirects);
        this.showHomepage = urlAfterRedirects === '/'; // Can't use AppRoutes.HOME here
      });
  }

}
