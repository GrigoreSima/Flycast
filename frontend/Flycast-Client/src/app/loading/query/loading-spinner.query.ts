import { Injectable } from '@angular/core';
import { Query } from '@datorama/akita';
import { LoadingSpinnerState, LoadingSpinnerStore } from '../store/loading-spinner.store';

@Injectable({ providedIn: 'root' })
export class LoadingSpinnerQuery extends Query<LoadingSpinnerState> {
  public constructor(protected override store: LoadingSpinnerStore) {
    super(store);
  }
}
