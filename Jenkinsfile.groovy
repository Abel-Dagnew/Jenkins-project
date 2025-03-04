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
        DOCKER_IMAGE_TAG = "${ACR_LOGIN_SERVER}/${DOCKER_IMAGE_NAME}:latest"
        GITHUB_REPO = "https://github.com/Abel-Dagnew/Jenkins-project.git"
        AZURE_WEB_APP_NAME = "abel-bot"
        AZURE_RESOURCE_GROUP = "abel-bot-rg"
        AZURE_PLAN_NAME = "${AZURE_WEB_APP_NAME}-plan"
        KEY_VAULT_NAME = "abel-bot-kv"
        REGION = "eastus"

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

        stage('Create App Service Plan and Web App') {
            steps {
                script {
                    // Create App Service Plan if it doesn't exist
                    sh """
                        az appservice plan create --name ${AZURE_PLAN_NAME} \
                            --resource-group ${AZURE_RESOURCE_GROUP} \
                            --location ${REGION} --sku B1 --is-linux || echo "App Service Plan may already exist."
                    """

                    // Create Web App if it doesn't exist
                    sh """
                        az webapp create --resource-group ${AZURE_RESOURCE_GROUP} \
                            --plan ${AZURE_PLAN_NAME} \
                            --name ${AZURE_WEB_APP_NAME} \
                            --deployment-container-image-name ${DOCKER_IMAGE_TAG} || echo "Web app may already exist."
                    """
                }
            }
        }

        stage('Create Key Vault and Store Secrets') {
            steps {
                script {
                    sh """
                        az keyvault create --name ${KEY_VAULT_NAME} \
                            --resource-group ${AZURE_RESOURCE_GROUP} \
                            --location ${REGION} || echo "Key Vault may already exist."
                    """

                    sh """
                        az keyvault secret set --vault-name ${KEY_VAULT_NAME} --name "MT5-LOGIN" --value '${MT5_LOGIN}'
                        az keyvault secret set --vault-name ${KEY_VAULT_NAME} --name "MT5-PASSWORD" --value '${MT5_PASSWORD}'
                        az keyvault secret set --vault-name ${KEY_VAULT_NAME} --name "MT5-SERVER" --value '${MT5_SERVER}'
                    """

                    def keyVaultUrl = sh(script: """
                        az keyvault show --name ${KEY_VAULT_NAME} --resource-group ${AZURE_RESOURCE_GROUP} --query properties.vaultUri -o tsv
                    """, returnStdout: true).trim()

                    sh "az webapp config appsettings set --name ${AZURE_WEB_APP_NAME} --resource-group ${AZURE_RESOURCE_GROUP} --settings KEY_VAULT_URL=${keyVaultUrl}"
                }
            }
        }

        stage('Build And Push Docker Image') {
            steps {
                script {
                    BuildPushDockerImage(ACR_NAME, DOCKER_IMAGE_NAME, ACR_LOGIN_SERVER)
                }
            }
        }

        stage('Deploy Image to Web App') {
            steps {
                PullImageToWebApp(AZURE_WEB_APP_NAME, AZURE_RESOURCE_GROUP, ACR_LOGIN_SERVER, DOCKER_IMAGE_NAME, ACR_USERNAME, ACR_PASSWORD)
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed!'
        }
    }
}
