from fastapi import FastAPI
from fastapi.responses import JSONResponse

app = FastAPI()

@app.get("/api/file-storage/health")
def health_check():
    return JSONResponse(content={"status": 200})
