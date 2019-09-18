import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';

class PasswordConfirmErrorMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return (Boolean)(form.submitted || (control.touched && (form.hasError('differentPassword') || control.errors)));
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

  constructor(private formBuilder: FormBuilder) { }

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

  }
}
