FROM nginx:stable-alpine

WORKDIR /app

RUN mkdir ./build

RUN useradd -ms /bin/bash www-data

ADD ./build ./build

RUN rm /etc/nginx/conf.d/default.conf

COPY ./nginx.conf /etc/nginx/conf.d

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]