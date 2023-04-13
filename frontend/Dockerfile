FROM node:19-alpine
WORKDIR /app
COPY package*.json .
RUN npm install
ADD src ./src
ADD public ./public
COPY .env ./
EXPOSE 80
CMD npm start