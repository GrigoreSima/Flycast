import pandas as pd
import requests
import datetime
import sys
import os

def main():

    if (len(sys.argv) <= 3):
        raise Exception("Not enough arguments in command line!\nUsage: *.py <code> <latitude> <longitude>")


    dir = "./data"

    end_date = (datetime.datetime.now() - datetime.timedelta(days = 2)).date().isoformat()

    start_date = end_date

    with open("./data/_last_date.txt", 'r') as last_date:
        start_date = datetime.date.fromisoformat(last_date.readline()).isoformat()

    if (start_date == end_date):
        return

    code = sys.argv[1]

    columns = [
            'soil_temperature_7_to_28cm', 
            'pressure_msl', 
            'relative_humidity_2m', 
            'wind_speed_100m', 
            'wind_direction_10m', 
            'cloud_cover', 
            'dew_point_2m'
            ]

    payload = {
        'latitude': sys.argv[2],
        'longitude': sys.argv[3],
        'start_date': start_date, 
        'end_date': end_date,
        'wind_speed_unit': 'ms',
        'hourly': columns,
        'timezone': 'auto'
    }

    r = requests.get("https://archive-api.open-meteo.com/v1/archive", params = payload)

    data = r.json()["hourly"]

    columns.append("time")

    df = pd.DataFrame(columns=columns)

    for column in columns:
        df[column] = data[column]

    if (f"{code}.csv" in os.listdir(dir)):
        ds = pd.read_csv(dir + '/' + code + ".csv")
        df = pd.concat([ds, df])

    df.to_csv(dir + '/' + code + ".csv", index = False)

main()