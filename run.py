import os

from clarifai.rest import Image as ClImage, ClarifaiApp
import json


app = ClarifaiApp('3sV5ZqH5HPz-obqWgk8m7iBHHf41IDhHHr_wUGaT', '91Mg1OCiSKYMemuJWt5GBWmK-FKmxOQ3DJmarLuY')

# files = ['/out29.jpg']
# app.tag_urls(files)

model = app.models.get('aaa03c23b3724a16a56b629203edc62c')
list = []
resultlist = []
x = 1
while True:
    result = model.predict_by_filename('out%d.jpg' % x)
    for concept in result.get("outputs")[0].get("data").get("concepts"):
        print(concept)
    os.remove('out%d.jpg' % x)
    x = x + 1;

# for x in range(1, 32):
#     result = model.predict_by_filename('frames/frame%d.jpg' % x)
#     for concept in result.get("outputs")[0].get("data").get("concepts"):
#         #outcome = ("name=%s prob=%f" % (concept.get("name"), concept.get("value")))
#         list.append([concept])
#
#     for x in range(22, 33):
#         resultlist.append('frames/frame%d.jpg' % x)

# result = model.predict(resultlist, model_output_info=None)
# print(result)