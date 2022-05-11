import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isAdmin = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.isAdmin.subscribe(
      (value) => {
        this.isAdmin = value;
      }
    );
  }

  doLogout() {
    this.userService.logout();
  }

}
