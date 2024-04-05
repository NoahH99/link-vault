import {useState} from 'react';
import {Link} from "react-router-dom";

interface SuccessfulResponse {
    originalUrl: string,
    shortCode: string,
    expirationDate: string,
}

interface ErroredResponse {
    message: string,
    statusCode: string,
    timestamp: string,
}

interface ShortenRequestBody {
    originalUrl: string;
    shortCode?: string;
    expirationDate?: string;
}

function ShortenURL() {
    const [originalURL, setOriginalURL] = useState('');
    const [shortenedURL, setShortenedURL] = useState('');
    const [customShortURL, setCustomShortURL] = useState('');
    const [expirationDate, setExpirationDate] = useState('');
    const [error, setError] = useState('');
    const domain = window.location.origin;

    const handleShorten = async () => {
        try {
            const requestBody: ShortenRequestBody = {originalUrl: originalURL};

            if (customShortURL !== '') requestBody.shortCode = customShortURL;
            if (expirationDate !== '') {
                const date = new Date(expirationDate);
                requestBody.expirationDate = date.toISOString();
            }

            const response = await fetch('/api/url-shortener/shorten', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestBody),
            });

            if (response == null) {
                setError('Failed to shorten URL. Please try again later.')
                return;
            }

            if (!response.ok) {
                const data: ErroredResponse = await response.json()
                setError(data.message)
                return
            }

            const data: SuccessfulResponse = await response.json();

            setOriginalURL('')
            setShortenedURL(`${data.shortCode}`);
            setCustomShortURL('')
            setExpirationDate('')
            setError('');
        } catch (error) {
            console.error('Error shortening URL: ', error);
            setError('Failed to shorten URL. Please try again later.');
        }
    };

    return (
        <div className="container">
            <h1>URL Shortener</h1>
            <div className="form-group">
                <label htmlFor="originalURL">Enter URL:</label>
                <input
                    type="text"
                    className="form-control"
                    id="originalURL"
                    value={originalURL}
                    onChange={(e) => setOriginalURL(e.target.value)}
                />
            </div>
            <div className="form-group mt-3">
                <label htmlFor="customShortURL">Custom Shortened URL:</label>
                <input
                    type="text"
                    className="form-control"
                    id="customShortURL"
                    value={customShortURL}
                    onChange={(e) => setCustomShortURL(e.target.value)}
                />
            </div>
            <div className="form-group mt-3">
                <label htmlFor="expirationDate">Expiration Date (Optional):</label>
                <input
                    type="date"
                    className="form-control"
                    id="expirationDate"
                    value={expirationDate}
                    onChange={(e) => setExpirationDate(e.target.value)}
                />
            </div>
            <div className="card-footer mt-3">
                <button className="btn btn-primary" onClick={handleShorten}>
                    Shorten URL
                </button>
                <Link to="/" className="btn btn-secondary ms-3">Go Home</Link>
            </div>
            {error && <div className="alert alert-danger mt-3">{error}</div>}
            {shortenedURL && (
                <div className="alert alert-primary mt-3">
                    <p>Shortened URL:</p>
                    <a href={"s/" + shortenedURL} target="_blank" rel="noopener noreferrer">{domain}/s/{shortenedURL}</a>
                </div>
            )}
        </div>
    );
}

export default ShortenURL;