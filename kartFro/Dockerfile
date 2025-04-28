FROM nginx:alpine

COPY /dist /usr/share/nginx/html
COPY /nginx.conf /etc/nginx/nginx.conf.d

EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]