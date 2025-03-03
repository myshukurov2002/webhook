#!/bin/bash

# 🔹 application.properties ichidan ma'lumotlarni olish
BOT_TOKEN=$(grep "^telegram.bot.token=" src/main/resources/application.properties | cut -d'=' -f2)
NGROK_AUTH=$(grep "^ngrok.auth.token=" src/main/resources/application.properties | cut -d'=' -f2)
SERVER_PORT=$(grep "^server.port=" src/main/resources/application.properties | cut -d'=' -f2)

echo "🟢 NGROK avtentifikatsiya qilinmoqda..."
ngrok authtoken "$NGROK_AUTH"

echo "🟢 NGROK $SERVER_PORT-portda ishga tushmoqda..."
ngrok http "$SERVER_PORT" > /dev/null &

# 🔹 NGROK'ning ishga tushishini 3 sekund kutish
sleep 3

# 🔹 NGROK URL'ni olish
NGROK_URL=$(curl -s http://127.0.0.1:4040/api/tunnels | grep -o '"public_url":"[^"]*' | cut -d'"' -f4)

echo "🔹 Webhook'ni o‘rnatish"
WEBHOOK_URL="$NGROK_URL/"
echo "🟢 Webhook o‘rnatilmoqda: $WEBHOOK_URL"
curl -X POST "https://api.telegram.org/bot$BOT_TOKEN/setWebhook?url=$WEBHOOK_URL"

# 🔹 Spring Boot ilovasini ishga tushirish
echo "🟢 Spring Boot ishga tushmoqda..."
mvn spring-boot:run
