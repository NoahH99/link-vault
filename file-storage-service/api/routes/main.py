from fastapi import APIRouter

from api.endpoints import health, upload, flipswitch

router = APIRouter()


router.include_router(health.router, prefix="/api/file-storage")
router.include_router(upload.router, prefix="/api/file-storage")
router.include_router(flipswitch.router, prefix="/api/flipswitch")
