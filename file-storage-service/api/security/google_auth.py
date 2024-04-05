import requests
from fastapi import Depends, HTTPException
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer


def validate_token(credentials: HTTPAuthorizationCredentials = Depends(HTTPBearer())):
    if not credentials:
        raise HTTPException(status_code=401, detail='No credentials supplied.')

    token = credentials.credentials
    validation_url = f"https://www.googleapis.com/oauth2/v3/tokeninfo?id_token={token}"
    response = requests.get(validation_url)

    if response.status_code != 200:
        raise HTTPException(status_code=401, detail='Invalid token')

    return response.json()
