from models.mlpr_model import MLPRModel
from models.linear_reg_model import LinearRegressionModel
from models.xgb_model import XGBoostModel
from models.random_forest_model import RandomForestModel
from models.decision_tree_model import DecisionTreeModel
# from models.arima_model import ARIMAModel
# from models.sarima_model import SARIMAModel
# from models.lstm_model import LSTMModel
# from models.gru_model import GRUModel

class ModelFactory:
    @staticmethod
    def get_model(model, params = {}):
        if params == None:
            params = {}
        match(model):
            case "MLPRegressor":
                return MLPRModel(params)
            case "LinearRegression":
                return LinearRegressionModel(params)
            case "XGBRegressor":
                return XGBoostModel(params)
            case "RandomForestRegressor":
                return RandomForestModel(params)
            case "DecisionTreeRegressor":
                return DecisionTreeModel(params)
            # case "ARIMA":
            #     return ARIMAModel(params)
            # case "SARIMA":
            #     return SARIMAModel(params)
            # case "LSTM":
            #     return LSTMModel(params)
            # case "GRU":
            #     return GRUModel(params)
