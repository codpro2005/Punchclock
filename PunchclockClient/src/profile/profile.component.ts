import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { HttpService } from '../services/http.service';
import { User } from 'src/casting/user';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  doLoad: boolean;
  user: User;
  changeUserForm: FormGroup;

  constructor(private httpService: HttpService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.httpService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.changeUserForm.controls.username.setValue(this.user.username);
      this.doLoad = true;
    });
    this.changeUserForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  public onSubmit() {
    if (this.changeUserForm.invalid) {
      return;
    }

    const newUser: User = {
      username: this.changeUserForm.value.username,
      password: this.changeUserForm.value.password
    };

    this.httpService.updateCurrentUser(newUser).subscribe(user => console.log(user));
  }

}
