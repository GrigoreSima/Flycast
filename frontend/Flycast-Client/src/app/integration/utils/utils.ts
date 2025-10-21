import {lastValueFrom, Observable, take} from 'rxjs';

export function promiseFromObservable<T>(observable: Observable<T>): Promise<T> {
  return lastValueFrom(observable.pipe(take(1)));
}
