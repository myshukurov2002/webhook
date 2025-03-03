@echo off
setlocal enabledelayedexpansion

REM 🔹 application.properties ichidan ma'lumotlarni olish
for /f "tokens=2 delims==" %%a in ('findstr /R "^telegram.bot.token=" src\main\resources\application.properties') do set BOT_TOKEN=%%a
for /f "tokens=2 delims==" %%a in ('findstr /R "^ngrok.auth.token=" src\main\resources\application.properties') do set NGROK_AUTH=%%a
for /f "tokens=2 delims==" %%a in ('findstr /R "^server.port=" src\main\resources\application.properties') do set SERVER_PORT=%%a

echo 🟢 NGROK avtentifikatsiya qilinmoqda...
ngrok authtoken %NGROK_AUTH%

echo 🟢 NGROK %SERVER_PORT%-portda ishga tushmoqda...
start /B ngrok http %SERVER_PORT%

REM 🔹 NGROK'ning ishga tushishini 3sek kutish
timeout /t 3 /nobreak >nul

REM 🔹 NGROK URL'ni olish (PowerShell orqali)
for /f %%a in ('powershell -Command "(Invoke-WebRequest -UseBasicParsing http://127.0.0.1:4040/api/tunnels).Content | ConvertFrom-Json | Select-Object -ExpandProperty tunnels | Where-Object {$_.proto -eq 'https'} | Select-Object -ExpandProperty public_url"') do (
    set NGROK_URL=%%a
)

echo 🔹 Webhook'ni o‘rnatish
set WEBHOOK_URL=%NGROK_URL%/
echo 🟢 Webhook o‘rnatilmoqda: %WEBHOOK_URL%
curl -X POST "https://api.telegram.org/bot%BOT_TOKEN%/setWebhook?url=%WEBHOOK_URL%"

REM 🔹 Spring Boot ilovasini ishga tushirish
echo 🟢 Spring Boot ishga tushmoqda...
mvn spring-boot:run
