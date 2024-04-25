import React from 'react'
import ReactDOM from 'react-dom/client'
import {BrowserRouter, Route, Routes,} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import Home from "./pages/Home.tsx";
import {GoogleOAuthProvider} from "@react-oauth/google";
import ShortenURL from "./pages/ShortenURL.tsx";
import UploadFile from "./pages/UploadFile.tsx";
import NotFound from "./pages/NotFound.tsx";

const clientId = import.meta.env.VITE_GOOGLE_CLIENT_ID

ReactDOM.createRoot(document.getElementById('root')!).render(
    <GoogleOAuthProvider clientId={clientId}>
        <React.StrictMode>
            <BrowserRouter>
                <Routes>
                    <Route path='/' element={<Home/>}/>
                    <Route path='/urls' element={<ShortenURL/>}/>
                    <Route path='/files' element={<UploadFile/>}/>
                    <Route path='*' element={<NotFound/>}/>
                </Routes>
            </BrowserRouter>
        </React.StrictMode>
    </GoogleOAuthProvider>
)
