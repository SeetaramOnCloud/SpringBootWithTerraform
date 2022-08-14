provider "aws" {
  region = var.aws_region
}



resource "aws_s3_bucket" "surya-s3-bucket" {
  bucket_prefix = var.surya_bucket_prefix
  tags = var.tags
}