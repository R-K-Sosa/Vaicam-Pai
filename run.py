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
    app = ClarifaiApp('3sV5ZqH5HPz-obqWgk8m7iBHHf41IDhHHr_wUGaT', '91Mg1OCiSKYMemuJWt5GBWmK-FKmxOQ3DJmarLuY')

    model = app.models.get('aaa03c23b3724a16a56b629203edc62c')

    newest = min(glob.iglob('*.jpg'), key=os.path.getctime)

    result = model.predict_by_filename(newest)

    wordbank.update(result.get("outputs")[0].get("data").get("concepts")[0])

    return jsonify(wordbank)

if __name__ == "__main__":
    app.run()