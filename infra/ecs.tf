resource "aws_ecs_cluster" "this" {
  name = "${var.project}-cluster-${var.env}"
}

resource "aws_ecs_task_definition" "app" {
  family                   = "${var.project}-task-${var.env}"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.task_execution.arn

  container_definitions = jsonencode([
    {
      name      = "app"
      image     = "${aws_ecr_repository.app.repository_url}:latest"
      essential = true
      portMappings = [{
        containerPort = var.container_port
        hostPort      = var.container_port
        protocol      = "tcp"
      }]
      environment = [
        { name = "SPRING_R2DBC_URL", value = var.db_url },
        { name = "SPRING_R2DBC_USERNAME", value = var.db_username },
        { name = "SPRING_R2DBC_PASSWORD", value = var.db_password }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.app.name
          awslogs-region        = "us-east-1"
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])
}

# Application Load Balancer
resource "aws_lb" "app" {
  name               = "${var.project}-alb-${var.env}"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.alb_sg.id]

  # ✅ Usamos todas las subnets públicas que confirmaste
  subnets = [
    "subnet-09c1b6c9ae2bdfa08", # us-east-1f
    "subnet-07f4efeeaea60d24b", # us-east-1e
    "subnet-0b406eb1a7e1f9e69", # us-east-1b
    "subnet-0f3ad9a9a57403a31", # us-east-1c
    "subnet-0445e2e018a4afd0e", # us-east-1d
    "subnet-0feb086fc62f3dabb"  # us-east-1a
  ]
}

resource "aws_lb_target_group" "app" {
  name        = "${var.project}-tg-${var.env}"
  port        = var.container_port
  protocol    = "HTTP"
  vpc_id      = data.aws_vpc.rds_vpc.id
  target_type = "ip"

  health_check {
    path                = "/actuator/health"  # ✅ tu app responde aquí
    interval            = 60                  # más tiempo entre checks
    timeout             = 15                  # da chance a responder
    healthy_threshold   = 5                   # necesita 5 respuestas OK
    unhealthy_threshold = 5                   # no lo marque unhealthy tan rápido
    matcher             = "200"
  }
}

# Listener para ALB
resource "aws_lb_listener" "app" {
  load_balancer_arn = aws_lb.app.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.app.arn
  }
}

# ECS Service con Load Balancer
resource "aws_ecs_service" "app" {
  name            = "${var.project}-svc-${var.env}"
  cluster         = aws_ecs_cluster.this.id
  task_definition = aws_ecs_task_definition.app.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    # ✅ Fuerzo las mismas subnets públicas que usa el ALB
    subnets = [
      "subnet-09c1b6c9ae2bdfa08",
      "subnet-07f4efeeaea60d24b",
      "subnet-0b406eb1a7e1f9e69",
      "subnet-0f3ad9a9a57403a31",
      "subnet-0445e2e018a4afd0e",
      "subnet-0feb086fc62f3dabb"
    ]
    security_groups  = [aws_security_group.ecs_sg.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.app.arn
    container_name   = "app"                  # 👈 debe coincidir con el task
    container_port   = var.container_port
  }

  depends_on = [aws_lb_listener.app]
}
