pipeline {
    agent any

    environment {
        // Azure container registry details
        ACR_NAME = "abelregistryy"  // Your ACR name
        ACR_LOGIN_SERVER = "${ACR_NAME}.azurecr.io"  // ACR login server URL
        ACR_USERNAME = "abelregistryy"  // ACR username
        ACR_PASSWORD = credentials('ACR_Pass')  // ACR password stored in Jenkins credentials
        DOCKER_IMAGE_NAME = "mydocker-repo"  // Name of your Docker image
        GITHUB_REPO = "https://github.com/Abel-Dagnew/Jenkins-project.git"  // GitHub repository URL
        DOCKER_USERNAME = "abel13"
        AZURE_WEB_APP_NAME = "Abel-Container234" // Azure Web App name
        AZURE_RESOURCE_GROUP = "Abel-Container234_group" // Azure Resource Group where Web App resides
    }

    stages {

        stage('Login to Azure') {
            steps {
                script {
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
                    // Build Docker image
                    sh "docker build -t ${ACR_NAME}/${DOCKER_IMAGE_NAME}:latest ."
                }
            }
        }

        stage('List Docker Images') {
            steps {
                script {
                    // List Docker images to verify the image tag exists
                    sh 'docker images'
                }
            }
        }

        stage('Tag Docker Image') {
            steps {
                script {
                    // Tag the Docker image for Azure Container Registry
                    sh "docker tag ${ACR_NAME}/${DOCKER_IMAGE_NAME}:latest ${ACR_LOGIN_SERVER}/${DOCKER_IMAGE_NAME}:latest"
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

        stage('Pull Image to Web App') {
            steps {
                script {
                    // Configure Azure Web App to use the latest image from ACR
                    sh '''
                        az webapp config container set --name ${AZURE_WEB_APP_NAME} \
                        --resource-group ${AZURE_RESOURCE_GROUP} \
                        --docker-custom-image-name ${ACR_LOGIN_SERVER}/${DOCKER_IMAGE_NAME}:latest \
                        --docker-registry-server-url https://${ACR_LOGIN_SERVER} \
                        --docker-registry-server-user ${ACR_USERNAME} \
                        --docker-registry-server-password ${ACR_PASSWORD}
                    '''
                }
            }
        }
    }
}
