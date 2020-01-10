import { TestBed } from '@angular/core/testing';

import { CheckoutFormService } from './checkout-form.service';

describe('CheckoutFormService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CheckoutFormService = TestBed.get(CheckoutFormService);
    expect(service).toBeTruthy();
  });
});
