import { TestBed } from '@angular/core/testing';

import { MeteorologicalStationService } from './meteorological-station.service';

describe('MeteorologicalStationService', () => {
  let service: MeteorologicalStationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MeteorologicalStationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
