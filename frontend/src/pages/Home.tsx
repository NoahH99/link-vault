import {GoogleLogin} from "@react-oauth/google";
import {jwtDecode} from "jwt-decode";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";

interface GoogleJWTToken {
    name: string,
    picture: string,
    exp: number
}

function Home() {

    const [name, setName] = useState<string | null>(null);
    const [profilePictureUrl, setProfilePictureUrl] = useState<string | null>(null);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');

        if (token == null) return;

        const payload = jwtDecode<GoogleJWTToken>(token);
        const currentTime = Date.now() / 1000;

        if (payload.exp <= currentTime) {
            logout();
            return;
        }

        setName(payload.name);
        setProfilePictureUrl(payload.picture);
    }, []);

    const logout = () => {
        localStorage.removeItem('jwtToken');
        window.location.href = '/';
    }

    return (
        <>
            {name == null && (
                <div className="d-flex justify-content-center align-items-center vh-100">
                    <div className="text-center">
                        <GoogleLogin
                            onSuccess={credentialResponse => {
                                const token = credentialResponse.credential;

                                if (token == undefined) return;

                                localStorage.setItem('jwtToken', token);

                                const payload = jwtDecode<GoogleJWTToken>(token);

                                setName(payload.name)
                                setProfilePictureUrl(payload.picture)
                            }}
                            onError={() => {
                                console.log('Login Failed');
                            }}
                        />
                    </div>
                </div>
            )}
            {name != null && profilePictureUrl != null && (
                <div className="container mt-5">
                    <div className="row">
                        <div className="col-md-6 offset-md-3">
                            <div className="card w-100">
                                <div className="card-header">
                                    <h3 className="text-center">Logged In</h3>
                                </div>
                                <div className="card-body">
                                    <div className="text-center mb-3">
                                        <img src={profilePictureUrl} className="rounded-circle"
                                             alt="Profile Picture"/>
                                    </div>
                                    <h5 className="card-title text-center">{name}</h5>
                                    <p className="card-text text-center">Welcome to your profile!</p>
                                </div>
                                <div className="card-footer text-center">
                                    <Link to="/urls" className="btn btn-primary">URL Shortener</Link>
                                    <Link to="/files" className="btn btn-primary mx-5">File Uploader</Link>
                                    <button className="btn btn-danger" onClick={logout}>Logout</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    )
}

export default Home