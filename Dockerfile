# # Step 1: Build the Vite App
# FROM node:22 AS builder

# # Set the working directory
# WORKDIR /usr/src/gofer-app

# # Copy package.json and install dependencies
# COPY ./gofer-app/package*.json ./
# RUN npm install

# # Copy the entire project
# COPY ./gofer-app .

# # Build the Vite app (this generates the "dist" folder)
# RUN npm run build

# # Step 2: Serve with Nginx
# FROM nginx:alpine

# # Remove default Nginx website config
# RUN rm -rf /usr/share/nginx/html/*

# # Copy built files from "builder" stage
# COPY --from=builder /usr/src/gofer-app/dist /usr/share/nginx/html

# # Expose the web server port
# EXPOSE 80

# # Start Nginx
# CMD ["nginx", "-g", "daemon off;"]



FROM python:3.9-slim

# Install system dependencies
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    wine \
    && rm -rf /var/lib/apt/lists/*

# Install MetaTrader 5
RUN wget -qO - https://www.mql5.com/en/trading-platform/start | grep -o 'https://download.mql5.com/cdn/web/metaquotes.software.corp/mt5/mt5setup.exe' | xargs wget -O mt5setup.exe
RUN wine mt5setup.exe /auto

# Set working directory
WORKDIR /app

# Copy requirements and install Python packages
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy application code
COPY . .

# Create a non-root user
RUN useradd -m -r -s /bin/bash tradingbot
USER tradingbot

# Expose port for Azure Web App
EXPOSE 8000

# Run the bot with gunicorn
CMD ["python", "run_bot.py"] 

