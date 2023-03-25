FROM nginx:stable-alpine

ARG USER_ID
ARG GROUP_ID

WORKDIR /app

RUN mkdir ./build \
    && addgroup -g ${GROUP_ID} www-data \
    && adduser -D -u ${USER_ID} -G www-data www-data

ADD ./build ./build

RUN rm /etc/nginx/conf.d/default.conf

COPY ./nginx.conf /etc/nginx/conf.d

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]