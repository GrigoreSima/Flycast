from models.model import Model
import xgboost as xgb

class XGBoostModel(Model):
    def __init__(self, params):
        self.model = xgb.XGBRegressor(**params)

    def train(self, X, y):
        self.model = self.model.fit(X, y, verbose=False)

    def predict(self, X):
        return self.model.predict(X)