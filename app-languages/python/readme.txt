cd /app
pip3 install -r requirements.txt

gunicorn fast_asyncpg:app --workers 2 --worker-class uvicorn.workers.UvicornWorker --bind 0.0.0.0:7222
