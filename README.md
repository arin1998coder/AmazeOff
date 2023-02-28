# AmazeOff

A simple backend Project built obeying the Spring MVC architecture where i practiced some basic API's like PUT,GET,POST and DELETE and Some Annotations like @Controller , @RestController , @GetMapping , @RequestMapping , @ResponseBody and etc,.

Overview : Replicating the process of Order to Delivery of an Online ordered product through a site like Amazon. There is an Order Class and Order Map which stores all the orders requested by the customer and there is a Delivery Partner Class and a Delivery Partner map which stores all the details of Delivery Partner. 

One delivery Partner can be assigned multiple orders as List of orders which that delivery partner will deliver.

Each of the order has an Id , and Delivery Time associated with it.

Each of the delivery partner has an Id and count of num of orders assigned to it .

We have a HashMap of Delivery Partner to List Of Orders to track all the orders mapped to a Delivery partner.

We have another hashmap of OrderToDelivery map in which each orderid is mapped to its respective delivery partner id.

Features:

1.User orders a product, that product is added to the Order Map which acts as a DB to store all the orders. (Post API)
2.New Delivery person is added so that is added to the Delivery Partner Map.(Post API)
3.Orders can be mapped to respective delivery partners. (Put API)
4.User can check when was the last order delivered by the delivery partner in Time HH:MM format(GET API)
5.User can check all the orders assigned to a delivery partner(GET API)
6.User can find out all the orders which are not yet delivered by the Delivery Partner ( GET API)
7.User can delete an order, so that order record is deleted from orders map and also from the list of orders assigned to the delivery partner. (DELETE API)
8.User can delete a delivery partner , so all the orders assigned to that delivery partner is unassigned . (DELETE API)
