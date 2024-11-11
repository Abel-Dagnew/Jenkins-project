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
        AZURE_CLIENT_ID       = credentials('ARM_CLIENT_ID')
        AZURE_CLIENT_SECRET   = credentials('ARM_CLIENT_SECRET')
        AZURE_TENANT_ID       = credentials('ARM_TENANT_ID')
        AZURE_SUBSCRIPTION_ID = credentials('ARM_SUBSCRIPTION_ID')
    }
    stages {
        stage('Login to Azure') {
            steps {
                script {
                    // Use Azure CLI to login to Azure using Service Principal credentials
                    withCredentials([azureServicePrincipal(credentialsId: 'azure-service-principal-id')]) {
                        // Logging in to Azure with Service Principal credentials
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
                    // Log in to Azure Container Registry using Azure CLI
                    sh '''
                        az acr login --name ${ACR_LOGIN_SERVER}
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