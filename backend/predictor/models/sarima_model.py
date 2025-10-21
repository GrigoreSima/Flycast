from models.model import Model
from statsmodels.tsa.statespace.sarimax import SARIMAX

class SARIMAModel(Model):
    def __init__(self, params):
        self.params = params

    def train(self, X, y):
        self.model = SARIMAX(y, **self.params)
        self.model = self.model.fit()

    def predict(self, X):
        return self.model.forecast(steps = len(X))