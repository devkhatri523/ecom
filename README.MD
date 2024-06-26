# spring moot microservice project
Ecommerce application to place order and send order confirmation and payment confirmation 
# How to run application locally
## Prerequisites
1. maven
2. java(`17`)

# project components
1) config server - central place to add configuration of service ( service name should match with the yml )
2) Discovery service - to register the service
3) Notification service - to publish notification - used kafka
4) Order service - to place order
5) Payment service - to process payment
6) Customer service - to create customer 
7) Product service - to fetch the product list
8) Notification service - to send email or order confirmation and payment confirmation
9) Gateway service - to route the api end point
# how to run this project
1) run command docker compose up
2) Run config sever
3) Run Discovery service
4) Run other services as per need

# inter service communication
For inter service communication feign client and rest template is used
