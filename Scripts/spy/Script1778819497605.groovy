import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling
import internal.GlobalVariable as GlobalVariable

// 1. ABRIR APP Y ENTRAR A LA ORDEN (Nuestra base indestructible)
Mobile.startExistingApplication(GlobalVariable.appId, FailureHandling.STOP_ON_FAILURE)
Mobile.delay(3)

Mobile.tap(findTestObject('Object Repository/PORD/OT/home_card_orden_estado', [('estadoOrden') : GlobalVariable.estadoOrden]), 30)
Mobile.delay(5) // Esperamos a que cargue el mapa y el detalle

// 2. EL EXPLORADOR NINJA
KeywordUtil.logInfo("Buscando la imagen del carrusel sin usar el Spy...")

try {
	// Usamos el objeto manual que acabas de crear
	def imagen = findTestObject('Object Repository/PORD/OT/detalle_footer_imagen_carrusel')
	
	// Verificamos si Katalon la ve
	if (Mobile.verifyElementPresent(imagen, 5, FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed("¡BINGO! Encontramos la imagen del carrusel.")
		
		// Vamos a extraer los 3 atributos más comunes y a imprimirlos
		String desc = Mobile.getAttribute(imagen, 'content-desc', 2, FailureHandling.OPTIONAL)
		String texto = Mobile.getAttribute(imagen, 'text', 2, FailureHandling.OPTIONAL)
		String id = Mobile.getAttribute(imagen, 'resource-id', 2, FailureHandling.OPTIONAL)
		
		KeywordUtil.logInfo("👉 CONTENT-DESC: " + desc)
		KeywordUtil.logInfo("👉 TEXT: " + texto)
		KeywordUtil.logInfo("👉 RESOURCE-ID: " + id)
		
	} else {
		KeywordUtil.markFailed("No se encontró la imagen con el XPath universal.")
	}
} catch (Exception e) {
	KeywordUtil.logInfo("Error explorando: " + e.getMessage())
}