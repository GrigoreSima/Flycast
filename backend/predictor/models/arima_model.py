from models.model import Model
from statsmodels.tsa.arima.model import ARIMA

class ARIMAModel(Model):
    def __init__(self, params):
        self.params = params

    def train(self, X, y):
        self.model = ARIMA(y, **self.params)
        self.model = self.model.fit()

    def predict(self, X):
        return self.model.forecast(steps = len(X))