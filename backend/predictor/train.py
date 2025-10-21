from models.model_factory import ModelFactory
from utils.config_utils import ConfigurationUtils
from utils.data_utils import DataUtils
from utils.model_utils import ModelUtils
from datetime import datetime
import os

time = datetime.now()

models_dict = ConfigurationUtils.get_prediction_configuration()

for file in os.listdir("./data"):
    if (file not in os.listdir("./trained_models")):
        os.mkdir(f"./trained_models/{file}")

    for target in models_dict.keys():
        X, y = DataUtils.get_data(target, file)
        X_train = X.to_numpy()
        y_train = y.to_numpy().flatten()

        model_name = ConfigurationUtils.get_prediction_detail(target, "model")
        model = ModelFactory.get_model(
            model_name, 
            ConfigurationUtils.get_params(model_name)
            )

        model.train(X_train, y_train)

        ModelUtils.trainModel(model, file, target)
        with open(f"./trained_models/{file}/_last_trained.txt", 'w') as saved:
            saved.write(f"Last trained: {time}")