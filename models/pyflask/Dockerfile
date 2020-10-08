FROM python
COPY . /app
WORKDIR /app
RUN apt-get update -y
RUN apt-get install -y libgl1-mesa-dev
RUN pip install -r requirements.txt
EXPOSE 5000
CMD ["python","app.py"]
