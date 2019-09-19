import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { User } from 'src/casting/user';
import { HttpService } from '../../services/http.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {
  public signInForm: FormGroup;
  public responseError: string;
  @ViewChild('usernameInput') usernameInput: ElementRef;

  constructor(
    private formBuilder: FormBuilder,
    private httpService: HttpService,
    private router: Router,
    private cookieService: CookieService) { }

  ngOnInit() {
    this.signInForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });

    // this.usernameInput.nativeElement.focus();
  }

  public onSubmit() {
    if (this.signInForm.invalid) {
      return;
    }

    const user: User = {
      ...this.signInForm.value
    };

    this.httpService.getJWT(user).subscribe(
      resp => {
        const jwt = resp.headers.get(this.httpService.jwtKey);
        this.cookieService.set(this.httpService.jwtKey, jwt);
        this.httpService.jwt = jwt;
        this.router.navigateByUrl('');
      },
      (error: ErrorEvent) => this.responseError = error.message);
  }
}
