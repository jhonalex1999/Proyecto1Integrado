import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaidaLibreComponent } from './caida-libre.component';

describe('CaidaLibreComponent', () => {
  let component: CaidaLibreComponent;
  let fixture: ComponentFixture<CaidaLibreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CaidaLibreComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CaidaLibreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
