# URL Shortener

This is a simple tool for shortening long URLs into shorter, more manageable links. It allows users to generate short URLs that redirect to the original long URLs, making it easier to share links in emails, social media, or other platforms.

## Features

- Shorten long URLs into compact and manageable links
- Customizable short links
- Statistics tracking for shortened URLs


## Building and Running the Project Locally

To build and run the URL shortener application locally, follow these steps:

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/NoahH99/cmsc355-url-shortener.git
   ```

2. Navigate to the project directory:

   ```bash
   cd cmsc355-url-shortener
   ```
   
3. Run Docker Compose to build and start the containers:

   ```bash
   docker-compose up --build
   ```

4. Once the containers are up and running, you can access the application at `http://localhost`.


## Contributing
Contributions to the URL shortener application are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.


## API Endpoints

The URL shortener application exposes the following API endpoints:

### Shorten URL: `/api/url/shorten`
- **Method:** `POST`
- **Description:** Shortens a long URL and returns the shortened link.
- **Request Body:**
   ```json
  {
    "url": "https://example-long-url.com",
    "shortCode": "3zXFC", // Optional
    "expirationDate": "YYYY-MM-DD HH:MM:SS" // Optional
  }
   ```
- **Response Body:**
   ```json
  {
    "url": "https://example-long-url.com",
    "shortCode": "3zXFC",
    "expirationDate": "YYYY-MM-DD HH:MM:SS"
  }
   ```

### Redirect Short URL: `/api/url/:shortCode`
- **Method:** `GET`
- **Description:** Redirects to the original URL associated with the provided short link.

### Get Global Stats: `/api/url/analytics`
- **Method:** `GET`
- **Description:** Retrieves global statistics for the top 10 most clicked URLs.
- **Response Body:**
   ```json
  {
    "totalShortenedURLs": 2,
    "totalClicks": 20,
    "topClickedURLs": [
      {
        "url": "https://example-long-url.com",
        "shortCode": "3zXFC",
        "expirationDate": "YYYY-MM-DD HH:MM:SS",
        "createdDate": "YYYY-MM-DD HH:MM:SS",
        "lastAccessedDate": "YYYY-MM-DD HH:MM:SS",
        "clicks": 15
      },
      {
        "url": "https://another-example-long-url.com",
        "shortCode": "example",
        "expirationDate": "YYYY-MM-DD HH:MM:SS",
        "createdDate": "YYYY-MM-DD HH:MM:SS",
        "lastAccessedDate": "YYYY-MM-DD HH:MM:SS",
        "clicks": 5
      },
      ...
    ]
  }
   ```

### Get URL Stats: `/api/url/analytics/:shortCode`
- **Method:** `GET`
- **Description:** Retrieves statistics for a shortened URL, including the number of clicks.
- **Response Body:**
   ```json
  {
    "url": "https://example-long-url.com",
    "shortCode": "3zXFC",
    "expirationDate": "YYYY-MM-DD HH:MM:SS",
    "createdDate": "YYYY-MM-DD HH:MM:SS",
    "lastAccessedDate": "YYYY-MM-DD HH:MM:SS",
    "clicks": 5
  }
   ```


### Get Health of Service: `/api/url/health`
- **Method:** `GET`
- **Description:** Gets the health of the service.
- **Response Body:**
   ```json
  {
    "status": 200
  }
   ```
  

## Additional Notes
- Ensure that Docker and Docker Compose are installed on your system before running the application.
- Customize the nginx configuration (`nginx.conf`) if necessary for your specific deployment needs.
- Create a `docker-compose.yaml` file using the example provided (`docker-compose.example.yaml`).
- Create an `application.properties` file using the example provided (`application.properties.example`).
- For development purposes, the frontend and backend modules can be run independently outside of Docker containers. Instructions for setting up the development environment may vary based on your local setup.


## License

This project is licensed under the [MIT License](https://github.com/NoahH99/cmsc355-url-shortener/blob/master/LICENSE).

```
MIT License

Copyright (c) 2024 Noah Hendrickson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```