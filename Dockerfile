FROM debian:bullseye-slim

# 필요한 패키지 업데이트 및 설치
RUN apt update && \
    apt install -y openjdk-17-jdk lsof net-tools && \
    apt autoremove -y && \
    apt clean

# 환경 변수 설정 (Java 홈 디렉토리)
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# WAR 파일 복사
ARG WAR_FILE=build/libs/*-SNAPSHOT.war
COPY ${WAR_FILE} app.war

# 컨테이너에서 사용할 포트(명시하는 역할)
EXPOSE 3030

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.war"]
