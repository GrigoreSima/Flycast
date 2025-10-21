from utils.config_utils import ConfigurationUtils
from utils.model_utils import ModelUtils
from utils.data_utils import DataUtils
import datetime
import numpy as np
import math
import sys
import json
import copy

def denormalize(number, mean, std):
    return (float(number) * std) + mean

if (len(sys.argv) <= 1):
    raise Exception("Not enough arguments in command line!\nUsage: *.py <year> <month> <day> <hour>")

input = [int(element) for element in sys.argv[1:]]
input.append(0)

input = np.array(input)

models_dict = ConfigurationUtils.get_prediction_configuration()

no_pred = 5

predictions = [{}] * no_pred

train_mean = json.load(open("data/train_mean.txt"))
train_std = json.load(open("data/train_std.txt"))

for target in models_dict.keys():
    # target = "Temperature"
    model = ModelUtils.loadModel(models_dict[target]["model"], target)
    
    lags = np.array(DataUtils.get_lags(target))
    date = datetime.datetime(input[0], input[1], input[2], 0)

    mean = train_mean[target]
    std = train_std[target]

    print(target)

    for idx in range(no_pred):
        print(np.concatenate([input, lags]))
        prediction = copy.deepcopy(predictions[idx])

        prediction["date"] = copy.deepcopy(date.isoformat())

        pred = model.predict(np.array([np.concatenate([input, lags])]))[0]

        ndigits = ConfigurationUtils.get_prediction_detail(target, "rounding")
        if ndigits > -1:
            prediction[target] = round(
                number = denormalize(pred, mean, std), 
                ndigits = ndigits
            ) 
        else:
            prediction[target] = denormalize(pred, mean, std)

        max_value = ConfigurationUtils.get_prediction_detail(target, "max_value")
        if prediction[target] >= denormalize(max_value, mean, std):
                prediction[target] = denormalize(max_value, mean, std)

        min_value = ConfigurationUtils.get_prediction_detail(target, "min_value")
        if prediction[target] <= denormalize(min_value, mean, std):
            prediction[target] = denormalize(min_value, mean, std)

        print(prediction[target])

        predictions[idx] = copy.deepcopy(prediction)

        # new prediction

        date += datetime.timedelta(hours = 1)

        input = [date.year, date.month, date.day, date.hour]

        for j in range(len(lags) - 1, 0, -1):
            lags[j] = lags[j-1]

        lags[0] = pred

    # break

# for idx, predX in enumerate(predictions):
#     print(f"Pred {idx}: {predX}")

for prediction in predictions:
    prediction["Wind direction"] = round((360 + math.atan2(prediction["Wy"], prediction["Wx"]) 
                                    * 180 / math.pi) % 360)
    prediction["Wind speed"] = round(math.sqrt(prediction["Wx"] ** 2 + prediction["Wy"] ** 2), 2)

    prediction.pop("Wx")
    prediction.pop("Wy")

for idx, predX in enumerate(predictions):
    print(f"Pred {idx}: {predX}")

# print(prediction_without_limits)
# json.dump(
#     obj = prediction, 
#     fp = open(f"prediction.txt", 'w'),
# )