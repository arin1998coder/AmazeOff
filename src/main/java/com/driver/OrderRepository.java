package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

     Map<String,Order> orderMap = new HashMap<>();
     Map<String,DeliveryPartner> partnerMap = new HashMap<>();
     Map<String, List<String>> partnerOrderPair = new HashMap<>();

    Map<String,String> orderToPartnerMap = new HashMap<>();

    //add order
    public void addOrder(Order order){
        String orderId=order.getId();
        orderMap.put(orderId,order);
    }
    //add a partner
    public void addPartner(String partnerId){
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,partner);
    }

    //assign an order to a partner
    public void assignOrderToAPartner(String orderId,String partnerId){
        if(partnerOrderPair.containsKey(partnerId)) {
            partnerOrderPair.get(partnerId).add(orderId);
        }
        else{
            partnerOrderPair.put(partnerId,new ArrayList<>());
            partnerOrderPair.get(partnerId).add(orderId);
        }
        mapOrderToPartner(orderId,partnerId);

    }
    //map partner id to a order id
    public void mapOrderToPartner(String orderId,String partnerId){
        orderToPartnerMap.put(orderId,partnerId);
    }

    //get order by order id
    public Order getOrder(String orderId){
        return orderMap.get(orderId);
    }

    //get partner by partner id
    public DeliveryPartner getPartner(String partnerId){
        return partnerMap.get(partnerId);
    }
    //get num of orders linked to a partner
    public int getOrderCountByPartnerId(String partnerId){
        int count=partnerOrderPair.get(partnerId).size();
        return count;
    }
    //get list of orders of a partner
    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> orders = partnerOrderPair.get(partnerId);
        return orders;
    }
    //get all orders in system as list
    public List<String> getAllOrders(){
        List<String> allOrders = new ArrayList<>();
        for(String order:orderMap.keySet()){
            allOrders.add(order);
        }
        return allOrders;
    }

    //count of unassigned orders to any partner
    public int getCountOfUnassignedOrders(){
        int totalOrders=orderMap.size();
        int assignedOrders=orderToPartnerMap.size();
        return totalOrders - assignedOrders;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){

        int count=0;
        //deliveryTime  = HH*60 + MM
        int givenTime=Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3));
        for(String order:partnerOrderPair.get(partnerId)){
            if(orderMap.get(order).getDeliveryTime()>givenTime)
                count++;
        }
        return count;
    }

    public String getLastDeliveryTimeOfAPartner(String partnerId){
        int lastdeliverytime=0;
        for(String order:partnerOrderPair.get(partnerId)){
            if(orderMap.get(order).getDeliveryTime()>lastdeliverytime){
                lastdeliverytime=orderMap.get(order).getDeliveryTime();
            }
        }
        String HH="";
        String MM="";
        int hh=(lastdeliverytime/60);
        int mm=(lastdeliverytime%60);

        if(hh<10){
            HH="0"+hh;
        }
        else
            HH=""+hh;
        if(mm<10){
            MM="0"+mm;
        }
        else
            MM=""+mm;

        return HH+":"+MM;
    }

    public void deletePartner(String partnerId){
        List<String> orderIds=getOrdersByPartnerId(partnerId);
        //unassigns all the orders linked to this partnerId
        for(String order:orderIds){
            orderToPartnerMap.remove(order);
        }
        partnerOrderPair.remove(partnerId);
        partnerMap.remove(partnerId);
    }
    public void deleteOrderById(String orderId){
        String partnerId=orderToPartnerMap.get(orderId); //get the parterId to which the order is assigned
        orderMap.remove(orderId); //remove the order from orderMap
        orderToPartnerMap.remove(orderId); //remove the order: partner mapping from orderToPartnerMap
        partnerOrderPair.get(partnerId).remove(orderId); //remove the order from the partner's list of assigned orders
    }
    //gives the List of order as order objects assigned to a ParterId
    public List<Order> getListOfOrdersAssignedToAPartner(String partnerId){
        List<String> orderIds=getOrdersByPartnerId(partnerId);
        List<Order> orderObjs=new ArrayList<>();
        for(String orderId:orderIds){
            orderObjs.add(getOrder(orderId));
        }
        return orderObjs;
    }

}
