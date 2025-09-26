resource "aws_ecr_repository" "app" {
  name = "${var.project}-${var.env}"
  image_scanning_configuration { scan_on_push = true }
  tags = { Project = var.project, Env = var.env }
}

output "ecr_repo_url" {
  value = aws_ecr_repository.app.repository_url
}
