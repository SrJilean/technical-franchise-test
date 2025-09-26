# Usar la misma VPC de RDS
data "aws_vpc" "rds_vpc" {
  id = "vpc-02bb3861cedac74cc"
}

# Subnets privadas (donde está RDS) - solo para conexión DB
data "aws_subnets" "rds_subnets" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.rds_vpc.id]
  }
}

# Subnets públicas (donde debe correr ECS con IP pública)
data "aws_subnets" "public_subnets" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.rds_vpc.id]
  }

  # 👇 Esto depende de cómo nombraste tus subnets
  filter {
    name   = "tag:Name"
    values = ["*public*"]
  }
}

# Security Group para ECS
resource "aws_security_group" "ecs_sg" {
  name   = "${var.project}-ecs-sg-${var.env}"
  vpc_id = data.aws_vpc.rds_vpc.id

  ingress {
    description = "HTTP acceso a contenedor"
    from_port   = var.container_port
    to_port     = var.container_port
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # expone tu API, luego se puede restringir
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group_rule" "ecs_to_rds" {
  type                     = "ingress"
  from_port                = 3306
  to_port                  = 3306
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.ecs_sg.id
  security_group_id        = "sg-04a74b3d0fe6137c8" # SG de tu RDS
}
