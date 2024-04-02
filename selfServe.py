import requests
from datetime import datetime
import folium
import json


class connection():
    r"Connection class to handle HTTP requests and responses."
    def __init__(self):
        self_url = 'https://carterbeaudoin2.ddns.net'
        #self_url = 'http://localhost:8000'
        self.authentication_endpoint = self_url + "/token"
        self.iLogger_publish_endpoint = self_url + "/iLogger/publish_data/"
        self.iLogger_retrieve_endpoint = self_url + "/iLogger/return_data/"
        self.iLogger_create_zone_endpoint = self_url + "/iLogger/create_zone/"
        self.iLogger_current_zone_endpoint = self_url + "/iLogger/current_zone/"
        self.iLogger_get_zones_endpoint = self_url + "/iLogger/get_zones/"
        self.room_state_endpoint = self_url + "/flipswitch/"
        self.room_camera_picture_endpoint = self_url + '/roomCamera/takePicture/'
        self.getItemsID_endpoint = self_url + "/command/"
                
        self.authHeaders = {
            'accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        }

        self.headers = {
            'accept': 'application/json',
            'Content-Type': 'application/json'
        }

        self.data = {
            'grant_type': '',
            'username': 'carterb',
            'password': 'bvOBzJ1drvgtKAxOoIIFQLan6UEUYl2g5S8xOwZNHd',
            'scope': '',
            'client_id': '',
            'client_secret': ''
        }           


    def make_request(self, url, method='GET', authheader=None, params=None):

        headers = self.headers.copy()

        if authheader is not None:
            headers['Authorization'] = f"Bearer {authheader}"

        try:
            if method == 'GET':
                r = requests.get(url, headers=headers, data=self.data, timeout=30)
            elif method == 'POST':
                if params is not None:
                    r = requests.post(url, headers=headers, timeout=30, json=params)
                else:
                    r = requests.post(url, headers=headers, data=self.data, timeout=30)
                    
        except requests.exceptions.Timeout:
            print("Timed out")
            return None, None

        try:
            return r.json(),r.status_code
        except Exception as e:
            print(f"There has been an issue connecting to the server! | {e}")


    def authenticate(self):
        r"Authenticates your data with the API"
        response = requests.post(url=self.authentication_endpoint,headers=self.authHeaders,data=self.data)
        output = response.json()
        status_code = response.status_code
        if  status_code != 200:
            print('Authentication Failed',status_code)
        else:
            return output["access_token"]
            


    def iLogger_publish(self,auth,json):
        response,status = self.make_request(self.iLogger_publish_endpoint,method="POST",authheader=auth,params=json)

        return response, status
    def iLogger_retrieve(self,auth):
        response,status = self.make_request(self.iLogger_retrieve_endpoint,"GET",auth)

        return response, status
    def iLogger_create_zone(self,auth,name: str,latitude: float,longitude: float,radius: int):
        response,status = self.make_request(self.iLogger_create_zone_endpoint,method="POST",authheader=auth,params={"name":name,"latitude":latitude,"longitude":longitude,"radius":radius})

        return response, status

    def iLogger_current_zone(self,auth):
        response,status = self.make_request(self.iLogger_current_zone_endpoint,method="GET",authheader=auth)

        return response, status

    def iLogger_get_zones(self,auth):
        response,status = self.make_request(self.iLogger_get_zones_endpoint,method="GET",authheader=auth)

        return response, status

    def flipswitchAPI(self,auth,json):
        response,status = self.make_request(self.room_state_endpoint,method="POST",authheader=auth,params=json)

        return response, status

    def image(self,auth,json):
        response,status = self.make_request(self.room_camera_picture_endpoint,method="POST",authheader=auth,params=json)

        return response, status

    def sendcmd(self,auth,item = str):
        response, status = self.make_request(self.getItemsID_endpoint + item,"GET",auth)
        return response, status

class flipSwitch(connection):
    r"Allows setting/updating/reading/deletion of any flipswitch object on the API"
    def __init__(self):
        self.conn = connection().flipswitchAPI
    
    def setFalse(self, auth, title=str):
        r"Sets [title] flipSwitch to {False}"
        return self.conn(auth,{"title": title,"state": 0,"toggleTime": 0})
    
    def setTrue(self, auth, title=str):
        r"Sets [title] flipSwitch to {True}"
        return self.conn(auth,{"title": title,"state": 1,"toggleTime": 0})
    
    def toggle(self, auth, title=str):
        r"Toggles [title] flipSwitch"
        return self.conn(auth,{"title": title,"state": 2,"toggleTime": 0})

    def toggleForDuration(self, auth, title=str, duration = float):
        r"Toggles [title] flipSwitch for [duration] seconds"
        return self.conn(auth,{"title": title,"state": 3,"toggleTime": duration})

    def getState(self, auth, title=str):
        r"Gets [title] flipSwitch's state"
        return self.conn(auth,{"title": title,"state": 4,"toggleTime": 0})
    
    def getAllStates(self, auth):
        r"Toggles all flipSwitch states"
        return self.conn(auth,{"title": "","state": 5,"toggleTime": 0})

    def deleteState(self, auth, title=str):
        r"Deletes [title] flipSwitch"
        return self.conn(auth,{"title": title,"state": 6,"toggleTime": 0})


now = connection()

auth = now.authenticate()
 


print(now.iLogger_retrieve(auth))
print(now.iLogger_current_zone(auth))


'''#print(now.iLogger_create_zone(auth, 'Poo Mountain',38.793910690664845, -77.33642730389913,50))
#print(now.iLogger_current_zone(auth))
response, status = now.iLogger_get_zones(auth)
#map = folium.Map()
for item in response:
    print(item["Data"])
        #folium.GeoJson(data).add_to(map)
    
#map.save('map.html')
'''



'''switch = flipSwitch()
switch.setFalse(auth,'Test Switch')'''
'''
switch.toggle(auth,title='Flipswitch')

print(switch.getAllStates(auth))

switch.toggle(auth,title='DoorOpen')

print(switch.getAllStates(auth))


import json
import base64

data = {}
with open('balls.jpg', mode='rb') as file:
    img = file.read()

data['img'] = base64.b64encode(img).decode('utf-8')


import io
import base64
from PIL import Image

def string2image(string: str) -> Image.Image:

    img_bytes_arr = string.encode('utf-8')
    img_bytes_arr_encoded = base64.b64decode(img_bytes_arr)
    image = Image.open(io.BytesIO(img_bytes_arr_encoded))
    return image

picture, status_code = now.image(auth,data)

pic = string2image(picture["Image"])

pic.save("test.png")'''

"""idata = now.iLogger_retrieve(auth)
print(idata)"""