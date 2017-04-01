# Vaicam-Pai

app = Flask(__name__)
app.config['DEBUG'] = True
@app.route("/")
def hello():
    #app = ClarifaiApp('3sV5ZqH5HPz-obqWgk8m7iBHHf41IDhHHr_wUGaT', '91Mg1OCiSKYMemuJWt5GBWmK-FKmxOQ3DJmarLuY')
    app = ClarifaiApp('NkxsnTAHCgoxcrrbea3vNG-RLs2QFhVY1YTVikmR', 'gNTYHINf5WlI6BONf3QrSbrcw0wUIPz_Y-k8MN1l')

    model = app.models.get('aaa03c23b3724a16a56b629203edc62c')

    result = model.predict_by_filename('1.jpg')
    while True:
        x = 1

        for concept in result.get("outputs")[0].get("data").get("concepts"):

            taco = str(concept)
            result = model.predict_by_filename('%d.jpg' % x)
            print("predicting image %d.jpg" % x)
            x = x + 1;
            time.sleep (5)

        return render_template("hello.html", taco = taco)


if __name__ == "__main__":
    app.run()
