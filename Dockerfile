FROM node:21.7.1-alpine AS build

WORKDIR /frontend

COPY frontend/package*.json ./

RUN npm ci

COPY frontend .

RUN npm run build

FROM nginx:1.24.0-alpine

WORKDIR usr/share/nginx/html

COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /frontend/dist .

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
