import requests
import logging
import time

# скрипт можно использовать для того, чтобы наполнить kafka топик сообщениями

url = "http://localhost:8080/tickets/new?type=MORTGAGE"

logging.basicConfig(format='%(asctime)s - %(message)s', level=logging.INFO)

counter = 0

for i in range(1):
    start = time.time()
    requests.get(url)
    end = time.time()
    elapsed = end - start
    logging.info(f"Send requests #{counter} to {url} for {elapsed * 1000} ms")
    counter += 1
