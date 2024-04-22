from typing import List
from fastapi import FastAPI, File, UploadFile
import os
import asyncio
import json


app = FastAPI()

def update_json_file(stateDict,filename):
    with open(f"JSON/{filename}.json", "w") as file:
        json.dump(stateDict, file)


@app.post("/api/file-storage/upload/{linkName}")
def upload(linkName,files: List[UploadFile] = File(...)):
    for file in files:
        try:
            contents = file.file.read()
            subdirectory = f"userFiles/{linkName}"
            os.makedirs(subdirectory, exist_ok=True)
            with open(f"userFiles/{linkName}/{file.filename}", 'wb') as f:
                f.write(contents)
        except Exception:
            return {"message": "There was an error uploading the file(s)"}
        finally:
            file.file.close()

    return {"message": f"Successfuly uploaded {[file.filename for file in files]}"}    


@app.get("/api/file-storage/delete/{linkName}")
async def sendFile(linkName):

    path = f'userFiles/{linkName}'

    def delete_directory(path):

        for item in os.listdir(path):
            item_path = os.path.join(path, item)

            if os.path.isdir(item_path):
                delete_directory(item_path)

            else:
                os.remove(item_path)

        os.rmdir(path)

    delete_directory(path)

    return {"message": f"Successfuly deleted file link [ {linkName} ] from server."}

@app.post('/api/flipswitch/{flipswitch}/{state}')
async def switch(flipSwitch,state):

    try:
        state = int(state)
    except Exception:
        return {"Status": 406, "Issue": "/api/flipswitch/[flipswitch]/[state]: state must be a number 0-4"}


    if os.path.exists("JSON/flipSwitches.json"):
        with open("JSON/flipSwitches.json", "r") as file:
            stateDict = json.load(file)
    else:
        stateDict = {}

    title = flipSwitch
    if title not in stateDict:
        stateDict[title] = {'state': False}

    flipSwitch_state = stateDict[title]['state']

    match state:
        case 0:
            flipSwitch_state = False
        case 1:
            flipSwitch_state = True
        case 2:
            flipSwitch_state = not flipSwitch_state
        case 3:
            return stateDict[title]
        case 4:
            stateDict.pop(title, None)
            update_json_file(stateDict,"flipSwitches")
            return stateDict[title]
        case _:
            return {"Status": 406, "Issue": "/api/flipswitch/[flipswitch]/[state]: state must be a number 0-4"}

    stateDict[title]['state'] = flipSwitch_state
    update_json_file(stateDict,"flipSwitches")

    for key in stateDict:
        print(str(key) + ": " + str(stateDict[key]))
    return {"State": flipSwitch_state}


