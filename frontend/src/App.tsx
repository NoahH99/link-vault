import {useState} from "react";
import {Link} from "react-router-dom";

function App() {

    const [apiData, setApiData] = useState<string | null>(null);

    const handleLoginClick = async () => {
        try {
            setApiData('logged in')
        } catch (e) {
            console.log(e);
        }
    }

    return (
        <>
            {apiData == null && (
                <button
                    onClick={handleLoginClick}
                    type="button"
                    className="btn btn-primary btn-lg">
                    Login with Google
                </button>
            )}
            {apiData != null && (
                <>
                    <h1>Logged In</h1>
                    <Link to="/shorten">
                        <button
                            type="button"
                            className="btn btn-primary btn-lg">
                            Shorten URL
                        </button>
                    </Link>
                    <Link to="/uploader">
                        <button
                            type="button"
                            className="btn btn-primary btn-lg">
                            Upload Files
                        </button>
                    </Link>
                </>
            )}
        </>
    )
}

export default App
