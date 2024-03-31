import './App.css'
import React, {ChangeEvent, useState} from 'react';

function App() {
    const [url, setUrl] = useState('');

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        try {
            const response = await fetch('https://backend/api/url/shorten', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({url: url}),
            });

            if (response.ok) {
                console.log('URL submitted successfully!');
            } else {
                console.error('Failed to submit URL');
            }
        } catch (error) {
            console.error('Error submitting URL:', error);
        }
    };

    const handleChange = (event: ChangeEvent<HTMLFormElement>) => {
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
                                onChange={() => handleChange}
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
