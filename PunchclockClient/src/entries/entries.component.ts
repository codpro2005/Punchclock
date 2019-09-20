import { HttpService } from './../services/http.service';
import { Component, OnInit } from '@angular/core';
import { Entry } from 'src/casting/entry';

@Component({
  selector: 'app-entries',
  templateUrl: './entries.component.html',
  styleUrls: ['./entries.component.scss']
})
export class EntriesComponent implements OnInit {
  public doLoad: boolean;
  public entries: Entry[];

  constructor(private httpService: HttpService) { }

  ngOnInit() {
    this.httpService.showCurrentEntries().subscribe(entries => {
      this.entries = entries;
      this.doLoad = true;
    });
  }

}
