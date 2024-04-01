import './App.css'
import {ChangeEvent, FormEvent, useState} from "react";
import {
    Link,
  } from "react-router-dom";

interface ApiResponse {
    originalUrl: string,
    shortCode: string,
    expirationDate: Date
}

function App() {
    const [url, setUrl] = useState('');
    const [notification, setNotification] = useState<string | null>(null);

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            const response = await fetch('/api/url/shorten', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({originalUrl: url}),
            });

            const data: ApiResponse = await response.json();

            await navigator.clipboard.writeText("localhost/" + data.shortCode);

            setUrl('')
            setNotification('Copied to clipboard!');
        } catch (error) {
            console.error('Error:', error);
            setNotification('Failed to shorten URL. Please try again.');
        }
    };

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        setUrl(event.target.value);
    };

    return (
        <>
            <div className="app">
                <div className="header">
                    <p>localhost/shorten</p>
                </div>
                <div className="hero">
                    <div className="hero-text">SHORTEN</div>
                    <form className="url-form" onSubmit={handleSubmit}>
                        <div className="form-inner">
                            <input
                                type="url"
                                placeholder="enter your url here"
                                value={url}
                                onChange={handleChange}
                            />
                            <button type="submit">lets's go</button>
                        </div>
                    </form>
                    {notification && (
                        <div className="notification">
                            <p>{notification}</p>
                        </div>
                    )}
                </div>
                <div className="footer">
                    <Link to="/info">check out our api</Link> 
                </div>
            </div>
        </>
    )
}

export default App
