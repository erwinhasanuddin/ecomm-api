#ecomm-api
#Issue: Bad reviews during our 12.12 event.

Based on initial investigation by Customer Service and Order Processing departments, this issue has some facts:
1. Our inventory quantities are often misreported, and some items even go as far as having a negative inventory quantity.
2. The misreported items are those that performed very well in our 12.12 event.
3. Because of these misreported inventory quantities, the Order Processing department was unable to fulfill a lot of orders, and thus requested help from our Customer Service department to call our customers and notify them that we had had to cancel their orders.
 
#Analysis

Based on those facts, there are some possible ways that caused the issues:
1. The system may not check the availability of the product's stock.
2. Because there is no stock check, the higher a product's sales, the greater the amount of negative inventory that occurs in the product.

As a result, there are more customers whose orders cannot be fulfilled.

#Solution
We have to create a logic to perform product stock checks at two different events or places.
- Add a field, let's call it "TrackInventory". The default value for this field is equal to true. The purpose of this field is to prevent an out-of-stock purchase. We can set it to false when the stock level for the product is unimportant or nonexistent, for example, if the product is a service.
- Before a product is added to the cart, if the TrackInventory value is true, then the system should check the stock level of that product.
- Before a user makes an order, if the TrackInventory value is true, then the system should check the stock level of that product.