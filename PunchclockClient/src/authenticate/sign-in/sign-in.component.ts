import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { User } from 'src/casting/user';
import { HttpService } from '../../app/http.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {
  public signInForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private httpService: HttpService) { }

  ngOnInit() {
    this.signInForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  public onSubmit() {
    if (this.signInForm.invalid) {
      return;
    }

    const user: User = {
      ...this.signInForm.value
    };

    this.httpService.getJWT(user).subscribe(resp => console.log(resp));
  }
}
