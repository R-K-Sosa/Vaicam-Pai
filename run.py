import youtube_dl
import cv2
# from clarifai.rest import Image as ClImage, ClarifaiApp
import json

with youtube_dl.YoutubeDL({}) as ydl:
   ydl.download(['https://www.youtube.com/watch?v=sGEEGDIHDRc'])

vidcap = cv2.VideoCapture('video.mp4')
success,image = vidcap.read()
count = 0
success = True
while success:
  success,image = vidcap.read()
  print('Read a new frame: ', success)
  cv2.imwrite("frames/frame%d.jpg" % count, image)
  count += 1
# app = ClarifaiApp('3sV5ZqH5HPz-obqWgk8m7iBHHf41IDhHHr_wUGaT', '91Mg1OCiSKYMemuJWt5GBWmK-FKmxOQ3DJmarLuY')
#
# files = ['/frames/frame29.jpg']
# app.tag_urls(files)
#
# model = app.models.get('aaa03c23b3724a16a56b629203edc62c')
# list = []
# resultlist = []
# for x in range(22, 32):
#     result = model.predict_by_filename('frames/frame%d.jpg' % x)
#     for concept in result.get("outputs")[0].get("data").get("concepts"):
#         #outcome = ("name=%s prob=%f" % (concept.get("name"), concept.get("value")))
#         list.append([concept])
#
# for x in range(22, 33):
#     resultlist.append('frames/frame%d.jpg' % x)
#
# result = model.predict(resultlist, model_output_info=None)
# print(result)