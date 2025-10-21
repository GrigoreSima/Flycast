import { Injectable } from '@angular/core';
import { Store, StoreConfig } from '@datorama/akita';

export interface LoadingSpinnerState {
  loading: boolean;
}

export function createInitialState(): LoadingSpinnerState {
  return {
    loading: true,
  };
}

@Injectable({ providedIn: 'root' })
@StoreConfig({ name: 'loading-spinner' })
export class LoadingSpinnerStore extends Store<LoadingSpinnerState> {
  public constructor() {
    super(createInitialState());
  }
}
