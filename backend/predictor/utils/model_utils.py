import pickle

class ModelUtils:
    @staticmethod
    def saveModel(model, model_name, target):
        pickle.dump(
            obj = model, 
            file = open(f"./saved_models/{model_name}/{target}.pickle", 'wb'),
            protocol = pickle.HIGHEST_PROTOCOL
        )

    @staticmethod
    def trainModel(model, station_code, target):
        pickle.dump(
            obj = model, 
            file = open(f"./trained_models/{station_code}/{target}.pickle", 'wb'),
            protocol = pickle.HIGHEST_PROTOCOL
        )

    @staticmethod
    def loadModel(station_code, target):
        return pickle.load(
            file = open(f"./trained_models/{station_code}/{target}.pickle", 'rb'),
        )