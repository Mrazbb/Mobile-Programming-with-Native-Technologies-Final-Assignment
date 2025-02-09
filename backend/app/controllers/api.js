exports.install = function() {
	ROUTE('+GET    /api/quote/          --> Quotes/list');
	ROUTE('+GET    /api/ratings/        --> Ratings/list');
	ROUTE('+POST   /api/ratings/        --> Ratings/save');
};


NEWACTION('Quotes/list', {
    name: 'List Quotes',
	action: async function ($, model) {
		console.log('quotes list');
		let quotes = await DATA.find('public.view_quote').promise();
		$.callback(quotes);
	}
});

NEWACTION('Ratings/list', {
	name: 'List Reviews',
	query: '*userid: String',
	action: async function ($, model) {
		console.log('ratings list');
		// santitzed input
		let userid = $.query.userid.replace(/[^0-9a-fA-F]/g, '');

		let ratings = await DATA.find('public.tbl_rating').where('userid', userid).promise();
	
		$.callback(ratings);
	}
});


NEWACTION('Ratings/save', {
	name: 'Save Review',
	input: 'userid: String, quoteid: Number, rating: Number',
	action: async function ($, model) {
		console.log('ratings save');

		// santitzed input
		let userid = model.userid.replace(/[^0-9a-fA-F]/g, '');

		let rating = await DATA.modify('public.tbl_rating', {
			rating: model.rating
		}, true).insert(function (doc) {
			doc.userid = userid;
			doc.quoteid = model.quoteid;
		}).where('userid', userid).where('quoteid', model.quoteid).promise();

		$.success();
		return;
	}
});


