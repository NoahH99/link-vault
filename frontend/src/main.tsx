import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import App from "./App.tsx";
import Shorten from "./Shorten.tsx";
import Uploader from "./Uploader.tsx";
import {createBrowserRouter, RouterProvider,} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>
    },
    {
        path: "/shorten",
        element: <Shorten/>
    },
    {
        path: "/uploader",
        element: <Uploader/>
    }
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>,
)
