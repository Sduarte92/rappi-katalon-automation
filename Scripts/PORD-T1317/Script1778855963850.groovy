import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

// ─────────────────────────────────────────────
// 1. CONFIGURACIÓN TOTALMENTE BLINDADA (Sin toInteger)
// ─────────────────────────────────────────────
// Asignamos valores por defecto seguros. Si falla el perfil, usa estos.
String appId       = 'com.grability.rappi'
String estadoOrden = 'el restaurante esta preparando tu orden'
int    timeout     = 30

try {
	if (GlobalVariable.appId != null) appId = GlobalVariable.appId.toString()
	if (GlobalVariable.estadoOrden != null) estadoOrden = GlobalVariable.estadoOrden.toString()
} catch (Exception e) {
	// Silenciamos el error del perfil para que la prueba continúe
}

// ─────────────────────────────────────────────
// 2. PARSEO DEL JSON
// ─────────────────────────────────────────────
String rawJson = '''
{
  "footer": [{
    "key": "on_top_footer_v2",
    "data": {
      "images": [
        { "url": "https://images.rappi.com/products/f45bd79a-1994-40e0-a7f6-192c0e42294d.png"  },
        { "url": "https://images.rappi.com/products/7bbeb970-ceac-4509-9d6a-cbb182d320f7.png"  },
        { "url": "https://images.rappi.com/products/092b1ef9-6003-4d6b-8dcb-e181e6b5da10.png"  },
        { "url": "https://images.rappi.com/products/4aad2e11-da9e-4470-a115-61fb5a072499.jpg"  },
        { "url": "https://images.rappi.com/products/21a1032b-32a8-40d3-8d19-546ac937ab8b.jpeg" },
        { "url": "https://images.rappi.com/products/e300de8b-de1d-4b7a-b5a2-0f681d1162ec.png"  },
        { "url": "https://images.rappi.com/products/ed4aa7a0-ff90-41fb-93a6-9f560caa3e7f.jpg"  },
        { "url": "https://images.rappi.com/products/01c321f2-56a5-4a77-91e8-5d1dc68b4061.png"  },
        { "url": "https://images.rappi.com/products/e9acc6c3-e623-43a6-8219-da6fb224322b.png"  },
        { "url": "https://images.rappi.com/products/5a315f56-fa25-4a33-bdf7-134c49f52a93.png"  }
      ]
    }
  }]
}
'''

def jsonSlurper  = new JsonSlurper()
def jsonData     = jsonSlurper.parseText(rawJson)
def imagenesEsperadas = jsonData.footer[0].data.images
int totalImagenes     = imagenesEsperadas.size()

KeywordUtil.logInfo("Total de imágenes esperadas en el carrusel: ${totalImagenes}")

// ─────────────────────────────────────────────
// 3. APERTURA DE APP Y NAVEGACIÓN
// ─────────────────────────────────────────────
Mobile.startExistingApplication(appId, FailureHandling.STOP_ON_FAILURE)
Mobile.delay(3)

TestObject cardOrden = findTestObject('Object Repository/PORD/OT/home_card_orden_estado', [('estadoOrden'): estadoOrden])
Mobile.waitForElementPresent(cardOrden, timeout, FailureHandling.STOP_ON_FAILURE)
Mobile.tap(cardOrden, timeout)
KeywordUtil.logInfo("Navegando al detalle de la orden...")

Mobile.delay(4)

// ─────────────────────────────────────────────
// 4. VALIDAR FOOTER FIJO (Sin Scroll)
// ─────────────────────────────────────────────
boolean footerVisible = Mobile.waitForElementPresent(
	findTestObject('Object Repository/PORD/OT/detalle_footer_imagen_carrusel'),
	timeout,
	FailureHandling.OPTIONAL
)

if (!footerVisible) {
	KeywordUtil.markFailedAndStop("[PORD-T1317] FALLO: El footer fijo NO fue encontrado.")
}
KeywordUtil.logInfo("[PORD-T1317] PASS: Footer fijo presente.")

// ─────────────────────────────────────────────
// 5. VALIDAR ROTACIÓN AUTOMÁTICA
// ─────────────────────────────────────────────
int    delayRotacion   = 2
int    imagenesValidas = 0
List   fallos          = []

for (int i = 0; i < totalImagenes; i++) {

	String urlEsperada = imagenesEsperadas[i].url
	String nombreImg   = urlEsperada.tokenize('/').last()

	KeywordUtil.logInfo("Esperando auto-rotación de imagen ${i + 1}/${totalImagenes}: ${nombreImg}")

	def objImagen = findTestObject('Object Repository/PORD/OT/detalle_footer_imagen_carrusel', [('imageUrl'): urlEsperada])

	boolean imagenPresente = Mobile.waitForElementPresent(objImagen, timeout, FailureHandling.OPTIONAL)

	if (imagenPresente) {
		imagenesValidas++
		KeywordUtil.logInfo("  ✔ Imagen ${i + 1} validada: ${nombreImg}")
	} else {
		String msg = "  ✘ Imagen ${i + 1} NO encontrada en la rotación: ${urlEsperada}"
		fallos.add(msg)
		KeywordUtil.logInfo(msg)
	}

	if (i < totalImagenes - 1) {
		Mobile.delay(delayRotacion)
	}
}

// ─────────────────────────────────────────────
// 6. RESULTADO FINAL
// ─────────────────────────────────────────────
if (fallos.size() > 0) {
	fallos.each { KeywordUtil.logInfo(it) }
	KeywordUtil.markFailedAndStop("[PORD-T1317] FALLO: ${fallos.size()} de ${totalImagenes} imágenes fallaron.")
} else {
	KeywordUtil.markPassed("[PORD-T1317] PASS: Las ${totalImagenes} imágenes del carrusel rotaron automáticamente y fueron validadas.")
}