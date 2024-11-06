terraform {
  required_providers {
    azuredevops = {
      source  = "microsoft/azuredevops"
      version = "~> 0.1"
    }
    
  }
}






module "Create_container_registry" {
  
  source = "./Create_container_registry"
  providers = {
    azurerm = azurerm.azresourceprovider
  }
     
}








