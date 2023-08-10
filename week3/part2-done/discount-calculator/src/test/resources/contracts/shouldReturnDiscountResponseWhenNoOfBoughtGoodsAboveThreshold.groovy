import org.springframework.cloud.contract.spec.Contract

Contract.make {
	request {
		url("/discount")
		method(POST())
		headers {
			contentType(applicationJson())
		}
		body([
				name               : "john",
				numberOfBoughtGoods: 5,
				occupation         : "EMPLOYED"
		])
	}
	response {
		status(OK())
		headers {
			contentType(applicationJson())
		}
		body([
				personName: "john",
				discountRate: 10
		])
	}

}
