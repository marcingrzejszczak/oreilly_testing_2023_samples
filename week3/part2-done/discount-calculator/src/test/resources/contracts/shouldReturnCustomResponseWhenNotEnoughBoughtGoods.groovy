import org.springframework.cloud.contract.spec.Contract

Contract.make {
	request {
		url("/discount")
		method(POST())
		headers {
			contentType(applicationJson())
		}
		body([
				name               : "mary",
				numberOfBoughtGoods: 0,
				occupation         : "EMPLOYED"
		])
	}
	response {
		status(BAD_REQUEST())
		headers {
			contentType(applicationJson())
		}
		body([
				person: [
						name               : "mary",
						numberOfBoughtGoods: 0,
						occupation         : "EMPLOYED"
				],
				additionalMessage: "We can't apply discounts to people who didn't buy any goods"
		])
	}

}
