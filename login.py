__author__ = '8422'
from flask import Flask, jsonify,request
import json

app = Flask(__name__)

user = 'sanjana'
passw = '123'

@app.route('/',methods=['post'])
def index():
    request_data = json.loads(request.data)
    dict1 = {}
    if request_data['name'].encode('utf-8') == user and request_data['pass'].encode('utf-8') == passw:
        dict1['status'] = 1
    else:
        dict1['status'] = 0

    dict1['message'] = "successful"
    print ("request_data")
    return jsonify(dict1)

@app.route('/login')
def index1():
    return "Hello, World test!"

if __name__ == '__main__':
    app.run(host= '0.0.0.0',debug=True)
    #app.run(debug=True)
