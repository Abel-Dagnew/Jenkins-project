# variables.tf
variable "resource_group_name" {
  type    = string
  default = "AbelJenkinsRG"
}

variable "location" {
  type    = string
  default = "West Europe"
}

variable "app_service_plan_name" {
  type    = string
  default = "AbelASP"
}

variable "app_service_name" {
  type    = string
  default = "Jenkins-Project2232"
}