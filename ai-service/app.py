from flask import Flask, jsonify
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address

app = Flask(__name__)

# Rate limiter — 30 requests per minute default
limiter = Limiter(
    get_remote_address,
    app=app,
    default_limits=["30 per minute"],
    storage_uri="memory://"
)

@app.errorhandler(429)
def rate_limit_handler(e):
    return jsonify({
        "error": "Rate limit exceeded",
        "message": "Too many requests. Please try again later.",
        "retry_after": str(e.description)
    }), 429

@app.route("/health", methods=["GET"])
def health():
    return jsonify({
        "status": "ok",
        "service": "Tool-14 AI Service",
        "version": "1.0.0"
    })

@app.route("/generate-report", methods=["POST"])
@limiter.limit("10 per minute")
def generate_report():
    return jsonify({
        "message": "Report generation endpoint",
        "status": "ok"
    }), 200

from routes.sanitise import sanitise_bp
app.register_blueprint(sanitise_bp)

if __name__ == "__main__":
    print("Starting Flask...")
    app.run(host="0.0.0.0", port=5000, debug=True)