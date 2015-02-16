FROM dockerfile/java:oracle-java8

ADD ./target/dist/btca.tar.gz /opt/btca

WORKDIR /opt/btca

CMD ["java", "-jar", "btca.jar"]
