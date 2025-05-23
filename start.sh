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
# Veritabanı varsa hata vermemesini sağla
su - postgres -c "psql -c \"SELECT 'CREATE DATABASE ironplug' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'ironplug')\gexec\""
echo "PostgreSQL kullanıcı ve veritabanı oluşturuldu..."

# Spring Boot uygulamasını başlat
echo "Spring Boot uygulaması başlatılıyor..."
java -jar /app/app.jar 