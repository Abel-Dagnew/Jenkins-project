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



# Use official PostgreSQL image
FROM postgres:13.4

# Set default environment variables (these can be overridden in Azure App Service)
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=yourpassword
ENV POSTGRES_DB=mydb

# Ensure PostgreSQL stores data in the mounted Azure File Share
VOLUME ["/var/lib/postgresql/data"]

# Expose PostgreSQL port
EXPOSE 5432

# Run PostgreSQL server
CMD ["postgres"]



