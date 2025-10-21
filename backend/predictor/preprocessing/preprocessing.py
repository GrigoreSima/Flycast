### Imports

import numpy as np
import pandas as pd
import os
import yaml
import json
import datetime


### Making a whole dataset (concat xls files)

dir = "./data"
for file in os.listdir(dir):
    if file[-4:] != '.csv': continue
        
    ds = pd.read_csv(dir + "/" + file)

    ### Column modifiers
    #### Transforming values

    ds["time"] = ds["time"].apply(lambda date : datetime.datetime.fromisoformat(date))
    ds["Year"] = ds["time"].apply(lambda date : date.year)
    ds["Month"] = ds["time"].apply(lambda date : date.month)
    ds["Day"] = ds["time"].apply(lambda date : date.day)
    ds["time"] = ds["time"].apply(lambda date : date.hour)
    ds["cloud_cover"] = ds["cloud_cover"].apply(lambda percentage : round((percentage / 100) * 8))

    wv = ds.pop("wind_speed_100m")

    # Convert to radians.
    wd_rad = ds.pop("wind_direction_10m")*np.pi / 180

    # Calculate the wind x and y components.
    ds['Wx'] = wv * np.cos(wd_rad)
    ds['Wy'] = wv * np.sin(wd_rad)


    #### Renaming columns

    ds = ds.rename(columns={
        "soil_temperature_7_to_28cm" : "Temperature",
        "relative_humidity_2m" : "Relative Humidity",
        "pressure_msl" : "Pressure",
        "wind_direction_10m" : "Wind direction",
        "wind_speed_100m" : "Wind speed",
        "cloud_cover" : "Clouds",
        "dew_point_2m" : "Dew point",
        "time" : "Time"
        })


    #### Eliminate the time element

    config = yaml.safe_load(open("../config.yaml"))

    index = config["data"]["index"]
    if "Time" not in index:
        # ds = ds[ds["Time"] == 12]
        ds = ds.drop("Time", axis=1)

        ds = ds.groupby(["Year", "Month", "Day"]).mean()
        ds = ds.reset_index()


    #### Sorting and reindexing

    ds = ds.set_index(index)
    ds = ds.sort_index()
    ds = ds.reset_index()


    ### Feature Analysis

    ds = ds.set_index(index)

    #### Normalization

    column_indices = {name: i for i, name in enumerate(ds.columns)}

    test_size = round(config["data"]["test_size"], 2)
    validation_size = round(config["data"]["validation_size"], 2)
    train_size = round(1 - test_size - validation_size, 2)

    train_ds = ds[0:int(len(ds) * train_size)]

    train_mean = train_ds.mean()
    train_std = train_ds.std()

    ds = (ds  - train_mean) / train_std

    if (file[:-4] not in os.listdir("../data")):
        os.mkdir(f"../data/{file[:-4]}")

    json.dump(
        obj = train_mean.to_dict(), 
        fp = open(f"../data/{file[:-4]}/train_mean.txt", 'w'),
    )

    json.dump(
        obj = train_std.to_dict(), 
        fp = open(f"../data/{file[:-4]}/train_std.txt", 'w'),
    )


    ### Lag creation

    def make_lags(ds, column, no):
        lags = pd.DataFrame()

        for i in range(1, no + 1):
            lags[f"{column}_lag{i}"] = ds[column].shift(i)

        return lags.bfill()

    columns = ds.columns
    ds = ds.reset_index()
    lags = {
        "Temperature" : 3,
        "Relative Humidity" : 1,
        "Pressure" : 1,
        "Wx" : 1,
        "Wy" : 1,
        "Clouds" : 1,
        "Dew point": 2
        }

    for col in columns:
        ds = ds.join(make_lags(ds, col, lags[col]), validate='1:1')


    ### Saving the dataset

    with open("./data/_last_date.txt", 'w') as last_date_file:
        last_date = ds.tail(1)
        last_date_file.write("{:02d}-{:02d}-{:02d}".format(last_date['Year'].values[0], last_date['Month'].values[0], last_date['Day'].values[0]))

    ds = ds.set_index(index)

    ds.to_csv(f"../data/{file[:-4]}/{file}")
    params = yaml.safe_load(open("../models/params.yaml"))

    params['dataset']['rows'] = ds.shape[0]
    params['dataset']['columns'] = ds.shape[1]

    yaml.dump(params, open("../models/params.yaml", 'w'))
    prediction_config = yaml.safe_load(open("../prediction_config.yaml"))

    for column in ds:
        if "_lag" not in column:
            prediction_config[column]['max_value'] = float(ds[column].max())
            prediction_config[column]['min_value'] = float(ds[column].min())

    yaml.dump(prediction_config, open("../prediction_config.yaml", 'w'))