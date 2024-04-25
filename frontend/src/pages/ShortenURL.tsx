import {useState, useEffect} from 'react';
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
    const [canShorten, setCanShorten] = useState<boolean>(
      JSON.parse(localStorage.getItem("canShorten") || "true")
    );


    useEffect(() => {
        localStorage.setItem("canShorten", canShorten.toString());
    }, [canShorten]);

    const handleFlipSwitch = async (flipSwitch: string) => {
      const state = canShorten ? 0 : 1;
      console.log("flipSwitch:", flipSwitch, "state:", state);
      try {
        const response = await fetch(`/api/flipswitch/${flipSwitch}/${state}`, {
          method: "GET",
        });

        if (response.ok) {
          const data = await response.json();
          setCanShorten(data.State);
        } else {
          console.error("Failed to toggle shorten capability");
        }
      } catch (error) {
        console.error("Error toggling shorten capability:", error);
      }
    };

    const handleShorten = async () => {

        if (!canShorten) {
            alert("Shortening is currently disabled.");
            return;
        }
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
        disabled={!canShorten}/>
    </div>
    <div className="form-group mt-3">
      <label htmlFor="customShortURL">Custom Shortened URL:</label>
      <input type="text"className="form-control"id="customShortURL"value={customShortURL}onChange={(e) => setCustomShortURL(e.target.value)}
      disabled={!canShorten} />
    </div>
    <div className="form-group mt-3">
      <label htmlFor="expirationDate">Expiration Date (Optional):</label>
      <input type="date"className="form-control"id="expirationDate"value={expirationDate}onChange={(e) => setExpirationDate(e.target.value)}
        disabled={!canShorten} />
    </div>
    <div className="card-footer mt-3">
      <button className="btn btn-primary"onClick={handleShorten}disabled={!canShorten}>
        Shorten URL
      </button>
      <Link to="/" className="btn btn-secondary ms-3">
        Go Home
      </Link>
    </div>
    {error && <div className="alert alert-danger mt-3">{error}</div>}
    {shortenedURL && (
      <div className="alert alert-primary mt-3">
        <p>Shortened URL:</p>
        <a href={"s/" + shortenedURL} target="_blank" rel="noopener noreferrer">
          {domain}/s/{shortenedURL}
        </a>
      </div>
    )}
    <button className="btn btn-info mt-3"onClick={() => handleFlipSwitch("canShorten")}>
      Toggle URL Shortening
    </button>{!canShorten && (<div className="alert alert-warning mt-3">URL shortening is currently disabled.
      </div>
    )}
  </div>
);

}


export default ShortenURL;