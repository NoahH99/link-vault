from fastapi import APIRouter
import asyncio
import json
import os

router = APIRouter()


current_script_directory = os.path.dirname(os.path.abspath(__file__))
json_folder_path = os.path.join(current_script_directory, 'JSON')
json_file_path = os.path.join(json_folder_path, 'flipSwitches.json')

def update_json_file(stateDict,filename):
    with open(f"{json_file_path}", "w") as file:
        json.dump(stateDict, file)


@router.get("/{flipSwitch}/{state}")
async def switch(flipSwitch,state):

    try:
        state = int(state)
    except Exception:
        return {"Status": 406, "Issue": "/api/flipswitch/[flipswitch]/[state]: state must be a number 0-4"}


    if os.path.exists(f"{json_file_path}"):
        with open(f"{json_file_path}", "r") as file:
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


    return {"Status": 200 ,"State": flipSwitch_state}