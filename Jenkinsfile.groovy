@Library('shared-lib') _

pipeline {
    agent any

    environment {
        // Azure configuration
        AZURE_SUBSCRIPTION_ID = credentials('AZURE_SUBSCRIPTION_ID')
        AZURE_TENANT_ID = credentials('AZURE_TENANT_ID')
        AZURE_CLIENT_ID = credentials('AZURE_CLIENT_ID')
        AZURE_CLIENT_SECRET = credentials('AZURE_CLIENT_SECRET')
        
        // Resource names
        RESOURCE_GROUP = "abel-bot-rg"
        LOCATION = "eastus"
        WEBAPP_NAME = "abel-bot"
        CONTAINER_REGISTRY_NAME = "abelbotregistry"
        APP_SERVICE_PLAN = "abel-bot-plan"
        KEY_VAULT_NAME = "abel-bot-kv"
        
        // Container configuration
        DOCKER_IMAGE_NAME = "abel-bot"
        DOCKER_TAG = "latest"
        
        // MT5 credentials
        MT5_LOGIN = credentials('MT5_LOGIN')
        MT5_PASSWORD = credentials('MT5_PASSWORD')
        MT5_SERVER = credentials('MT5_SERVER')
        
        // GitHub configuration
        GITHUB_REPO = "https://github.com/your-username/abel-bot.git"
        GITHUB_BRANCH = "main"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Login to Azure') {
            steps {
                azureLogin()
            }
        }

        stage('Create Azure Resources') {
            steps {
                script {
                    // Create resource group
                    sh """
                        az group create --name ${RESOURCE_GROUP} --location ${LOCATION}
                    """
                    
                    // Create Azure Container Registry
                    sh """
                        az acr create --resource-group ${RESOURCE_GROUP} \
                            --name ${CONTAINER_REGISTRY_NAME} \
                            --sku Basic \
                            --admin-enabled true
                    """
                    
                    // Create App Service Plan
                    sh """
                        az appservice plan create --name ${APP_SERVICE_PLAN} \
                            --resource-group ${RESOURCE_GROUP} \
                            --location ${LOCATION} \
                            --sku B1 \
                            --is-linux
                    """
                    
                    // Create Web App for Containers
                    sh """
                        az webapp create --resource-group ${RESOURCE_GROUP} \
                            --plan ${APP_SERVICE_PLAN} \
                            --name ${WEBAPP_NAME} \
                            --deployment-container-image-name "${CONTAINER_REGISTRY_NAME}.azurecr.io/${DOCKER_IMAGE_NAME}:${DOCKER_TAG}"
                    """
                    
                    // Create Key Vault
                    sh """
                        az keyvault create --name ${KEY_VAULT_NAME} \
                            --resource-group ${RESOURCE_GROUP} \
                            --location ${LOCATION}
                    """
                }
            }
        }

        stage('Configure Key Vault') {
            steps {
                script {
                    // Store MT5 credentials in Key Vault
                    sh """
                        az keyvault secret set --vault-name ${KEY_VAULT_NAME} \
                            --name "MT5-LOGIN" \
                            --value "${MT5_LOGIN}"
                    """
                    
                    sh """
                        az keyvault secret set --vault-name ${KEY_VAULT_NAME} \
                            --name "MT5-PASSWORD" \
                            --value "${MT5_PASSWORD}"
                    """
                    
                    sh """
                        az keyvault secret set --vault-name ${KEY_VAULT_NAME} \
                            --name "MT5-SERVER" \
                            --value "${MT5_SERVER}"
                    """
                    
                    // Get Key Vault URL and configure Web App
                    sh """
                        KEY_VAULT_URL=$(az keyvault show --name ${KEY_VAULT_NAME} \
                            --resource-group ${RESOURCE_GROUP} \
                            --query properties.vaultUri -o tsv)
                        
                        az webapp config appsettings set --name ${WEBAPP_NAME} \
                            --resource-group ${RESOURCE_GROUP} \
                            --settings KEY_VAULT_URL=$KEY_VAULT_URL
                    """
                }
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                script {
                    // Get ACR credentials
                    sh """
                        ACR_USERNAME=$(az acr credential show --name ${CONTAINER_REGISTRY_NAME} --query username -o tsv)
                        ACR_PASSWORD=$(az acr credential show --name ${CONTAINER_REGISTRY_NAME} --query "passwords[0].value" -o tsv)
                        
                        # Configure Web App to use ACR
                        az webapp config container set --name ${WEBAPP_NAME} \
                            --resource-group ${RESOURCE_GROUP} \
                            --docker-custom-image-name "${CONTAINER_REGISTRY_NAME}.azurecr.io/${DOCKER_IMAGE_NAME}:${DOCKER_TAG}" \
                            --docker-registry-server-url "https://${CONTAINER_REGISTRY_NAME}.azurecr.io" \
                            --docker-registry-server-user $ACR_USERNAME \
                            --docker-registry-server-password $ACR_PASSWORD
                    """
                    
                    // Build and push Docker image
                    sh """
                        az acr build --registry ${CONTAINER_REGISTRY_NAME} \
                            --image ${DOCKER_IMAGE_NAME}:${DOCKER_TAG} \
                            .
                    """
                }
            }
        }

        stage('Deploy to Web App') {
            steps {
                script {
                    // Restart Web App to apply changes
                    sh """
                        az webapp restart --name ${WEBAPP_NAME} \
                            --resource-group ${RESOURCE_GROUP}
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Deployment successful! Web App URL: https://${WEBAPP_NAME}.azurewebsites.net"
        }
        failure {
            echo "Deployment failed! Check the logs for details."
        }
        always {
            cleanWs()
        }
    }
}