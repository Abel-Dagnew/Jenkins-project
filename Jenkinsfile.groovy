pipeline {
    agent any
    environment {
        // Azure container registry details
        ACR_NAME = "abelregistryy"  // Your ACR name
        ACR_LOGIN_SERVER = "${ACR_NAME}.azurecr.io"  // ACR login server URL
        ACR_USERNAME = "abelregistryy"  // ACR username
        ACR_PASSWORD = credentials('ACR_Pass')  // ACR password
        DOCKER_IMAGE_NAME = "Abelimage1st"  // Name of your Docker image
        GITHUB_REPO = "https://github.com/Abel-Dagnew/Jenkins-project.git"  // GitHub repository URL
    }
    stages {
        stage('Checkout Code') {
            steps {
                // Clone the GitHub repository
                git url: "${GITHUB_REPO}", branch: 'main'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image from the Dockerfile in the repository
                    sh 'docker build -t ${ACR_LOGIN_SERVER}/${DOCKER_IMAGE_NAME}:latest .'
                }
            }
        }
        
        stage('Login to Azure Container Registry') {
            steps {
                script {
                    // Login to Azure Container Registry using the username and password
                    sh 'docker login ${ACR_LOGIN_SERVER} -u ${ACR_USERNAME} -p ${ACR_PASSWORD}'
                }
            }
        }

        stage('Push Docker Image to ACR') {
            steps {
                script {
                    // Push the Docker image to ACR
                    sh 'docker push ${ACR_LOGIN_SERVER}/${DOCKER_IMAGE_NAME}:latest'
                }
            }
        }
    }
    post {
        always {
            // Clean up Docker images
            sh 'docker rmi ${ACR_LOGIN_SERVER}/${DOCKER_IMAGE_NAME}:latest'
        }
    }
}
