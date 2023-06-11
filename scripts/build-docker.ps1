$sha = (git rev-parse HEAD)
docker build -t security-jwt-app:$sha .