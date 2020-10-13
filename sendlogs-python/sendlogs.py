import json
import time
import string
import requests
import logging
from random import *

logging.basicConfig(filename='sendlogs.log', level=logging.DEBUG)

json_template = json.loads(""" {
           "privateKey": "24cdd646-9f5d-3ff4-c567-d78496d27669",
           "applicationName": "PYTHON send logs",
           "subsystemName": "PYTHON subsystem name",
           "computerName": "PYTHON computer name",
           "logEntries": [
             {
               "timestamp": 1457827957703.342, 
               "severity": 4,
               "text": "Encountered an error while registering the user john123",
               "category": "DAL",
               "className": "UserManager",
               "methodName": "RegisterUser",
               "threadId": "a-352"
             }
           ]
        }""")

def generate():
    logEntry = {
    "timestamp": time.time() * 1000,
    "severity": randint(1, 6),
    "text" : 'Encountered an error while operation ' + ''.join(choice(string.ascii_letters + string.digits) for x in range(20)),
    "category": ''.join(choice(string.ascii_uppercase) for x in range(3)),
    "className": 'ClassName-' + ''.join(choice(string.ascii_letters) for x in range(10)),
    "methodName": 'MethodName-' + ''.join(choice(string.ascii_letters) for x in range(10)),
    "threadId" : 'x-' + str(randint(100, 999))
    }
    return logEntry

list = []
for x in range(0, 1001):
    list.append(generate())

json_template["logEntries"] = list
json_obj = json.dumps(json_template)
logging.info("JSON obj extended")
header = {'Content-type': 'application/json'}

for x in range(0, 3):
    r = None
    try:
        #r = requests.post("http://localhost:8080/api/v1/logs", data=json_obj, headers=header)
        r = requests.post("https://api.coralogix.com/api/v1/logs", data=json_obj, headers=header, verify=False)
        logging.info('POST request sent' + str(r.status_code) + ', ' +  str(r.reason) + ', ' + str(r.text))
    except requests.exceptions.RequestException as e:
        logging.info(e)

