import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil

Mobile.startExistingApplication('com.grability.rappi', FailureHandling.STOP_ON_FAILURE)
Mobile.delay(5)

// El llamado oficial mediante CustomKeywords
CustomKeywords.'com.rappi.automation.LoginRappi.iniciarSesionEmail'('autoscroll2@test.test', '000000')