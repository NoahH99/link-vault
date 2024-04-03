from fastapi import FastAPI
from fastapi.responses import JSONResponse

app = FastAPI()

@app.get("/api/authentication/health")
def health_check():
    return {"status": 200}
