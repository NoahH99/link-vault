from datetime import datetime, timedelta, timezone
from typing import Union
import ntfy
from fastapi import Depends, FastAPI, HTTPException, status, Request
from fastapi.responses import HTMLResponse
from fastapi.security import OAuth2PasswordBearer, OAuth2PasswordRequestForm
from jose import JWTError, jwt
from passlib.context import CryptContext
from pydantic import BaseModel
from typing_extensions import Annotated
import asyncio
import json
import csv
from os import path
import json
import base64
import ssl
from turfpy import measurement as turfpyMeasure, transformation as turfpyTransform


pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

app = FastAPI()


@app.get("/api/file-storage",response_class=HTMLResponse)
async def get_zones():
    with open('HTML/redirect.html', 'r') as file:  # r to open file in READ mode
        html_as_string = file.read()

    return html_as_string

@app.get("/api/file-storage/test")
async def get_zones():
    return [{"Status": "Success", "Data": "Hello"}]
