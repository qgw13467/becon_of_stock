FROM nginx:stable-alpine

ARG USER_ID
ARG GROUP_ID

RUN mkdir /app/build \
  && addgroup -g ${GROUP_ID} mygroup \
  && adduser -D -u ${USER_ID} -G mygroup myuser

WORKDIR /app

COPY . /app

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]