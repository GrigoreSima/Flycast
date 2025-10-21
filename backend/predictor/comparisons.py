from models.model_factory import ModelFactory
from utils.config_utils import ConfigurationUtils
from utils.data_utils import DataUtils
from utils.performance_utils import PerformanceUtils
from utils.model_utils import ModelUtils
from datetime import datetime
from matplotlib.font_manager import FontProperties
import matplotlib.pyplot as plt
import pandas as pd
import os
import shutil

for folder in os.listdir("./runs"):
    if len(os.listdir("./runs/" + folder)) == 0:
        os.rmdir("./runs/" + folder)


eval_metrics = pd.DataFrame(columns=[
    "Model", 
    "RMS validation", 
    "MAE validation", 
    "R2 validation",
    "RMS test", 
    "MAE test", 
    "R2 test"
    ])

time = datetime.now()
folder = time.strftime('%Y%m%d%H%M%S')
if ConfigurationUtils.get_detail("save_to_file") == 1:
    os.mkdir(f"runs/{folder}")

    # for model_name in ConfigurationUtils.get_detail("model", "name"):
    #     if os.path.exists(f"./saved_models/{model_name}/"):
    #         shutil.rmtree(f"./saved_models/{model_name}/")
    #     os.mkdir(f"./saved_models/{model_name}/")

for idx, model_name in enumerate(ConfigurationUtils.get_detail("model", "name")):
    if not os.path.exists(f"./saved_models/{model_name}/"):
        os.mkdir(f"./saved_models/{model_name}/")

validation_size = ConfigurationUtils.get_detail("data", "validation_size")

for target in ConfigurationUtils.get_detail("data", "features"):
    X, y = DataUtils.get_data(target)
    X_train, X_validation, X_test, y_train, y_validation, y_test = DataUtils.train_test_split(X.to_numpy(), y.to_numpy().flatten())

    # ConfigurationUtils.save_params("LSTM", "columns", X.shape[1])
    # ConfigurationUtils.save_params("GRU", "columns", X.shape[1])

    models = {}
    results = {}

    for model in ConfigurationUtils.get_detail("model", "name"):
        params = ConfigurationUtils.get_params(model)
        if params is not None:
            params["validation_size"] = validation_size
        models[model] = ModelFactory.get_model(model, params)

    eval_metrics.drop(eval_metrics.index, inplace = True)

    fig, axs = plt.subplots(len(models) + 1, 1)
    fig.suptitle(f"Comparisons between models on {target} \n{time}")
    fig.set_figwidth(15)
    fig.set_figheight(17)

    for idx, (model_name, model) in enumerate(models.items()):
        idx += 1
        model.train(X_train, y_train)

        if ConfigurationUtils.get_detail("save_to_file") == 1:
            ModelUtils.saveModel(model, model_name, target)
            with open(f"./saved_models/{model_name}/_last_trained.txt", 'w') as file:
                file.write(f"Last trained: {time}\nTraining analysis in folder: runs/{folder}")

        y_pred = model.predict(X_validation)        
        y_pred = y_pred.flatten()


        y_pred_multiple = model.predict_multiple(X_test)[:len(y_test)]    
        y_pred_multiple = y_pred_multiple.flatten()

        # print(y_test)
        # print(y_pred_multiple)
        # print("\n\n")

        results[model_name] = {
            "validation" : y_pred,
            "test" : y_pred_multiple,
        }

        metrics_validation = PerformanceUtils.get_evaluation_metrics(y_validation, y_pred)
        metrics_test = PerformanceUtils.get_evaluation_metrics(y_test, y_pred_multiple)

        eval_metrics.loc[len(eval_metrics)] = [
            model_name, 
            metrics_validation["rmse"], 
            metrics_validation["mae"], 
            metrics_validation["r2"],
            metrics_test["rmse"], 
            metrics_test["mae"], 
            metrics_test["r2"]
            ]
        
        # ax = plt.subplot()
        # fig.set_figwidth(10)
        # fig.set_figheight(5)
        
        # PerformanceUtils.plot(ax, X_test, y_test, results[model_name]["test"], "test")
        # plt.show()

    # break

    eval_metrics.sort_values(
        by = [
            "R2 test",
            "R2 validation",
        ],
        ascending = False,
        inplace = True)

    for idx in range(1, len(eval_metrics) + 1):
        model_name = eval_metrics.iloc[idx - 1]["Model"]

        PerformanceUtils.plot(axs[idx], X_validation, y_validation, results[model_name]["validation"], "validation")
        PerformanceUtils.plot(axs[idx], X_test, y_test, results[model_name]["test"], "test")

        axs[idx].set_title(model_name)
        axs[idx].set_xlabel("")
    
    ConfigurationUtils.save_prediction_config(target, eval_metrics.iloc[0]["Model"])

    best_evals = {
        "rmse validation": eval_metrics["RMS validation"].min(),
        "mae validation": eval_metrics["MAE validation"].min(),
        "r2 validation": eval_metrics["R2 validation"].max(),
        "rmse test": eval_metrics["RMS test"].min(),
        "mae test": eval_metrics["MAE test"].min(),
        "r2 test": eval_metrics["R2 test"].max()
    }

    axs[0].axis('off')
    axs[0].axis('tight')

    table = axs[0].table(
        cellText = eval_metrics.values, 
        cellLoc = 'center',
        colLabels = eval_metrics.columns, 
        loc = 'center',
        )
    
    table.auto_set_font_size(False)
    table.set_fontsize(8)
    table.scale(1, 2)

    for (row, col), cell in table.get_celld().items():
        if (row == 0):
            cell.set_text_props(fontproperties = FontProperties(weight='bold'))

        for idx, eval in enumerate(best_evals.values()):
            if (col == idx + 1 and cell.get_text().get_text() == str(eval)):
                cell.set_text_props(color = 'g')


    axs[0].set_title("Evaluation metrics")
    # plt.tight_layout()
    plt.subplots_adjust(hspace = 0.75)

    if ConfigurationUtils.get_detail("save_to_file") == 0:
        plt.show()
    else:
        plt.savefig(f"runs/{folder}/{target}.png", format="png")