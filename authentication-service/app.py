from fastapi import FastAPI, HTTPException, Request, Body
from fastapi.security import OAuth2AuthorizationCodeBearer
from authlib.integrations.starlette_client import OAuth
from starlette.config import Config
from fastapi.responses import RedirectResponse, JSONResponse


config = Config('.env')
app = FastAPI()

oauth = OAuth()
oauth.register(
    name='google',
    client_id=config('googleClientID'),
    client_secret=config('googleClientSecret'),
    authorize_url='https://accounts.google.com/o/oauth2/auth',
    authorize_params=None,
    access_token_url='https://accounts.google.com/o/oauth2/token',
    access_token_params=None,
    refresh_token_url=None,
    refresh_token_params=None,
    client_kwargs={'scope': 'openid profile email'},
)

oauth2_scheme = OAuth2AuthorizationCodeBearer(
    authorizationUrl='https://accounts.google.com/o/oauth2/auth',
    tokenUrl='https://accounts.google.com/o/oauth2/token',
    scopes={"openid", "email", "profile"}
)

@app.get("/login")
async def login(request: Request, body: dict = Body(...)):
    redirectURL = body.get("redirectURL")
    if not redirectURL:
        raise HTTPException(status_code=400, detail="Redirect URL not provided")
    oauth2RedirectURL = request.url_for('auth', _external=True) + f"?redirectURL={redirectURL}"
    return await oauth.google.authorize_redirect(request, oauth2RedirectURL)

@app.route("/auth")
async def auth(request: Request):
    redirectURL = request.query_params.get("redirectURL")
    try: 
        token = await oauth.google.authorize_access_token(request)
        return RedirectResponse(url=f"{redirectURL} ?token={token['access_token']}")
    except Exception as e:
        return JSONResponse(
            status_code=400,
            content={"success": False, "message": "Error authenticating user", "error": str(e)}
        )