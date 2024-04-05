import {Link} from "react-router-dom";

function NotFound() {
    return (
        <div className="container text-center mt-5">
            <h1 className="display-4">404 - Page Not Found</h1>
            <p className="lead">The page you're looking for does not exist.</p>
            <Link to="/" className="btn btn-primary">Go to Home</Link>
        </div>
    );
}

export default NotFound
