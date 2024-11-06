terraform {
  required_providers {
    azuredevops = {
      source  = "microsoft/azuredevops"
      version = "~> 0.1"
    }
    
  }
}
provider "azurerm" {
  features {}
  alias           = "azresourceprovider"
  client_id       = var.client_id       // Reference a variable
  client_secret   = var.client_secret   // Reference a variable
  tenant_id       = var.tenant_id       // Reference a variable
  subscription_id = var.subscription_id  // Reference a variable
}





module "Create_container_registry" {
  
  source = "./Create_container_registry"
  providers = {
    azurerm = azurerm.azresourceprovider
  }
     
}








