pipeline {
    agent any

    environment {
        TF_VAR_client_id       = credentials('ARM_CLIENT_ID')
        TF_VAR_client_secret   = credentials('ARM_CLIENT_SECRET')
        TF_VAR_tenant_id       = credentials('ARM_TENANT_ID')
        TF_VAR_subscription_id = credentials('ARM_SUBSCRIPTION_ID')
    }

    stages {
        stage('Terraform Init') {
            steps {
                script {
                    echo 'Initializing Terraform...'
                    // Check current PATH
                    echo "Current PATH: ${env.PATH}" // Output the current PATH
                    sh 'terraform init' // Make sure terraform is installed in PATH
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    echo 'Running Terraform Plan...'
                    // Provide feedback on the plan process
                    sh 'terraform plan -target=module.Create_container_registry'
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    echo 'Applying Terraform Configuration...'
                    // Provide feedback before applying
                    sh 'terraform apply -target=module.Create_container_registry -auto-approve'
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            deleteDir()
        }
        success {
            echo 'Terraform Apply completed successfully!'
        }
        failure {
            echo 'Terraform Apply failed. Please check the logs for errors.'
        }
    }
}


pipeline {
    agent any
    environment {
        // Azure container registry details
        ACR_NAME = "abelregistryy"  // Your ACR name
        ACR_LOGIN_SERVER = "${ACR_NAME}.azurecr.io"  // ACR login server URL
        ACR_USERNAME = "abelregistryy"  // ACR username
        ACR_PASSWORD = "jphu8GP6Wgg3TOyRSsoNYp6kxlKw+H5r6y4HnDVM9g+ACRDtGhSR"  // ACR password
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
