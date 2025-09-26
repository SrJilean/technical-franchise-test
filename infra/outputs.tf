output "alb_dns_name" {
  description = "DNS público del Application Load Balancer"
  value       = aws_lb.app.dns_name
}
