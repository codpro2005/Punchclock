import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationEnum } from 'src/casting/AuthenticationEnum';

@Component({
  selector: 'app-authenticate',
  templateUrl: './authenticate.component.html',
  styleUrls: ['./authenticate.component.scss']
})
export class AuthenticateComponent implements OnInit {
  public authenticationOption: AuthenticationEnum;
  public signInForm: FormGroup;
  public signUpForm: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.authenticationOption = AuthenticationEnum.SignIn;

    this.signInForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });

    this.signUpForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      passwordConfirm: ['', Validators.required],
    });
  }

  public setAuthenticationOption(authenticationOption: AuthenticationEnum) {
    this.authenticationOption = authenticationOption;
  }
}
