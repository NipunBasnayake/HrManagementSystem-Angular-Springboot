import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPayrollsComponent } from './view-payrolls.component';

describe('ViewPayrollsComponent', () => {
  let component: ViewPayrollsComponent;
  let fixture: ComponentFixture<ViewPayrollsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewPayrollsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewPayrollsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
