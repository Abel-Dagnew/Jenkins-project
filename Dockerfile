# Use the official Node.js image as the base image
FROM node:22

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json (if available)
COPY ./app/package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of your application code
COPY ./app .

# Expose the port the app runs on
EXPOSE 3000

# Command to run the application
CMD ["node", "app.js"]