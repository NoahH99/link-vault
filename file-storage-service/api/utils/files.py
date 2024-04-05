import os
import uuid
from typing import List

from fastapi import UploadFile

from api.utils.formatter import human_readable_size


def generate_unique_filename(file_extension: str) -> str:
    file_uuid = str(uuid.uuid4())

    return f"{file_uuid}{file_extension}"


def save_uploaded_file(file: UploadFile, directory_path: str, filename: str) -> str:
    file_path = os.path.join(directory_path, filename)

    with open(file_path, "wb") as f:
        f.write(file.file.read())

    return file_path


def get_file_url(file_path: str) -> str:
    return f"http://localhost/files/{file_path.split('/file-storage/')[1]}"


def process_uploaded_file(file, directory_path):
    file_extension = os.path.splitext(file.filename)[1]
    unique_filename = generate_unique_filename(file_extension)
    saved_file_path = save_uploaded_file(file, directory_path, unique_filename)
    file_url = get_file_url(saved_file_path)
    file_size = os.path.getsize(saved_file_path)
    readable_file_size = human_readable_size(file_size)

    return {
        "original_filename": file.filename,
        "filename": unique_filename,
        "file_url": file_url,
        "raw_file_size": file_size,
        "file_size": readable_file_size
    }


def process_uploaded_files(files: List[UploadFile], directory_path: str) -> List[dict]:
    uploaded_files = []

    for file in files:
        uploaded_files.append(process_uploaded_file(file, directory_path))

    return uploaded_files


def create_user_directory(hashed_user_id: str) -> str:
    directory_path = f"/file-storage/{hashed_user_id}/"
    os.makedirs(directory_path, exist_ok=True)
    return directory_path
