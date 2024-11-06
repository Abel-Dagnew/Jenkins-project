terraform {
  required_providers {
    azuredevops = {
      source  = "microsoft/azuredevops"
      version = "~> 0.1"
    }
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.0"  # Specify the version you are using
    }
  }
}





module "Create_App_Service" {
  
  source = "./Create_App_Service"
 
     
}
module "Create_container_registry" {
  
  source = "./Create_container_registry"
 
     
}







