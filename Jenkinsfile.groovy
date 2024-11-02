pipeline {
    agent any
    
    environment {
        TF_VAR_client_id       = credentials('ARM_CLIENT_ID')        // Retrieve from Jenkins credentials
        TF_VAR_client_secret   = credentials('ARM_CLIENT_SECRET')    // Retrieve from Jenkins credentials
        TF_VAR_tenant_id       = credentials('ARM_TENANT_ID')        // Retrieve from Jenkins credentials
        TF_VAR_subscription_id = credentials('ARM_SUBSCRIPTION_ID') 
    }
    
    stages {
        stage('Install Terraform') {
            steps {
                script {
                    echo 'Installing Terraform on Linux...'
                    sh '''
                        # Install required packages without sudo
                        apt-get update || true
                        apt-get install -y unzip curl jq || true
                        # Download the latest version of Terraform
                        curl -LO https://releases.hashicorp.com/terraform/$(curl -s https://checkpoint-api.hashicorp.com/v1/check/terraform | jq -r .current_version)/terraform_$(curl -s https://checkpoint-api.hashicorp.com/v1/check/terraform | jq -r .current_version)_linux_amd64.zip
                        # Unzip and move to a directory in the PATH
                        unzip terraform_*_linux_amd64.zip
                        mv terraform ~/bin/  # Move to a directory in the user's PATH
                        # Clean up downloaded files
                        rm terraform_*_linux_amd64.zip
                    '''
                    
                    // Ensure ~/bin is in the PATH
                    sh 'echo "$PATH"'
                    
                    // Verify installation
                    sh '~/bin/terraform -version'
                }
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    echo 'Initializing Terraform...'
                    sh '~/bin/terraform init'
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    echo 'Running Terraform Plan...'
                    sh '~/bin/terraform plan -target=module.Create_App_Service'
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    echo 'Applying Terraform Configuration...'
                    sh '~/bin/terraform apply -target=module.Create_App_Service -auto-approve'
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
