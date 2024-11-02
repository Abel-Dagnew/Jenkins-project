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
                    sh 'terraform plan -target=module.Create_App_Service'
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    echo 'Applying Terraform Configuration...'
                    // Provide feedback before applying
                    sh 'terraform apply -target=module.Create_App_Service -auto-approve'
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
