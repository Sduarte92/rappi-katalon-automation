import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable as GlobalVariable

// 1. CARGAMOS TUS VARIABLES ORIGINALES (INTACTAS)
String appId        = GlobalVariable.appId
String ambienteLog = GlobalVariable.ambiente
String textoScroll = GlobalVariable.textoScroll
int timeoutGeneral = GlobalVariable.timeoutGeneral.toInteger()
int timeoutScroll  = GlobalVariable.timeoutScroll.toInteger()
int maxScrolls     = GlobalVariable.maxScrolls.toInteger()

String emailUsuario = "autoscroll2@test.test"
String otpDummy     = "000000"

KeywordUtil.logInfo("INICIO TC: PORD-T230 | Ambiente: ${ambienteLog}")

try {
	// --- PASO 1: LEVANTAR APP Y LOGIN ---
	KeywordUtil.logInfo("[PASO 1] Iniciando app...")
	Mobile.startExistingApplication(appId, FailureHandling.STOP_ON_FAILURE)
	Mobile.delay(3)
	
	KeywordUtil.logInfo("[PASO 1] Ejecutando Keyword de Login...")
	CustomKeywords.'com.rappi.automation.LoginRappi.iniciarSesionEmail'(emailUsuario, otpDummy)
	Mobile.delay(4)

	// --- PASO 2: TAP INTELIGENTE (IGNORA EL ESTADO DE LA ORDEN) ---
	KeywordUtil.logInfo("[PASO 2] Buscando la card de la orden por sus elementos fijos...")
	
	// Creamos un objeto dinámico que busca las palabras 'delivery' o 'Estimated' que siempre están en la tarjeta
	TestObject cardOrdenInmutable = new TestObject('card_orden_activa_fija')
	cardOrdenInmutable.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@text, 'delivery') or contains(@text, 'Estimated') or contains(@text, 'Entrega')]", true)
	
	// Esperamos a que la tarjeta se pinte en el Home
	Mobile.waitForElementPresent(cardOrdenInmutable, timeoutGeneral, FailureHandling.STOP_ON_FAILURE)
	KeywordUtil.logInfo("[PASO 2] ¡Card de la orden detectada con éxito! Realizando tap.")
	Mobile.tap(cardOrdenInmutable, timeoutGeneral)

	// --- PASO 3 (TU LOGICA ORIGINAL INTACTA) ---
	KeywordUtil.logInfo("[PASO 3] Esperando pantalla de detalle...")
	Mobile.delay(3)
	Mobile.waitForElementNotPresent(cardOrdenInmutable, timeoutGeneral, FailureHandling.OPTIONAL)
	Mobile.delay(2)
	KeywordUtil.logInfo("[PASO 3] Transición completada.")

	// --- PASO 4 (SWIPE FÍSICO HASTA EL BOTÓN FINAL - INTACTO) ---
	KeywordUtil.logInfo("[PASO 4] Buscando el botón: '${textoScroll}'...")
	boolean botonVisible = false
	int scrollAttempt = 0
	
	TestObject botonFinal = new TestObject('boton_ver_tienda')
	botonFinal.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@text, '${textoScroll}') or contains(@content-desc, '${textoScroll}')]")

	int device_Height = Mobile.getDeviceHeight()
	int device_Width = Mobile.getDeviceWidth()
	int startX = device_Width / 2
	int startY = (device_Height * 0.80).toInteger()
	int endY = (device_Height * 0.20).toInteger()

	while (!botonVisible && scrollAttempt < maxScrolls) {
		scrollAttempt++
		KeywordUtil.logInfo("[PASO 4] Deslizando pantalla... Intento #${scrollAttempt}/${maxScrolls}")
		
		botonVisible = Mobile.waitForElementPresent(botonFinal, 2, FailureHandling.OPTIONAL)
		
		if (!botonVisible) {
			Mobile.swipe(startX, startY, startX, endY)
			Mobile.delay(2)
		}
	}

	if (botonVisible) {
		KeywordUtil.markPassed("PORD-T230 PASS: Se llegó hasta el botón '${textoScroll}'. OnTop validado.")
	} else {
		KeywordUtil.markFailed("PORD-T230 FAIL: No se encontró el botón '${textoScroll}' tras ${maxScrolls} deslizamientos.")
	}

} catch (Exception e) {
	KeywordUtil.logInfo("[ERROR] ${e.getMessage()}")
	KeywordUtil.markFailed("PORD-T230 ERROR: ${e.getMessage()}")
} finally {
	KeywordUtil.logInfo("FIN TC: PORD-T230")
}