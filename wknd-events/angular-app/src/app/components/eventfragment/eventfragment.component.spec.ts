import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventfragmentComponent } from './eventfragment.component';

describe('EventfragmentComponent', () => {
  let component: EventfragmentComponent;
  let fixture: ComponentFixture<EventfragmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventfragmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventfragmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
