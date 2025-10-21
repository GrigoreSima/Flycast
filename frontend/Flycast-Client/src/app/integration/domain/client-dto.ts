export interface ClientDTO {
  id: string;
  username: string;
  profilePhoto: number;
  email: string;
  motive: AppUseMotives.AppUseMotivesType;
}

export namespace AppUseMotives {
  export type AppUseMotivesType = 'FORECAST_FOR_FLYING' | 'LEARNING_METAR' | 'WEATHER_CHECKING'
  export const AppUseMotivesType = {
    FORECAST_FOR_FLYING: 'FORECAST_FOR_FLYING' as AppUseMotivesType,
    LEARNING_METAR: 'LEARNING_METAR' as AppUseMotivesType,
    WEATHER_CHECKING: 'WEATHER_CHECKING' as AppUseMotivesType
  }
}
