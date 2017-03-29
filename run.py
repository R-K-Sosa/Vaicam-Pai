from flask import Flask
import requests
import os
from clarifai.rest import Image as ClImage, ClarifaiApp

app = Flask(__name__)

@app.route("/")
def hello():
    app = ClarifaiApp('3sV5ZqH5HPz-obqWgk8m7iBHHf41IDhHHr_wUGaT', '91Mg1OCiSKYMemuJWt5GBWmK-FKmxOQ3DJmarLuY')

    model = app.models.get('aaa03c23b3724a16a56b629203edc62c')
    list = []
    resultlist = []
    x = 1
    while True:
        result = model.predict_by_filename('out%d.png' % x)
        for concept in result.get("outputs")[0].get("data").get("concepts"):
            return str(concept)
        os.remove('out%d.png' % x)
        x = x + 1;

if __name__ == "__main__":
    app.run()