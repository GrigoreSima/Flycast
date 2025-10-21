from sklearn.metrics import root_mean_squared_error, mean_absolute_error, r2_score
from utils.config_utils import ConfigurationUtils
from datetime import datetime
import matplotlib.pyplot as plt
import pandas as pd

class PerformanceUtils:
    @staticmethod
    def get_evaluation_metrics(y, y_pred):
        rmse = root_mean_squared_error(y, y_pred)
        mae = mean_absolute_error(y, y_pred)
        r2 = r2_score(y, y_pred)
        return {'rmse': rmse,  # Best possible value is 0.0
                'mae': mae,  # Best possible value is 0.0
                'r2': r2,  # Best possible score is 1.0
                }
    
    @staticmethod
    def plot(ax, X, y, y_pred, title):
        index = ConfigurationUtils.get_detail("data", "index")
        index_len = len(index)
        base = pd.DataFrame(X)
        base = base[[i for i in range(index_len)]]

        datetime_label = 'Date & Time'
        target_label = f"Target {title}"
        prediction_label = f"Predicted {title}"

        if ("Time" in index):
            base[datetime_label] = base.apply(
            lambda x: datetime(
                year=int(x[0]), 
                month=int(x[1]), 
                day=int(x[2]), 
                hour=int(x[3])
                ), 
                axis=1
            )
        else: 
            base[datetime_label] = base.apply(
            lambda x: datetime(
                year=int(x[0]), 
                month=int(x[1]), 
                day=int(x[2]), 
                ), 
                axis=1
            )

        real = base
        real[target_label] = pd.DataFrame(y)
        real = real[[datetime_label, target_label]]
        real = real.set_index(datetime_label)

        pred = base
        pred[prediction_label] = pd.DataFrame(y_pred)
        pred = pred[[datetime_label, prediction_label]]
        pred = pred.set_index(datetime_label)

        real.plot(ax=ax)
        pred.plot(ax=ax)