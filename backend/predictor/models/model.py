from abc import ABC, abstractmethod
from utils.config_utils import ConfigurationUtils
import numpy as np

class Model(ABC):
    @abstractmethod
    def train(self, X, y):
        pass

    @abstractmethod
    def predict(self, X):
        pass

    def predict_multiple(self, X):
        y = []
        multiple_pred_size = ConfigurationUtils.get_detail("data", "multiple_pred_size")
        
        for idx in range(multiple_pred_size):
            start = X[idx]

            pred = self.predict(np.array([start]))
            y.append(pred)

            if (idx + 1 < multiple_pred_size):
                for j in range(len(start) - 1, 4, -1):
                    X[idx + 1][j] = start[j-1]

                X[idx + 1][4] = pred

        return np.array(y)