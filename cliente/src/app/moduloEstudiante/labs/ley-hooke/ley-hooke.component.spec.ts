import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeyHookeComponent } from './ley-hooke.component';

describe('LeyHookeComponent', () => {
  let component: LeyHookeComponent;
  let fixture: ComponentFixture<LeyHookeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeyHookeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeyHookeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
