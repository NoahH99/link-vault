from fastapi import APIRouter

from api.endpoints import health, upload

router = APIRouter()

prefix = "/api/file-storage"

router.include_router(health.router, prefix=prefix)
router.include_router(upload.router, prefix=prefix)
