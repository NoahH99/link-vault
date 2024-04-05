import hashlib
import os
import uuid
from typing import List

import requests
from fastapi import FastAPI, HTTPException, Depends, UploadFile
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from starlette.staticfiles import StaticFiles

app = FastAPI()
security = HTTPBearer()

os.makedirs("/file-storage", exist_ok=True)
app.mount("/files", StaticFiles(directory="/file-storage"), name="files")


def validate_token(credentials: HTTPAuthorizationCredentials = Depends(security)):
    if not credentials:
        raise HTTPException(status_code=401, detail='No credentials supplied.')

    token = credentials.credentials
    validation_url = f"https://www.googleapis.com/oauth2/v3/tokeninfo?id_token={token}"
    response = requests.get(validation_url)

    if response.status_code != 200:
        raise HTTPException(status_code=401, detail='Invalid token')

    return response.json()


def human_readable_size(size_in_bytes: int) -> str:
    for unit in ['B', 'KB', 'MB', 'GB']:
        if size_in_bytes < 1024.0:
            return f"{size_in_bytes:.2f} {unit}"
        size_in_bytes /= 1024.0

    return f"{size_in_bytes:.2f} TB"


@app.post("/api/file-storage/upload")
def upload_files(files: List[UploadFile], decoded_token: dict = Depends(validate_token)):
    user_id = decoded_token.get("sub")
    hashed_user_id = hashlib.sha256(user_id.encode()).hexdigest()
    directory_path = f"/file-storage/{hashed_user_id}/"

    os.makedirs(directory_path, exist_ok=True)

    uploaded_files_info = []
    for file in files:
        file_extension = os.path.splitext(file.filename)[1]
        file_uuid = str(uuid.uuid4())
        file_name = f"{file_uuid}{file_extension}"
        file_path = os.path.join(directory_path, file_name)

        with open(file_path, "wb") as f:
            f.write(file.file.read())

        file_url = "http://localhost/files/" + file_path.split("/file-storage/")[1]
        file_size = os.path.getsize(file_path)
        readable_file_size = human_readable_size(file_size)

        uploaded_files_info.append({
            "original_filename": file.filename,
            "filename": file_name,
            "file_url": file_url,
            "raw_file_size": file_size,
            "file_size": readable_file_size
        })

    return {"uploaded_files": uploaded_files_info}


@app.get("/api/file-storage/health")
def health_check():
    return {"status": 200}
