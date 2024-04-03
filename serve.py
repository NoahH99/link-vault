import requests

linkName = "hello"

url = f'http://127.0.0.1:8000/api/file-storage/upload/{linkName}'
files = [('files', open('images/scr.png', 'rb')), ('files', open('images/balls2.png', 'rb'))]
resp = requests.post(url=url, files=files) 
print(resp.json())

url = f'http://127.0.0.1:8000/api/file-storage/delete/{linkName}'
resp = requests.get(url=url) 
print(resp.json())