from models.model import Model
from sklearn.ensemble import RandomForestRegressor

class RandomForestModel(Model):
    def __init__(self, params):
        self.model = RandomForestRegressor(**params)

    def train(self, X, y):
        self.model = self.model.fit(X, y)

    def predict(self, X):
        return self.model.predict(X)