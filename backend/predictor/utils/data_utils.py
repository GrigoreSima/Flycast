from sklearn.model_selection import train_test_split
from utils.config_utils import ConfigurationUtils
import pandas as pd
import numpy as np

class DataUtils:
    @staticmethod
    def train_test_split(X, y):
        test_size = ConfigurationUtils.get_detail("data", "test_size")
        validation_size = ConfigurationUtils.get_detail("data", "validation_size")
        multiple_pred_size = ConfigurationUtils.get_detail("data", "multiple_pred_size")

        X_train, X_validation, \
        y_train, y_validation = train_test_split(
            X, 
            y, 
            test_size = validation_size + test_size, 
            shuffle = False
        )

        X_validation = X_validation[:-multiple_pred_size]
        y_validation = y_validation[:-multiple_pred_size]

        X_test = X_validation[-multiple_pred_size:]
        y_test = y_validation[-multiple_pred_size:]

        return X_train, X_validation, X_test, y_train, y_validation, y_test

    @staticmethod
    def get_data(target, station_code):
        path = ConfigurationUtils.get_detail("data", "path") + f"/{station_code}/{station_code}.csv"
        index = ConfigurationUtils.get_detail("data", "index")

        data = pd.read_csv(path)
        data = data.sort_values(by=index)

        features = [col for col in data.columns.to_list() if f"{target}_lag" in col]
        
        X = data[index + features]
        y = data[target]

        return X, y
    
    @staticmethod
    def get_lags(target, station_code):
        path = ConfigurationUtils.get_detail("data", "path") + f"/{station_code}/{station_code}.csv"
        data = pd.read_csv(path)
        features = [col for col in data.columns.to_list() if f"{target}_lag" in col]
        
        lags = data.iloc[-1][features]

        for i in range(len(features), 1, -1):
            lags[f"{target}_lag{i}"] = lags[f"{target}_lag{i-1}"]

        lags[f"{target}_lag1"] = data.iloc[-1][f"{target}"]

        return lags
    
    @staticmethod
    def format_lstm_data_train(data, target = [], length = 24):
        X = []
        for i in range(len(data) - length):
            X.append(data[i: i + length, :])

        if len(target) == 0:
            return np.array(X)
        
        return np.array(X), np.array(target[length : ])
    
    @staticmethod
    def format_lstm_data_predict(data):
        X = []
        for i in range(len(data)):
            X.append(data[i: i + 1, :])
        
        return np.array(X)