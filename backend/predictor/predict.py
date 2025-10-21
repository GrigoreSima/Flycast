from utils.config_utils import ConfigurationUtils
from utils.model_utils import ModelUtils
from utils.data_utils import DataUtils
import datetime
import numpy as np
import math
import sys
import json

def denormalize(number, mean, std):
    return (float(number) * std) + mean

if (len(sys.argv) <= 4):
    raise Exception("Not enough arguments in command line!\nUsage: *.py <year> <month> <day> <code>")

input = np.array([int(element) for element in sys.argv[1:4]])
date = datetime.date(input[0], input[1], input[2])
code = sys.argv[4]

models_dict = ConfigurationUtils.get_prediction_configuration()
prediction_without_limits = {
    "date": date.isoformat()
}
prediction = {
    "date": date.isoformat()
}

train_mean = json.load(open(f"data/{code}/train_mean.txt"))
train_std = json.load(open(f"data/{code}/train_std.txt"))

for target in models_dict.keys():
    model = ModelUtils.loadModel(code, target)

    lags = np.array(DataUtils.get_lags(target, code))

    pred = model.predict(np.array([np.concatenate([input, lags])]))[0]

    mean = train_mean[target]
    std = train_std[target]

    ndigits = ConfigurationUtils.get_prediction_detail(target, "rounding")
    if ndigits > -1:
        prediction[target] = round(
            number = denormalize(pred, mean, std), 
            ndigits = ndigits
        ) 
    else:
        prediction[target] = denormalize(pred, mean, std)

    prediction_without_limits[target] = prediction[target]

    max_value = ConfigurationUtils.get_prediction_detail(target, "max_value")
    if prediction[target] >= denormalize(max_value, mean, std):
            prediction[target] = denormalize(max_value, mean, std)

    min_value = ConfigurationUtils.get_prediction_detail(target, "min_value")
    if prediction[target] <= denormalize(min_value, mean, std):
        prediction[target] = denormalize(min_value, mean, std)

    
prediction["Wind direction"] = round((360 + math.atan2(prediction["Wy"], prediction["Wx"]) 
                                      * 180 / math.pi) % 360)
prediction["Wind speed"] = round(math.sqrt(prediction["Wx"] ** 2 + prediction["Wy"] ** 2), 2)

prediction.pop("Wx")
prediction.pop("Wy")

renamings = {
    "date" : "date",
    "Clouds" : "cloudiness",
    "Dew point" : "dewPoint",
    "Pressure" : "pressure",
    "Relative Humidity" : "humidity",
    "Temperature" : "temperature",
    "Wind direction" : "windDirection",
    "Wind speed" : "windSpeed",
}

for key in renamings.keys():
    prediction[renamings[key]] = prediction.pop(key)

json.dump(
    obj = prediction, 
    fp = sys.stdout,
)