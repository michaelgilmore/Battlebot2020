import scrapy

class BlogSpider(scrapy.Spider):
    name = 'blogspider'
    start_urls = ['http://search.medscape.com/search/?q=public%20health&plr=edu']
	
    def parse(self, response):
        print "parse()"
        for url in response.css('ul li a::attr("href")').re(r'.*/\d\d\d\d/\d\d/$'):
            yield scrapy.Request(response.urljoin(url), self.parse_titles)
		
    def parse_titles(self, response):
        print "parse_titles()"
        for post_title in response.css('div.entries > ul > li a::text').extract():
            yield {'title': post_title}
		