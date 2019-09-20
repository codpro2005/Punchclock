import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { HttpService } from '../services/http.service';
import { User } from 'src/casting/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  @Output() userUpdate: EventEmitter<any> = new EventEmitter();
  doLoad: boolean;
  user: any;
  changeUserForm: FormGroup;
  responseError: string;
  success: boolean;

  constructor(private httpService: HttpService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.changeUserForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.httpService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.changeUserForm.controls.username.setValue(this.user.username);
      this.doLoad = true;
    });
  }

  public onSubmit() {
    this.responseError = null;
    this.success = null;
    if (this.changeUserForm.invalid) {
      return;
    }

    const newUser: User = {
      username: this.changeUserForm.value.username,
      password: this.changeUserForm.value.password,
    };

    this.httpService.updateCurrentUser(newUser).subscribe(user => {
      this.userUpdate.emit({
        ...this.user,
        username: user.username,
        password: user.password,
      });
      this.success = true;
    },
    (error: ErrorEvent) => this.responseError = error.message);
  }

}
