from models.model import Model
from utils.data_utils import DataUtils
import tensorflow as tf

class GRUModel(Model):
    def __init__(self, params):
        self.epochs = params['epochs']
        self.batch_size = params['batch_size']
        self.timesteps = params['timesteps']
        self.columns = params['columns']
        self.dropout = params['dropout']
        self.validation_size = params["validation_size"]

        self.model = tf.keras.models.Sequential([
            tf.keras.layers.Input(shape = (self.timesteps, self.columns)),

            tf.keras.layers.GRU(64, return_sequences=True),
            tf.keras.layers.GRU(64, return_sequences=True),
            tf.keras.layers.GRU(64, return_sequences=True),
            tf.keras.layers.Dense(32, activation='relu'),
            tf.keras.layers.Dropout(self.dropout),
            tf.keras.layers.Dense(32, activation='relu'),
            tf.keras.layers.Dense(units=1)
        ])

        self.model.compile(
            loss=tf.keras.losses.MeanSquaredError(),
            optimizer=tf.keras.optimizers.SGD(),
            metrics=[tf.keras.metrics.RootMeanSquaredError(), tf.keras.metrics.MeanAbsoluteError()]
        )

    def train(self, X, y):
        X, y = DataUtils.format_lstm_data_train(X, y, self.timesteps)
        self.model.fit(
            X, 
            y,
            epochs = self.epochs,
            batch_size = self.batch_size,
            validation_split = self.validation_size,
            shuffle = False,
        )

    def predict(self, X):
        return self.model.predict(DataUtils.format_lstm_data_predict(X))
        
        