from fastapi import FastAPI
from fastapi.security import OAuth2AuthorizationCodeBearer
from starlette.requests import Request
from authlib.integrations.starlette_client import OAuth
from starlette.config import Config


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
async def login(request: Request):
    redirect_uri = request.url_for('auth', _external=True)
    return await oauth.google.authorize_redirect(request, config('redirectURL'))

@app.route("/auth")
async def auth(request: Request):
    token = await oauth.google.authorize_access_token(request)
    user = await oauth.google.parse_id_token(request, token)
    return user