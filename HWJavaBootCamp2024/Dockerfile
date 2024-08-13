FROM ubuntu:latest
COPY src/main/java /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac AccountantApplication.java
CMD ["java", "Main"]
