#!/bin/bash
set -e

# PostgreSQL servisini başlat
service postgresql start
echo "PostgreSQL servisi başlatıldı..."

# PostgreSQL'in hazır olup olmadığını kontrol et
until pg_isready -h localhost; do
  echo "PostgreSQL başlatılıyor, lütfen bekleyin..."
  sleep 2
done

# PostgreSQL kullanıcısı ve veritabanını oluştur
su - postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD 'postgres';\""
# Veritabanı oluşturma - varsa hata vermez
su - postgres -c "psql -c \"CREATE DATABASE ironplug WITH OWNER postgres;\" || echo 'Veritabanı zaten var, devam ediliyor...'"
echo "PostgreSQL kullanıcı ve veritabanı oluşturuldu..."

# Spring Boot uygulamasını başlat
echo "Spring Boot uygulaması başlatılıyor..."
java -jar /app/app.jar 