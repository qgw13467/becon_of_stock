FROM nginx:stable-alpine

ARG USER_ID
ARG GROUP_ID

WORKDIR /app
RUN mkdir -p /app/build && \
    addgroup -g ${GROUP_ID} mygroup && \
    adduser -D -u ${USER_ID} -G mygroup myuser

COPY nginx.conf /etc/nginx/nginx.conf
COPY . /app

RUN chown -R myuser:mygroup /app

USER myuser

CMD ["nginx", "-g", "daemon off;"]