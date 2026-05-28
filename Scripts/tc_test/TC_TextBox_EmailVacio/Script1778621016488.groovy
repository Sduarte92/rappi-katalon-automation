import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

String nombreCompleto      = "Josepher duarte dd"
String direccionActual     = "Calle 123# 44-44, Bogotá, Colombia"
String direccionPermanente = "Avenida 45# 666, Medellín, Colombia"

try {

	KeywordUtil.logInfo("PASO 1: Abriendo navegador")
	WebUI.openBrowser('')
	WebUI.maximizeWindow()
	WebUI.navigateToUrl('https://demoqa.com/text-box')
	WebUI.waitForPageLoad(10)

	KeywordUtil.logInfo("PASO 2: Llenando formulario sin email")
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TextBox/txt_FullName'), 10)
	WebUI.setText(findTestObject('Object Repository/Page_TextBox/txt_FullName'), nombreCompleto)
	WebUI.setText(findTestObject('Object Repository/Page_TextBox/txt_CurrentAddress'), direccionActual)
	WebUI.setText(findTestObject('Object Repository/Page_TextBox/txt_PermanentAddress'), direccionPermanente)

	KeywordUtil.logInfo("PASO 3: Clic en Submit")
	WebUI.scrollToElement(findTestObject('Object Repository/Page_TextBox/btn_Submit'), 5)
	WebUI.click(findTestObject('Object Repository/Page_TextBox/btn_Submit'))

	KeywordUtil.logInfo("PASO 4: Verificando que el output NO contiene email")
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TextBox/div_Output'), 5)
	String textoOutput = WebUI.getText(findTestObject('Object Repository/Page_TextBox/div_Output'))

	assert !textoOutput.contains('@') : "FAILED: El output contiene un email cuando debería estar vacío"
	KeywordUtil.logInfo("✔ Correcto: el output no contiene email")
	KeywordUtil.markPassed("TC_TextBox_EmailVacio - PASSED")

} catch (Exception e) {
	KeywordUtil.logInfo("ERROR: ${e.getMessage()}")
	WebUI.takeScreenshot()
	KeywordUtil.markFailed("TC_TextBox_EmailVacio FAILED: ${e.getMessage()}")

} finally {
	WebUI.closeBrowser()
}