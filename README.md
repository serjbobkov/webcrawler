# Overview
It's a simple web crawler in a Java language. 
Crawler limited to a one domain name, it visits all pages within the domain, but doen't follow the links to external sites such as Google or Twitter.

The output is a structured xml document.

For parsing pages application uses JSoup library https://jsoup.org/ .

For generating xml it uses Jackson FasterXML library https://github.com/FasterXML/jackson .

# How to run

It uses Maven for build project.

For running application you need to have installed Java 11 and Maven.

Command line:  

_mvn clean compile exec:java -Dexec.mainClass="com.bobkov.App" -Dexec.args="DOMAIN_NAME OUTPUT_PATH DEPTH"_

Command line arguments:
* DOMAIN_NAME - url to crawl
* OUTPUT_PATH - folder where will be generated output xml with result
* DEPTH - the max depth of crawl

example of usage:

_mvn clean compile exec:java -Dexec.mainClass="com.bobkov.App" -Dexec.args="http://www.homecredit.ru /tmp 2"_


# Future improvements

* use http client (apache http client, ok http client, etc) instead of jsoup url connection. It's more configurable
* use configurable parser (for example on SAX) instead of JSoup, because it uses DOM model
* add possibility to configure User-Agent, additional headers, using cookies
* add possibility to have different configurable output transformers (txt, json, xml, etc)
* add configuration for crawl speed, make some imitation of real user
* add possibility to use scripts for authentification
* add possibility to use additional ssl certificates
* add tests


