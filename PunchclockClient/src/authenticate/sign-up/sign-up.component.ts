import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { HttpService } from '../../app/http.service';
import { User } from 'src/casting/user';
import { Router } from '@angular/router';
import { AuthenticationPath } from 'src/casting/AuthenticationPath';

class PasswordConfirmErrorMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return (Boolean)((form.submitted || control.touched) && (form.hasError('differentPassword') || control.errors));
  }
}

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {
  public signUpForm: FormGroup;
  public passwordErrorMatcher = new PasswordConfirmErrorMatcher();
  public responseSuccess: boolean;
  public responseError: string;

  constructor(private formBuilder: FormBuilder, private httpService: HttpService, private router: Router) { }

  ngOnInit() {
    this.signUpForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      passwordConfirm: ['', Validators.required],
    }, { validators: [this.passwordConfirmed] });

    this.passwordConfirmed.bind(this);
  }

  private passwordConfirmed(formGroup: FormGroup): any {
    return formGroup.value.password === formGroup.value.passwordConfirm ? null : { differentPassword: true };
  }

  public onSubmit() {
    if (this.signUpForm.invalid) {
      return;
    }

    const user: User = {
      username: this.signUpForm.value.username,
      password: this.signUpForm.value.password,
    };

    this.httpService.createUser(user).subscribe(
      () => {
        this.responseError = undefined;
        this.responseSuccess = true;
        setTimeout(() => this.router.navigateByUrl(AuthenticationPath.SignIn), 1000);
      },
      (error: ErrorEvent) => this.responseError = error.error.error);
  }
}
