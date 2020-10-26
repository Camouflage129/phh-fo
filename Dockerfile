FROM camouflage129/base:0.1
RUN apk update
RUN apk add busybox-extras
COPY target/fo-0.1.jar /home/phh-fo.jar
CMD nohup java -jar /home/phh-fo.jar 1> /dev/null 2>&1
EXPOSE 9000