import { Injectable, } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User, UserService } from 'src/app/core';

@Injectable()
export class UserItemResolver implements Resolve<User> {
  constructor(
    private userService: UserService,
    private router: Router,
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> {

    return this.userService.getItem(route.params['itemid'])
      .pipe(catchError((err) => this.router.navigateByUrl('/')));
  }
}
