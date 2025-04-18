import {Component, Renderer2} from '@angular/core';
import {MatSlideToggle} from '@angular/material/slide-toggle';

@Component({
  selector: 'app-header',
  imports: [
    MatSlideToggle
  ],
  templateUrl: './header.component.html',
  standalone: true,
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  darkModeEnabled = true;

  constructor(private renderer: Renderer2) {
    this.renderer.addClass(document.body, 'dark'); // Dark mode is default
  }

  toggleDarkMode(event: any)
  {
    this.darkModeEnabled = event.checked;
    console.log('Dark mode enabled:', this.darkModeEnabled);
    if(this.darkModeEnabled)
    {
      this.renderer.addClass(document.body, 'dark');
    } else {
      this.renderer.removeClass(document.body, 'dark');
    }
  }
}
