from models.model import Model
from sklearn.tree import DecisionTreeRegressor

class DecisionTreeModel(Model):
    def __init__(self, params):
        self.model = DecisionTreeRegressor(**params)

    def train(self, X, y):
        self.model = self.model.fit(X, y)

    def predict(self, X):
        return self.model.predict(X)