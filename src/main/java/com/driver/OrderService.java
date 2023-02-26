package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository repository;

    //decrement the count of num of orders for a parter by 1
    public void decrementNumOfOrdersCountOfAPartner(String partnerId){

        int numOfOrders=repository.partnerMap.get(partnerId).getNumberOfOrders() - 1;
        repository.partnerMap.get(partnerId).setNumberOfOrders(numOfOrders);

    }

    //update the num of orders count of a partner
    public void updateOrdersCountOfPartner(String partnerId){
        int numOfOrders = repository.partnerMap.get(partnerId).getNumberOfOrders() + 1;
        repository.partnerMap.get(partnerId).setNumberOfOrders(numOfOrders);
    }


    //1
    public void add_Order(Order order){
        repository.addOrder(order);
    }
    //2
    public void add_Partner(String partnerId){
        repository.addPartner(partnerId);
    }

    //3
    public void add_Partner_Order_Pair(String orderId,String partnerId){
        //if partner and order exists in their respective map's then only add them to partnerOrderPair map
        if(repository.partnerMap.containsKey(partnerId) && repository.orderMap.containsKey(orderId)) {
            repository.assignOrderToAPartner(orderId, partnerId);
            //updating the num of orders a partner
            updateOrdersCountOfPartner(partnerId);
        }
    }

    //4
    public Order getOrderById(String orderId){
        if(repository.orderMap.containsKey(orderId))
            return repository.getOrder(orderId);
        return null;
    }
    //5
    public DeliveryPartner getPartnerById(String partnerId){
        if(repository.partnerMap.containsKey(partnerId)) {
            return repository.getPartner(partnerId);
        }
        else
            return null;
    }

    //6
    public int get_Order_Count_Of_A_Partner(String partnerId){
        return repository.getOrderCountByPartnerId(partnerId);
    }

    //7
    public List<String> get_Orders_By_Partner_Id(String partnerId){
        return repository.getOrdersByPartnerId(partnerId);
    }

    //8
    public List<String> get_All_Orders(){
        return repository.getAllOrders();
    }

    //9
    public int get_Count_Of_Unassigned_Orders(){
        return repository.getCountOfUnassignedOrders();
    }

    //10
    public int get_Count_Of_Orders_Undelivered_After_Given_Time(String time,String partnerId){
        //not taking care of the case if string's format passed by customer is valid or invalid like its in H,MM or HH.MM or HH

        return repository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    //11
    public String get_Last_Delivery_Time_Of_A_Partner(String partnerId){
        return repository.getLastDeliveryTimeOfAPartner(partnerId);
    }

    //12
    public void delete_Partner_By_Id(String partnerId){
         repository.deletePartner(partnerId);
    }

    //13
    public void delete_Order_By_Id(String orderId){
        String partnerId=repository.orderToPartnerMap.get(orderId); //get the parterId to which the order is assigned
        decrementNumOfOrdersCountOfAPartner(partnerId);
        repository.deleteOrderById(orderId);
    }

    public List<Order> getListOfOrdersAssignedToAPartnerId(String partnerId){
        return repository.getListOfOrdersAssignedToAPartner(partnerId);
    }

}
