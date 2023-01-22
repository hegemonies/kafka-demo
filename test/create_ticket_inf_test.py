import requests
import logging
import time
import random

# скрипт можно использовать для того, чтобы наполнить kafka топик сообщениями

types = {"MORTGAGE", "SALARY", "ACCOUNT", "PAY_BILL", "MAIL_SERVICE"}

def getRandomType():
    return random.choice(tuple(types))

url = "http://localhost:8080/tickets/new"

logging.basicConfig(format='%(asctime)s - %(message)s', level=logging.INFO)

counter = 0

for i in range(1000):
    _url = url + "?type=" + getRandomType()
    start = time.time()
    requests.get(_url)
    end = time.time()
    elapsed = end - start
    logging.info(f"Send requests #{counter} to {_url} for {elapsed * 1000} ms")
    counter += 1
