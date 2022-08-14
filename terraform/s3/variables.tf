variable "aws_region" {
  description = "The AWS region to use to create resources."
  default     = "us-east-1"
}

variable "bucket_prefix" {
  type        = string
  description = "(required since we are not using 'bucket') Creates a unique bucket name beginning with the specified prefix. Conflicts with bucket."
  default     = "seetaram-tf-"
}

variable "surya_bucket_prefix" {
  type        = string
  description = "(required since we are not using 'bucket') Creates a unique bucket name beginning with the specified prefix. Conflicts with bucket."
  default     = "surya-tf-"
}

variable "tags" {
  type        = map
  description = "(Optional) A mapping of tags to assign to the bucket."
  default     = {
    environment = "DEV"
    terraform   = "true"
    name = "test bucket"
  }
}

