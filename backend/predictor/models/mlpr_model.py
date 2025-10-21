from models.model import Model
from sklearn.neural_network import MLPRegressor

class MLPRModel(Model):
    def __init__(self, params):
        self.model = MLPRegressor(**params)

    def train(self, X, y):
        self.model = self.model.fit(X, y)

    def predict(self, X):
        return self.model.predict(X)