#!/bin/bash
set -e

echo "Spring Boot uygulamasi baslatiliyor..."

# Profil ve port kontrolü
if [ -z "$SPRING_PROFILES_ACTIVE" ]; then
  SPRING_PROFILES_ACTIVE=docker
fi

if [ -z "$SERVER_PORT" ]; then
  if [ "$SPRING_PROFILES_ACTIVE" = "render" ]; then
    SERVER_PORT=10000
  else
    SERVER_PORT=8082
  fi
fi

echo "Aktif profil: $SPRING_PROFILES_ACTIVE"
echo "Kullanılan port: $SERVER_PORT"

# Uygulamayı başlat
exec java -jar \
  -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE \
  -Dserver.port=$SERVER_PORT \
  /app/app.jar
