functional formbase auth with out csrf protecition enabled
## Fontend funtionality


- get user information excluding password
- add a new user with role included
- update user informaton
- delete user

- get a store
- add a store if user is a seller
- update store information
- delete store (maybe? this should just disable it from being found)

- get product
- add product
- update price, image, and descripition of product
- delete product

- get cart
- update cart

- get order
- add order

- get payment method ( retrun with the first 12 card numbers replaced with '*') @ready
- add payment method @ready
- update payment method (only update experation date) @ready
- delete payment method (maybe? this should just disable it from being found) 




### permissions

- PRODUCT_READ
- PRODUCT_WRITE
- CART_READ
- CART_WRITE
- STORE_READ
- STORE_WRITE
- ORDER_READ
- ORDER_WRITE
- PAYMENT_READ
- PAYMENT_WRITE
- PAYMENTMETHOD_READ
- PAYMENTMETHOD_WRITE
- USERINFO_READ
- USERINFO_WRITE
- ROLE_READ
- ROLE_WRITE
- PERMISSION_READ
- PERMISSION_WRITE


### roles and permissions asosiated with them

- Customer
    - ProductRead
    - PaymentMethodRead
    - PaymentMethodWright
    - PaymentRead
    - PaymentWright
    - OrderRead
    - OrderWrite
- Seller
- Admin

## API functions

### UserInfo
- getUserInfo(Username: String): Optional&lt;UserInfo>
    - will not retrun password or user id
- addUser(user: UserInfo): void
- updateUser(user: UserInfo): void
- deleteUser(username: String) : void

### Role
### Permission
### Cart
- get(username: String): Optional&lt;Cart>
- update(username: String, cart: Cart): void
- create(username: String, cart: Cart): void
- delete(username: String, cart: Cart): void

### Store
- get(id: Integer): Optional&lt;Store>
- getAll(id: Integer): Optional&lt;Store>
- update(username: String, store:Store): void
- create(username: String, store:Store): void
- delete(username: String, store:Store): void

### Order
orders are complete and can not be updated by anyone other than the admin
- get(username: String, id: Integer): Optional&lt;Store>
- update(username: String, store:Store): void
- create(username: String, store:Store): void
- delete(username: String, store:Store): void

### Product
- get(username: String, storeId: Integer, productId: Integer): Optional&lt;Product>
- get some amount that can all also be filted by type
- update(username: String, storeId: Integer, id: Integer): void
- create(username: String, storeId: Integer, id: Integer): void
- delete(username: String, storeId: Integer, id: Integer): void

### PaymentMethod
- get(username: String, id: Integer): Optional&lt;PaymentMethod>
- getAll(username: String): List&lt;Optional&lt;PaymentMethod>>
- update(username: String, paymentMethod:PaymentMethod): void
- create(username: String, paymentMethod:PaymentMethod): void
- delete(username: String, id: Integer): void

### Payment
- get
- update
- create
- delete