@Library('shared-lib') _

pipeline {
    agent any

    environment {
        // Azure container registry details
        ACR_NAME = "abelregistryy"
        ACR_LOGIN_SERVER = "${ACR_NAME}.azurecr.io"
        ACR_USERNAME = "abelregistryy"
        ACR_PASSWORD = credentials('ACR_Pass')
        DOCKER_IMAGE_NAME = "abel-bot"
        GITHUB_REPO = "https://github.com/Abel-Dagnew/Jenkins-project.git"
        DOCKER_USERNAME = "abel13"
        AZURE_WEB_APP_NAME = "abel-bot"
        AZURE_RESOURCE_GROUP = "abel-bot-rg"
        KEY_VAULT_NAME = "abel-bot-kv"
        NAMESPACE = "myproject"
        
        // MT5 credentials
        MT5_LOGIN = credentials('MT5_LOGIN')
        MT5_PASSWORD = credentials('MT5_PASSWORD')
        MT5_SERVER = credentials('MT5_SERVER')
    }

    stages {
        stage('Login to Azure') {
            steps {
                azureLogin()
            }
        }

        stage('Login to ACR') {
            steps {
                ACRLogin(ACR_PASSWORD, ACR_LOGIN_SERVER, ACR_USERNAME)
            }
        }

        stage('Create Key Vault') {
            steps {
                script {
                    // Create Key Vault
                    sh """
                        az keyvault create --name ${KEY_VAULT_NAME} \
                            --resource-group ${AZURE_RESOURCE_GROUP} \
                            --location eastus
                    """
                    
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
                            --resource-group ${AZURE_RESOURCE_GROUP} \
                            --query properties.vaultUri -o tsv)
                        
                        az webapp config appsettings set --name ${AZURE_WEB_APP_NAME} \
                            --resource-group ${AZURE_RESOURCE_GROUP} \
                            --settings KEY_VAULT_URL=$KEY_VAULT_URL
                    """
                }
            }
        }

        stage('Build And Push Docker Image') {
            steps {
                script {
                    BuildPushDockerImage(ACR_NAME, DOCKER_IMAGE_NAME, ACR_LOGIN_SERVER)()
                }
            }
        }
        stage('Pull Image to Web App') {
            steps {
                PullImageToWebApp(AZURE_WEB_APP_NAME, AZURE_RESOURCE_GROUP, ACR_LOGIN_SERVER,DOCKER_IMAGE_NAME, ACR_USERNAME,ACR_PASSWORD ) // Call the shared library function
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed!'
        }
    }
}