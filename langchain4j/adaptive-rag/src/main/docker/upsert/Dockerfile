FROM python:3.11-slim

COPY requirements.txt /tmp

RUN apt-get update && apt-get install -y --no-install-recommends curl ca-certificates

RUN pip install --no-cache-dir --user -r /tmp/requirements.txt

WORKDIR /workspace

COPY index.py /workspace

CMD ["python", "index.py"]
