FROM java:openjdk-8-jdk

ADD ./target/dist/btca.tar.gz /opt/btca

WORKDIR /opt/btca

CMD ["java", "-jar", "btca.jar"]
