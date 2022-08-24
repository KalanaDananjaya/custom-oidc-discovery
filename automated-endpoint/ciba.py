from flask import Flask,jsonify,make_response, request
import json
from flask import Response
import mysql.connector

import os

ASSETS_DIR = os.path.dirname(os.path.abspath(__file__))
app = Flask('app')

mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  password="root",
  database="openbank_apimgtdb_conformance"
)

mycursor = mydb.cursor()

@app.route('/automatedapproval', methods=["GET","POST"])
def automatedapproval():
    body = {}
    args = request.args
    action = args.get("action")
    if (action == "allow"):
      action = "AUTHENTICATED"
    if (action=="deny"):
      action = "CONSENT_DENIED"
    auth_req_id = args.get("token")
    query = "UPDATE IDN_OAUTH2_CIBA_AUTH_CODE SET AUTH_REQ_STATUS = '{action}', AUTHENTICATED_USER_NAME='admin@wso2.com', USER_STORE_DOMAIN='abc', TENANT_ID = 1, IDP_ID = 1 WHERE AUTH_REQ_ID = '{token}'".format(action = action, token = auth_req_id)
    print(query)
    mycursor.execute(query)
    mydb.commit()
    response = make_response(jsonify(body))
    response.headers['content-type'] = 'application/json'
    return response


context = ('cert.pem', 'key.pem')
app.run(host = '0.0.0.0', port = 9000, ssl_context=context)
