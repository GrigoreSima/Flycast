import {MeteorologicalStationDTO} from './meteorological-station-dto';

export interface ForecastDTO {
  id: string;
  meteorologicalStation: MeteorologicalStationDTO;
  date: Date;
  cloudiness: number;
  dewPoint: number;
  pressure: number;
  humidity: number;
  temperature: number;
  windDirection: number;
  windSpeed: number;
  review: ReviewOptions.ReviewOptionsType | null;
  metar: string;
}

export namespace ReviewOptions {
  export type ReviewOptionsType = 'BEST' | 'GOOD' | 'BAD' | "WORST"
  export const ReviewOptionsType = {
    BEST: 'BEST' as ReviewOptionsType,
    GOOD: 'GOOD' as ReviewOptionsType,
    BAD: 'BAD' as ReviewOptionsType,
    WORST: 'WORST' as ReviewOptionsType
  }
}
