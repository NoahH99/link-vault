# Link Vault

LinkVault is a versatile web application designed to provide users with efficient URL shortening and secure file storage services. It offers a seamless experience for managing and sharing links and files, ensuring convenience and reliability for both personal and professional use.



## Sprint Details

- [Sprint 1](/sprint-info/SPRINT1.md)
- [Sprint 2](/sprint-info/SPRINT2.md)
- [Sprint 3](/sprint-info/SPRINT3.md)



## Features

1. File Storage Service:
    - Upload files securely and retrieve download links.
    - Authenticate users via Google OAuth2 tokens.
    - Support for multiple file uploads.

2. URL Shortener Service:
    - Shorten long URLs and generate custom short links.
    - Redirect users to the original URLs using short links.
    - Track analytics for shortened URLs.



## Building and Running the Project Locally

To build and run the Link Vault application locally, follow these steps:

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/NoahH99/link-vault.git
   ```

2. Navigate to the project directory:

   ```bash
   cd link-vault
   ```

3. Edit the Docker Compose configuration file:

   ```bash
   cp docker-compose.example.yaml docker-compose.yaml
   ```

   Edit `docker-compose.yaml` with the necessary information for your setup.

4. Set Google Client ID:
    - Obtain a Google Client ID from the Google Cloud Console.
    - Open the `docker-compose.yaml` file and set the `GOOGLE_CLIENT_ID` variable to the obtained Google Client ID.

5. Edit the url-shortener-service application configuration file:

   ```bash
   cp url-shortener-service/src/main/resources/application.properties.example url-shortener-service/src/main/resources/application.properties
   ```

   Edit `url-shortener-service/src/main/resources/application.properties` with the appropriate configurations for your environment.

6. Edit the url-shortener-service test configuration file:

   ```bash
   cp url-shortener-service/src/test/resources/application.properties.example url-shortener-service/src/test/resources/application.properties
   ```

   Edit `url-shortener-service/src/test/resources/application.properties` with the necessary configurations for your testing environment.

7. Run Docker Compose to build and start the containers:

   ```bash
   docker-compose up --build
   ```

8. Once the containers are up and running, you can access the application at `http://localhost`.



## Contributing

Contributions to the Link Vault application are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.



## API Endpoints

The Link Vault application exposes the following API endpoints:

### File Storage Service Endpoints:

#### Upload Files: `/api/file-storage/upload`

- **Method:** `POST`
- **Description:** Upload one or multiple files securely.
- **Authorization:** Bearer token obtained from Google OAuth2.
- **Request Body:** Form-data with `files` to be uploaded.
- **Response Body:**
   ```json
   {
      "uploaded_files": [
         {
            "original_filename": "Example1.jpg",
            "filename": "7b7624f1-7b94-4824-a039-3e49000a8918.jpg",
            "file_url": "http://localhost/files/c504fe011c2e0f625441bc0b9f1469d67193e5626ab1fc7de7460feac1e8181e/7b7624f1-7b94-4824-a039-3e49000a8918.jpg",
            "raw_file_size": 786097,
            "file_size": "767.67 KB"
         },
         {
            "original_filename": "Example2.jpg",
            "filename": "48a6f6ce-0ae7-414e-b8a6-1e73fd8a3255.jpg",
            "file_url": "http://localhost/files/c504fe011c2e0f625441bc0b9f1469d67193e5626ab1fc7de7460feac1e8181e/48a6f6ce-0ae7-414e-b8a6-1e73fd8a3255.jpg",
            "raw_file_size": 80701,
            "file_size": "78.81 KB"
         },
         {
            "original_filename": "Example3.jpg",
            "filename": "a4148ff8-6f78-43d9-893b-2c1d4d1ffb1d.jpg",
            "file_url": "http://localhost/files/c504fe011c2e0f625441bc0b9f1469d67193e5626ab1fc7de7460feac1e8181e/a4148ff8-6f78-43d9-893b-2c1d4d1ffb1d.jpg",
            "raw_file_size": 60573,
            "file_size": "59.15 KB"
         }
      ]
   }
   ```


#### Health Check: `/api/file-storage/health`

- **Method:** `GET`
- **Description:** Check the health status of the file storage service.
- **Response Body:**
   ```json
   {
      "status": 200
   }
   ```


### URL Shortener Service Endpoints:

#### Shorten URL: `/api/url-shortener/shorten`

- **Method:** `POST`
- **Description:** Shortens a long URL and returns the shortened link.
- **Request Body:**
   ```json
   {
      "originalUrl": "https://example-long-url.com",
      "shortCode": "example",
      "expirationDate": "2024-04-10T12:00:00Z"
   }
   ```
- **Response Body:**
   ```json
   {
      "originalUrl": "https://example-long-url.com",
      "shortCode": "example",
      "expirationDate": "2024-04-10T12:00:00Z"
   }
   ```


#### Redirect Short URL: /api/url-shortener/s/:shortCode

- **Method:** `GET`
- **Description:** Redirects to the original URL associated with the provided short link.
- **Path Parameter:** `shortCode` - Short code generated for the original URL.


#### Global Analytics: `/api/url-shortener/analytics`

- **Method:** `GET`
- **Description:** Get global analytics for all shortened URLs.


#### URL Analytics: `/api/url-shortener/analytics/:shortCode`

- **Method:** `GET`
- **Description:** Get analytics for a specific shortened URL.
- **Path Parameter:** `shortCode` - Short code generated for the original URL.


#### Health Check: `/api/url-shortener/health`

- **Method:** `GET`
- **Description:** Check the health status of the url shortener service.
- **Response Body:**
   ```json
   {
      "status": 200
   }
   ```

#### Flip Switches: `/api/flipswitch/{switchName}/{state}`

- **Method:** `GET`
- **Description:** Creates or modifies the state of {switchName} via modifier {state}.
  {switchName} can be any string (excluding spaces and forward slashes.
  {state} must be an number 0-4, which perform the following actions:
    - ( 0 ) Set the {switchName} to False.
    - ( 1 ) Set the {switchName} to True.
    - ( 2 ) Toggle the {switchName} state.
    - ( 3 ) Queries the {switchName} state, changing nothing.
    - ( 4 ) Deletes the {switchName} switch
- **Response Body:**
   Success:
   ```json
   {
      "Status": 200 ,
      "State": flipSwitch_state
   }
   ```
   Failure:
   ```json
   {
      "Status": 406,
      "Issue": "/api/flipswitch/[flipswitch]/[state]: state must be a number 0-4"
   }
   ```



## License

This project is licensed under the [MIT License](/LICENSE).

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
