from models.model import Model
from sklearn.linear_model import LinearRegression

class LinearRegressionModel(Model):
    def __init__(self, params):
        self.model = LinearRegression(**params)

    def train(self, X, y):
        self.model = self.model.fit(X, y)

    def predict(self, X):
        return self.model.predict(X)