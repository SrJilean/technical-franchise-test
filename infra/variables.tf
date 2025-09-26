variable "project" {
  default = "franchises"
}
variable "env" {
  default = "dev"
}
variable "container_port" {
  default = 8080
}

variable "db_url" {
  description = "URL de conexión R2DBC a MySQL en RDS"
  type        = string
}

variable "db_username" {
  description = "Usuario de la base de datos"
  type        = string
}

variable "db_password" {
  description = "Contraseña de la base de datos"
  type        = string
  sensitive   = true
}