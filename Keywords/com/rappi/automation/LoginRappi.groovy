package com.rappi.automation

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent

class LoginRappi {

	@Keyword
	def iniciarSesionEmail(String email, String otp) {
		KeywordUtil.logInfo("=== INICIANDO LOGIN ===")

		// PASO 1: Other sign in methods
		Mobile.tapAtPosition(360, 1460, FailureHandling.STOP_ON_FAILURE)
		Mobile.delay(3)
		
		// PASO 2: I already have an account
		Mobile.tapAtPosition(360, 1450, FailureHandling.STOP_ON_FAILURE)
		Mobile.delay(3)

		// PASO 3: Continue with your email
		Mobile.tapAtPosition(360, 1250, FailureHandling.STOP_ON_FAILURE)
		Mobile.delay(3)

		// PASO 4: Llenar Email
		KeywordUtil.logInfo("PASO 4: Llenando campo Email")
		TestObject inputEmail = new TestObject()
		inputEmail.addProperty("xpath", ConditionType.EQUALS, "//android.widget.AutoCompleteTextView", true)
		Mobile.waitForElementPresent(inputEmail, 10, FailureHandling.STOP_ON_FAILURE)
		Mobile.tap(inputEmail, 5, FailureHandling.STOP_ON_FAILURE)
		Mobile.delay(1)
		Mobile.clearText(inputEmail, 5, FailureHandling.OPTIONAL)
		Mobile.setText(inputEmail, email, 10, FailureHandling.STOP_ON_FAILURE)
		Mobile.delay(2)
		Mobile.hideKeyboard(FailureHandling.OPTIONAL)
		Mobile.delay(2)

		// PASO 5: Clic en la flecha verde (Siguiente)
		Mobile.tapAtPosition(641, 1442, FailureHandling.STOP_ON_FAILURE)
		Mobile.delay(4)

		// PASO 6: Digitar el número 0 exactamente 6 veces
		KeywordUtil.logInfo("PASO 6: Digitando el número 0 seis veces...")
		AndroidDriver driver = MobileDriverFactory.getDriver()
		for (int i = 1; i <= 6; i++) {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_0))
			Mobile.delay(1)
		}
		
		KeywordUtil.logInfo("Esperando pantalla de permisos...")
		Mobile.delay(5)

		// PASO 7: Clic en 'Don't allow' (Coordenada original que sí lo presionaba)
		KeywordUtil.logInfo("PASO 7: Presionando Don't allow...")
		Mobile.tapAtPosition(360, 1450, FailureHandling.STOP_ON_FAILURE)
		Mobile.delay(6)

		// PASO 8: Validación de seguridad de la Pantalla de Inicio
		KeywordUtil.logInfo("PASO 8: Validando llegada a la pantalla de inicio...")
		TestObject homeIndicador = new TestObject()
		homeIndicador.addProperty("xpath", ConditionType.EQUALS, "//*[contains(@text, 'Turbo') or contains(@text, 'Restaurantes') or contains(@text, 'Rappi')]", true)
		boolean estaEnHome = Mobile.waitForElementPresent(homeIndicador, 20, FailureHandling.OPTIONAL)
		
		if (estaEnHome) {
			KeywordUtil.markPassed("¡LOGIN COMPLETADO CON ÉXITO!")
			
			// PASO 9: Un cuarto de scroll invertido (De arriba hacia abajo para mostrar la Card del Home)
			KeywordUtil.logInfo("PASO 9: Ejecutando el ajuste de pantalla hacia abajo...")
			Mobile.swipe(360, 800, 360, 1150)
			Mobile.delay(3)
			
			KeywordUtil.logInfo("Pantalla ajustada con éxito.")
		} else {
			KeywordUtil.markFailed("❌ El proceso terminó pero no se detectó la pantalla de inicio.")
		}
	}
}