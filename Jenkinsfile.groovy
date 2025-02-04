@Library('shared-lib') _  // Replace with actual library name


pipeline {
    agent any

    environment {
        // Azure container registry details
        ACR_NAME = "abelregistryy"  // Your ACR nam
        ACR_LOGIN_SERVER = "${ACR_NAME}.azurecr.io"  // ACR login server URL
        ACR_USERNAME = "abelregistryy"  // ACR username
        ACR_PASSWORD = credentials('ACR_Pass')  // ACR password stored in Jenkins credentials
        DOCKER_IMAGE_NAME = "mydocker-repo"  // Name of your Docker image
        GITHUB_REPO = "https://github.com/Abel-Dagnew/Jenkins-project.git"  // GitHub repository URL
        DOCKER_USERNAME = "abel13"
        AZURE_WEB_APP_NAME = "Abel-Container234" // Azure Web App name
        AZURE_RESOURCE_GROUP = "Abel-Container234_group" // Azure Resource Group where Web App resides
        NAMESPACE = "myproject"
    }

    stages {

        stage('Login to Azure') {
            steps {
                azureLogin()  // Call the shared library function
            }
        }

        stage('Login to ACR') {
            steps {
                ACRLogin() // Call the shared library function
            }
        }

        stage('Build And Push Docker Image') {
            steps {
                BuildPushDockerImage() // Call the shared library function
            }
        }

        

        // stage('Pull Image to Web App') {
        //     steps {
        //         PullImageToWebApp() // Call the shared library function
        //     }
        // }
        stage('Deploy to AKS') {
            steps {
                DeployToAKS() // Call the shared library function
            }
        // }
        // stage('Deploy Prometheus') {
        //     steps {
        //         DeployPrometheus()
        //     }
        // }
        // stage('Deploy Grafana') {
        //     steps {
        //         DeployGrafana()
        //     }
        // }
    }
    }
    post {
        always {
            echo 'Pipeline completed!'
    }
    }
}
