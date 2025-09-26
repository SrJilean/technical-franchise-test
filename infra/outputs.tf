output "alb_dns_name" {
  description = "DNS público del Application Load Balancer"
  value       = aws_lb.app.dns_name
}

output "ecs_service_name" {
  value = aws_ecs_service.app.name
}

output "ecs_cluster_name" {
  value = aws_ecs_cluster.this.name
}
