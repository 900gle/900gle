# import tensorflow as tf
import tensorflow_hub as hub
import tensorflow_text
import kss, numpy
import json

from flask import Flask, request
from flask_restful import reqparse, abort, Api, Resource

app = Flask(__name__)
api = Api(app)

def getTextVectors(input):
    vectors = module(input)
    return [vector.numpy().tolist() for vector in vectors]
#
class NumpyEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, numpy.ndarray):
            return obj.tolist()
        return json.JSONEncoder.default(self, obj)

class Vector(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('keyword', type=str)
            args = parser.parse_args()
            _keyword = args['keyword']
            return {'vectors': json.dumps(getTextVectors(_keyword)[0], cls=NumpyEncoder)}
        except Exception as e:
            return {'error': str(e)}
api.add_resource(Vector, '/vectors')

if __name__ == '__main__':
    module = hub.load("https://tfhub.dev/google/universal-sentence-encoder-multilingual-large/3")
    app.run(debug=True)
