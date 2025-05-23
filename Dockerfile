FROM eclipse-temurin:17-jre-focal

# PostgreSQL kurulumu
RUN apt-get update && \
    apt-get install -y postgresql postgresql-contrib && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# PostgreSQL veri dizini oluşturma
RUN mkdir -p /var/lib/postgresql/data && \
    chown -R postgres:postgres /var/lib/postgresql/data

# Maven build için gerekli
WORKDIR /app
COPY target/*.jar app.jar

# Başlatma scripti
COPY start.sh /start.sh
RUN chmod +x /start.sh

# Portlar
EXPOSE 8082 5432

# Başlatma komutu
CMD ["/start.sh"] 