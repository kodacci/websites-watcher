# syntax=docker/dockerfile:1
FROM eclipse-temurin:25.0.2_10-jre-noble

LABEL authors="Andrey Ryabtsev"

RUN useradd -U websites-watcher
WORKDIR /home/websites-watcher
USER websites-watcher
COPY --chown=websites-watcher core/target/websites-watcher-core.jar ./
COPY --chown=websites-watcher distrib/docker/core/entrypoint.sh ./
RUN chmod +x ./entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/home/websites-watcher/entrypoint.sh"]
