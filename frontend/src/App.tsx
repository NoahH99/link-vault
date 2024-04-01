import './App.css'
import {ChangeEvent, FormEvent, useState} from "react";

interface ApiResponse {

}

function App() {
    const [url, setUrl] = useState('');

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

            if (!response.ok) {
                throw new Error('Failed to shorten URL');
            }

            const data: ApiResponse = await response.json();
            console.log(data);
        } catch (error) {
            console.error('Error:', error);
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
                <div className="overlay"></div>
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
                </div>
                <div className="footer">
                    <p>check out our api</p>
                </div>
            </div>
        </>
    )
}

export default App
