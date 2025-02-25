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



# Example Dockerfile
FROM postgres:latest

# Copy SQL file with a shorter name
COPY init.sql /docker-entrypoint-initdb.d/

# Set environment variables
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=yourpassword
ENV POSTGRES_DB=mydb

# Expose PostgreSQL port
EXPOSE 5432

# Start PostgreSQL
CMD ["postgres"]


# Run PostgreSQL server
CMD ["postgres"]



