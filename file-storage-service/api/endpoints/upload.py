from typing import List

from fastapi import APIRouter, UploadFile, Depends

from api.security.google_auth import validate_token
from api.utils.files import create_user_directory, process_uploaded_files
from api.utils.user import get_user_id, hash_user_id

router = APIRouter()


@router.post("/upload")
def upload_files(files: List[UploadFile], decoded_token: dict = Depends(validate_token)):
    user_id = get_user_id(decoded_token)
    hashed_user_id = hash_user_id(user_id)
    directory_path = create_user_directory(hashed_user_id)
    uploaded_files = process_uploaded_files(files, directory_path)

    return {"uploaded_files": uploaded_files}
