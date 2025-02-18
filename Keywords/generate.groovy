import java.text.SimpleDateFormat

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.util.CryptoUtil

public class generate {

	/**
	 * Generate encryptedText
	 * @param encryptedText the text you want to decrypt
	 */

	@Keyword
	def decryptedText(String encryptedText) {

		CryptoUtil.decode(CryptoUtil.getDefault(encryptedText))
	}
	
	/**
	 * Generate encryptedText
	 * @param date the text you want to convert to unix time
	 */

	@Keyword
	def unixTime(String date) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		
		long unixTimestamp = dateFormat.parse(date).getTime() / 1000
	}
}
