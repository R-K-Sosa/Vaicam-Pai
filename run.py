from flask import Flask, jsonify
import requests
import glob
from clarifai.rest import ClarifaiApp
import json

import os
app = ClarifaiApp('3sV5ZqH5HPz-obqWgk8m7iBHHf41IDhHHr_wUGaT', '91Mg1OCiSKYMemuJWt5GBWmK-FKmxOQ3DJmarLuY')

app = Flask(__name__)
app.config['DEBUG'] = True

@app.route("/")
def hello():
    wordbank = {}
    app = ClarifaiApp('UzbdCFxqhjb6JL94ogmugfWnfEKAJ7WGSeOe1GiQ', '_RkOzOu9L0ZTdVdIGKQcZKSuAnamobafUWZwpP8H')

    model = app.models.get('bc2c1be334f44095b8c214cdf2dc8fbe')

    newest = min(glob.iglob('*.png'), key=os.path.getctime)

    result = model.predict_by_filename(newest)

    # wordbank = (result.get("outputs")[0].get("data").get("concepts")[0].get("name")) + "         "+ str(result.get("outputs")[0].get("data").get("concepts")[0].get("value")) + "}"


    wordbank = (result.get("outputs")[0].get("data").get("concepts")[0])
    return jsonify(wordbank )

if __name__ == "__main__":
    app.run()