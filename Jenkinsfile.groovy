pipeline {
    agent {
        docker {
            image 'docker:stable'   // Use Docker's official image with Docker CLI installed
            args '-v /var/run/docker.sock:/var/run/docker.sock'   // Mount Docker socket for communication
        }
    }
    environment {
        // Azure container registry details
        ACR_NAME = "abelregistryy"  // Your ACR name
        ACR_LOGIN_SERVER = "${ACR_NAME}.azurecr.io"  // ACR login server URL
        ACR_USERNAME = "abelregistryy"  // ACR username
        ACR_PASSWORD = credentials('ACR_Pass')  // ACR password stored in Jenkins credentials
        DOCKER_IMAGE_NAME = "Abelimage1st"  // Name of your Docker image
        GITHUB_REPO = "https://github.com/Abel-Dagnew/Jenkins-project.git"  // GitHub repository URL
        DOCKER_CREDENTIALS_ID = credentials('c3c94d98-85b5-49e7-b6fd-9d1f6f6838ea')
    }
    stages {
        stage('Login to Azure') {
            steps {
                script {
                    // Use withCredentials to inject the Azure Service Principal credentials
                    withCredentials([ 
                        string(credentialsId: 'ARM_CLIENT_ID', variable: 'AZURE_CLIENT_ID'),
                        string(credentialsId: 'ARM_CLIENT_SECRET', variable: 'AZURE_CLIENT_SECRET'),
                        string(credentialsId: 'ARM_TENANT_ID', variable: 'AZURE_TENANT_ID'),
                        string(credentialsId: 'ARM_SUBSCRIPTION_ID', variable: 'AZURE_SUBSCRIPTION_ID')
                    ]) {
                        // Logging in to Azure using the Service Principal credentials
                        sh '''
                            az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET --tenant $AZURE_TENANT_ID
                            az account set --subscription $AZURE_SUBSCRIPTION_ID
                        '''
                    }
                }
            }
        }

        stage('Login to ACR') {
            steps {
                script {
                    // Log in to Azure Container Registry using username and password
                    sh '''
                        echo ${ACR_PASSWORD} | docker login ${ACR_LOGIN_SERVER} --username ${ACR_USERNAME} --password-stdin
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image and tag it with the ACR repository
                    sh '''
                        docker build -t ${ACR_LOGIN_SERVER}/${DOCKER_IMAGE_NAME}:latest .
                    '''
                }
            }
        }

        stage('Push Docker Image to ACR') {
            steps {
                script {
                    // Push the Docker image to Azure Container Registry
                    sh '''
                        docker push ${ACR_LOGIN_SERVER}/${DOCKER_IMAGE_NAME}:latest
                    '''
                }
            }
        }
    }

    post {
        always {
            cleanWs()  // Clean up workspace after the job
        }
    }
}
