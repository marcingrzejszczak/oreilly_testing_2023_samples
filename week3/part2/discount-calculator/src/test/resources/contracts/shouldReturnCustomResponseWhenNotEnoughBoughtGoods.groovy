/* TODO: Fix me - "error" scenario

Request
 url `/discount`
 method `POST`
 header `Content-Type` : `application/json`
 body `{ "name" : "mary", "numberOfBoughtGoods": 0, "occupation" : "EMPLOYED" }`
Response
 status `400`
 header `Content-Type` : `application/json`
 body `{ "person" : { "name" : "mary", "numberOfBoughtGoods": 0, "occupation" : "EMPLOYED" }, "additionalMessage": "We can't apply discounts to people who didn't buy any goods" }`

 */
