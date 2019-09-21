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
  public entriesCheckInEditMode: boolean[] = [];
  public entriesCheckOutEditMode: boolean[] = [];
  public checkInEntryValue: string[] = [];
  public checkOutEntryValue: string[] = [];

  constructor(private httpService: HttpService) { }

  ngOnInit() {
    this.httpService.showCurrentEntries().subscribe(entries => {
      this.entries = entries;
      this.entries.forEach(() => {
        this.entriesCheckInEditMode.push(false);
        this.entriesCheckOutEditMode.push(false);
        this.checkInEntryValue.push('');
        this.checkOutEntryValue.push('');
      });
      this.doLoad = true;
    },
      () => {
        this.doLoad = true;
      });
  }

  public setEntryCheckInEditMode(id: number) {
    this.entriesCheckInEditMode[id] = true;
  }

  public setEntryCheckOutEditMode(id: number) {
    this.entriesCheckOutEditMode[id] = true;
  }

  public updateValueForCheckInEntry(event: any, id: number) {
    this.checkInEntryValue[id] = event.target.value;
  }

  public updateValueForCheckOutEntry(event: any, id: number) {
    this.checkOutEntryValue[id] = event.target.value;
  }

  public updateEntryCheckIn(id: number) {
    if (!this.checkInEntryValue[id]) {
      this.entriesCheckInEditMode[id] = false;
      return;
    }
    this.httpService.updateEntryCheckIn({ id: id + 1, checkIn: this.checkInEntryValue[id] }).subscribe(
      newEntry => {
        this.entries[id].checkIn = newEntry.checkIn;
        this.entriesCheckInEditMode[id] = false;
      });
  }

  public updateEntryCheckOut(id: number) {
    if (!this.checkOutEntryValue[id]) {
      this.entriesCheckOutEditMode[id] = false;
      return;
    }
    this.httpService.updateEntryCheckOut({ id: id + 1, checkOut: this.checkOutEntryValue[id] }).subscribe(
      newEntry => {
        this.entries[id].checkOut = newEntry.checkOut;
        this.entriesCheckOutEditMode[id] = false;
      });
  }

  public deleteAllEntries() {
    this.httpService.deleteAllCurrentUserEntries().subscribe(() => this.entries = null);
  }
}
