resource "aws_cloudwatch_log_group" "app" {
  name              = "/ecs/${var.project}-${var.env}"
  retention_in_days = 7
}