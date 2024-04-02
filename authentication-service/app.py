from flask import Flask

app = Flask(__name__)

@app.route('/api/authentication/health')
def health_check():
    return { "status": 200 }

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
