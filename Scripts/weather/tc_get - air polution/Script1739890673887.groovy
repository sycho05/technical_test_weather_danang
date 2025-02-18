import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import groovy.json.JsonSlurper
import internal.GlobalVariable

/** Author		: Danang Wicaksana
 * Created Date	: 18/02/2025
 * Updated by	: -
 * Updated Date	: -
 * Summary		: get air polution
 * */

'Load internal data file weather - coordinate city from row 1 '
def internalDataFile = findTestData('Data Files/weather_data/coordinate_city data set')

String lat= internalDataFile.getValue('lat', 1)
String lon= internalDataFile.getValue('lon', 1)
String city= internalDataFile.getValue('city', 1)

'Load internal data file date dataset from row 1'
def internalDataFileDate = findTestData('Data Files/weather_data/date dataset')

String date= internalDataFileDate.getValue('date', 1)

'initial response'
def response = WS.sendRequest(findTestObject('Object Repository/api_weather/get - air polution',[
	('lat'):lat,
	('lon'):lon,
	('date'):CustomKeywords.'generate.unixTime'(date),
	('apiKey'):CustomKeywords.'generate.decryptedText'(GlobalVariable.apiKeyEncrypted)]))

'Parse response to JSON'
def jsonResponse = new JsonSlurper().parseText(response.getResponseText())

'verify response status code'
WS.verifyResponseStatusCode(response, 200)

'verify the lat, lon same as data test and the aqi index not null'
WS.verifyEqual(jsonResponse.coord.lat, lat)
WS.verifyEqual(jsonResponse.coord.lon, lon)
WS.verifyNotEqual(jsonResponse.list.main.aqi, '')

'to show to report'
WS.comment(jsonResponse.list.main.toString())

def aqi = jsonResponse.list.main.aqi.toString().replace('aqi:', '').replace(']', '').replace('[', '')

print(aqi)

if (aqi=='1') {
	
	KeywordUtil.markPassed('AQI is Good')
	
} else if (aqi=='2') {
	
	KeywordUtil.markPassed('AQI is Fair')
	
	
} else if (aqi=='3') {
	
	KeywordUtil.markPassed('AQI is Moderate')
	
	
} else if (aqi=='4') {
	
	KeywordUtil.markPassed('AQI is Poor')
	
	
} else if (aqi=='5') {
	
	KeywordUtil.markPassed('AQI is Very Poor')
		
} else {
	
	KeywordUtil.markErrorAndStop('Scale for AQI Not Found')
}