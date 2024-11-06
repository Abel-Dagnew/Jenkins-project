# Resource Group
variable "resource_group_name" {
  description = "The name of the resource group in which to create the ACR"
  type        = string
  default     = "myResourceGroup"
}

variable "location" {
  description = "The Azure region in which to create the resources"
  type        = string
  default     = "West Europe"
}

# Container Registry
variable "acr_name" {
  description = "The name of the Azure Container Registry"
  type        = string
  default     = "AbellaRegistry"
}

variable "acr_sku" {
  description = "The SKU of the Azure Container Registry (e.g., Basic, Standard, Premium)"
  type        = string
  default     = "Standard"
}

variable "acr_admin_enabled" {
  description = "Enable admin access to the Azure Container Registry"
  type        = bool
  default     = true
}

# Optional Tags
variable "environment" {
  description = "The environment for the ACR (e.g., dev, staging, prod)"
  type        = string
  default     = "production"
}

variable "project" {
  description = "The name of the project"
  type        = string
  default     = "myProject"
}
