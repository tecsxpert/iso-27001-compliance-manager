import re
import bleach
from flask import request, jsonify
from functools import wraps

# List of prompt injection patterns to detect
INJECTION_PATTERNS = [
    r"ignore.*(all|previous|above).*(instructions|rules|prompts)",
    r"ignore all previous instructions",
    r"repeat your.*(system|prompt)",
    r"you are now",
    r"disregard.*(all|previous|).*instructions",
    r"forget.*(all|previous|).*instructions",
    r"act as",
    r"jailbreak",
    r"do anything now",
    r"pretend you are",
    r"bypass.*(all|your|).*restrictions",
    r"reveal the system prompt",
    r"reveal.*prompt",
    r"show.*system prompt",
]

def sanitise_input(text):
    # Strip all HTML tags
    clean_text = bleach.clean(text, tags=[], strip=True)
    # Remove extra whitespace
    clean_text = clean_text.strip()
    return clean_text

def detect_prompt_injection(text):
    text_lower = text.lower()
    
    # Direct keyword check
    BANNED_PHRASES = [
        "ignore all previous instructions",
        "ignore previous instructions",
        "reveal the system prompt",
        "repeat your system prompt",
        "forget all instructions",
        "disregard all instructions",
        "you are now",
        "act as",
        "jailbreak",
        "do anything now",
        "pretend you are",
        "bypass restrictions",
    ]
    
    for phrase in BANNED_PHRASES:
        if phrase in text_lower:
            return True
            
    # Regex check
    for pattern in INJECTION_PATTERNS:
        if re.search(pattern, text_lower):
            return True
            
    return False

def sanitise_middleware(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        data = request.get_json()

        if not data:
            return jsonify({
                "error": "Invalid request",
                "message": "Request body must be JSON"
            }), 400

        # Sanitise and check all string fields
        for key, value in data.items():
            if isinstance(value, str):
                # Check length
                if len(value) > 5000:
                    return jsonify({
                        "error": "Input too long",
                        "message": f"Field '{key}' exceeds 5000 character limit"
                    }), 400

                # Strip HTML
                data[key] = sanitise_input(value)

                # Detect prompt injection
                if detect_prompt_injection(data[key]):
                    return jsonify({
                        "error": "Invalid input detected",
                        "message": "Your input contains prohibited patterns"
                    }), 400

        request.sanitised_data = data
        return f(*args, **kwargs)

    return decorated_function

from flask import Blueprint

sanitise_bp = Blueprint("sanitise", __name__)

@sanitise_bp.route("/sanitise-test", methods=["POST"])
@sanitise_middleware
def sanitise_test():
    return jsonify({
        "message": "Input is clean and safe",
        "sanitised_data": request.sanitised_data
    }), 200