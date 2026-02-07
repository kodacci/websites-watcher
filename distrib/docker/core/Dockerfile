# syntax=docker/dockerfile:1
FROM docker-registry.ra-tech.pro/pro.ra-tech/docker-base/eclipse-jre-25-chromedriver:noble

LABEL authors="Andrey Ryabtsev"

RUN useradd -U websites-watcher
WORKDIR /home/websites-watcher
USER websites-watcher
COPY --chown=websites-watcher core/target/websites-watcher-core.jar ./
COPY --chown=websites-watcher distrib/docker/core/entrypoint.sh ./
RUN chmod +x ./entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/home/websites-watcher/entrypoint.sh"]
