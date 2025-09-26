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
      name      = "app",
      image     = "${aws_ecr_repository.app.repository_url}:latest",
      essential = true,
      portMappings = [{
        containerPort = var.container_port,
        hostPort      = var.container_port,
        protocol      = "tcp"
      }],
      environment = [
        { name = "SPRING_R2DBC_URL",      value = "r2dbc:mysql://<RDS-ENDPOINT>:3306/technical_test" },
        { name = "SPRING_R2DBC_USERNAME", value = "admin" },
        { name = "SPRING_R2DBC_PASSWORD", value = "19012030Abc" }
      ],
      logConfiguration = {
        logDriver = "awslogs",
        options = {
          awslogs-group         = aws_cloudwatch_log_group.app.name,
          awslogs-region        = "us-east-1",
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])
}

resource "aws_ecs_service" "app" {
  name            = "${var.project}-svc-${var.env}"
  cluster         = aws_ecs_cluster.this.id
  task_definition = aws_ecs_task_definition.app.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets          = data.aws_subnets.rds_subnets.ids
    security_groups  = [aws_security_group.ecs_sg.id]
    assign_public_ip = true
  }
}
