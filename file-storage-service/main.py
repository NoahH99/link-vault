import os

from fastapi import FastAPI
from starlette.staticfiles import StaticFiles

from api.routes import main

app = FastAPI()

app.include_router(main.router)

os.makedirs("/file-storage", exist_ok=True)
app.mount("/files", StaticFiles(directory="/file-storage"), name="files")

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8080)
