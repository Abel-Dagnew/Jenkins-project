pipeline {
    agent any
    
    environment {
        TF_VAR_client_id       = credentials('ARM_CLIENT_ID')        // Retrieve from Jenkins credentials
        TF_VAR_client_secret   = credentials('ARM_CLIENT_SECRET')    // Retrieve from Jenkins credentials
        TF_VAR_tenant_id       = credentials('ARM_TENANT_ID')        // Retrieve from Jenkins credentials
        TF_VAR_subscription_id = credentials('ARM_SUBSCRIPTION_ID') 
    }
    
    stages {
        stage('Terraform Init') {
            steps {
                script {
                    echo 'Initializing Terraform...'
                    sh 'terraform init'
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    echo 'Running Terraform Plan...'
                    sh 'terraform plan -target=module.Create_App_Service '
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    echo 'Applying Terraform Configuration...'
                    sh 'terraform apply -target=module.Create_App_Service  -auto-approve'
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
            echo 'Terraform Apply failed.'
        }
    }
}
