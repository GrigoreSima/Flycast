import yaml
class ConfigurationUtils:
    configuration = yaml.safe_load(open("config.yaml"))
    prediction_configuration = yaml.safe_load(open("prediction_config.yaml"))
    params = yaml.safe_load(open("./models/params.yaml"))

    @staticmethod
    def get_configuration():
        return ConfigurationUtils.configuration
    
    @staticmethod
    def get_detail(*args):
        config = ConfigurationUtils.configuration

        for detail in args:
            config = config[detail]

        return config
    
    @staticmethod
    def get_prediction_configuration():
        return ConfigurationUtils.prediction_configuration
    
    @staticmethod
    def get_prediction_detail(*args):
        config = ConfigurationUtils.prediction_configuration

        for detail in args:
            config = config[detail]

        return config
    
    @staticmethod
    def save_prediction_config(target, model_name):
        config = ConfigurationUtils.prediction_configuration
        config[target]["model"] = model_name

        yaml.dump(config, open("prediction_config.yaml", 'w'))
    
    @staticmethod
    def get_params(model):
        return ConfigurationUtils.params[model]
    
    @staticmethod
    def save_params(model, key, value):
        params = ConfigurationUtils.params
        params[model][key] = value

        yaml.dump(params, open("./models/params.yaml", 'w'))