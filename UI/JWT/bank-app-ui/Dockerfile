FROM nginx
COPY nginx.conf /etc/nginx/nginx.conf
COPY /dist/bank-app-ui /usr/share/nginx/html
VOLUME /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


# FROM nginx:1.17.1-alpine
# COPY nginx.conf /etc/nginx/nginx.conf
# COPY /dist/aston-villa-app /usr/share/nginx/html
#docker build . -t justamitsaha/bank-app-ui
#docker  run -d --name webapp -p 80:80 justamitsaha/bank-app-ui
#docker  run -d --name webapp -p 80:80 --restart always justamitsaha/bank-app-ui