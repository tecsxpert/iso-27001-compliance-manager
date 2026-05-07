import requests
import time

BASE_URL = "http://127.0.0.1:5000"

print("=" * 50)
print("Test 1 — Normal request to /health (should return 200)")
response = requests.get(f"{BASE_URL}/health")
print(f"Status: {response.status_code}")
print(f"Response: {response.json()}")

print("=" * 50)
print("Test 2 — Hit /generate-report 11 times (11th should return 429)")
for i in range(1, 12):
    response = requests.post(f"{BASE_URL}/generate-report")
    print(f"Request {i}: Status {response.status_code}")
    if response.status_code == 429:
        print(f"Rate limit hit! Response: {response.json()}")
        break

print("=" * 50)
print("Test 3 — Check retry_after field in 429 response")
for i in range(11):
    response = requests.post(f"{BASE_URL}/generate-report")
if response.status_code == 429:
    print(f"Status: {response.status_code}")
    print(f"Response: {response.json()}")
else:
    print("Wait 1 minute and run again to test 429")