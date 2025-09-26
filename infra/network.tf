# Usar la misma VPC de RDS
data "aws_vpc" "rds_vpc" {
  id = "vpc-02bb3861cedac74cc"
}

# Subnets privadas (para RDS)
data "aws_subnets" "rds_subnets" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.rds_vpc.id]
  }
}

# Subnets públicas (para ALB y ECS)
data "aws_subnets" "public_subnets" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.rds_vpc.id]
  }

  filter {
    name   = "map-public-ip-on-launch"
    values = ["true"]
  }
}

# Security Group para el ALB (público)
resource "aws_security_group" "alb_sg" {
  name   = "${var.project}-alb-sg-${var.env}"
  vpc_id = data.aws_vpc.rds_vpc.id

  ingress {
    description = "Permitir_HTTP_publico"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # abierto a internet
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Security Group para ECS (containers)
resource "aws_security_group" "ecs_sg" {
  name   = "${var.project}-ecs-sg-${var.env}"
  vpc_id = data.aws_vpc.rds_vpc.id

  ingress {
    description     = "Trafico_desde_ALB"
    from_port       = var.container_port
    to_port         = var.container_port
    protocol        = "tcp"
    security_groups = [aws_security_group.alb_sg.id] # solo desde el ALB
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Regla: permitir que ECS acceda al RDS
resource "aws_security_group_rule" "ecs_to_rds" {
  type                     = "ingress"
  from_port                = 3306
  to_port                  = 3306
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.ecs_sg.id
  security_group_id        = "sg-04a74b3d0fe6137c8" # SG del RDS
}
