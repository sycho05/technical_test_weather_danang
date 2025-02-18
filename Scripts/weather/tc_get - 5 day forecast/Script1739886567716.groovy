import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import groovy.json.JsonSlurper
import internal.GlobalVariable

'Load internal data file weather - coordinate city from row 1 '
def internalDataFile = findTestData('Data Files/weather_data/coordinate_city data set')

String lat= internalDataFile.getValue('lat', 1)
String lon= internalDataFile.getValue('lon', 1)
String city= internalDataFile.getValue('city', 1)


'initial response'
def response = WS.sendRequest(findTestObject('Object Repository/api_weather/get - 5 day forecast',[
	('lat'):lat,
	('lon'):lon,
	('apiKey'):CustomKeywords.'generate.decryptedText'(GlobalVariable.apiKeyEncrypted)]))

'Parse response to JSON'
def jsonResponse = new JsonSlurper().parseText(response.getResponseText())

'verify response status code'
WS.verifyResponseStatusCode(response, 200)

'verify the lat, lon, city same as data test'
WS.verifyEqual(jsonResponse.city.coord.lat, lat)
WS.verifyEqual(jsonResponse.city.coord.lon, lon)
WS.verifyEqual(jsonResponse.city.name, city)

'to show to report'
WS.comment(jsonResponse.city.toString())


