import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

String nombreCompleto      = "josepher duarte"
String correoElectronico   = "jsduarte@test.com"
String direccionActual     = "Calle 123 # 22-22, Bogotá, Colombia"
String direccionPermanente = "Avenida 45 #33-33, Medellín, Colombia"

try {

	KeywordUtil.logInfo("PASO 1: Abriendo navegador")
	WebUI.openBrowser('')
	WebUI.maximizeWindow()
	WebUI.navigateToUrl('https://demoqa.com/text-box')
	WebUI.waitForPageLoad(10)

	KeywordUtil.logInfo("PASO 2: Llenando Full Name")
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TextBox/txt_FullName'), 10)
	WebUI.setText(findTestObject('Object Repository/Page_TextBox/txt_FullName'), nombreCompleto)

	KeywordUtil.logInfo("PASO 3: Llenando Email")
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TextBox/txt_Email'), 10)
	WebUI.setText(findTestObject('Object Repository/Page_TextBox/txt_Email'), correoElectronico)

	KeywordUtil.logInfo("PASO 4: Llenando Current Address")
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TextBox/txt_CurrentAddress'), 10)
	WebUI.setText(findTestObject('Object Repository/Page_TextBox/txt_CurrentAddress'), direccionActual)

	KeywordUtil.logInfo("PASO 5: Llenando Permanent Address")
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TextBox/txt_PermanentAddress'), 10)
	WebUI.setText(findTestObject('Object Repository/Page_TextBox/txt_PermanentAddress'), direccionPermanente)

	KeywordUtil.logInfo("PASO 6: Clic en Submit")
	WebUI.scrollToElement(findTestObject('Object Repository/Page_TextBox/btn_Submit'), 5)
	WebUI.waitForElementClickable(findTestObject('Object Repository/Page_TextBox/btn_Submit'), 10)
	WebUI.click(findTestObject('Object Repository/Page_TextBox/btn_Submit'))

	KeywordUtil.logInfo("PASO 7: Esperando output")
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TextBox/div_Output'), 10)

	// DEBUG: imprimir el texto exacto del output
	String textoOutput = WebUI.getText(findTestObject('Object Repository/Page_TextBox/div_Output'))
	KeywordUtil.logInfo("TEXTO DEL OUTPUT: " + textoOutput)

	KeywordUtil.markPassed("TC_TextBox_HappyPath - PASSED")

} catch (Exception e) {
	KeywordUtil.logInfo("ERROR: ${e.getMessage()}")
	WebUI.takeScreenshot()
	KeywordUtil.markFailed("TC_TextBox_HappyPath FAILED: ${e.getMessage()}")

} finally {
	WebUI.closeBrowser()
}