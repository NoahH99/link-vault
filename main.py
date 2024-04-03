from typing import List
from fastapi import FastAPI, File, UploadFile
import os

app = FastAPI()


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
