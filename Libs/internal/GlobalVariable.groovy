package internal

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseMain


/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */
public class GlobalVariable {
     
    /**
     * <p></p>
     */
    public static Object G_BASE_URL
     
    /**
     * <p></p>
     */
    public static Object appId
     
    /**
     * <p></p>
     */
    public static Object platform
     
    /**
     * <p></p>
     */
    public static Object estadoOrden
     
    /**
     * <p></p>
     */
    public static Object ambiente
     
    /**
     * <p></p>
     */
    public static Object timeoutGeneral
     
    /**
     * <p></p>
     */
    public static Object timeoutScroll
     
    /**
     * <p></p>
     */
    public static Object maxScrolls
     
    /**
     * <p></p>
     */
    public static Object textoScroll
     

    static {
        try {
            def selectedVariables = TestCaseMain.getGlobalVariables('default')
			selectedVariables += TestCaseMain.getGlobalVariables(RunConfiguration.getExecutionProfile())
    
            G_BASE_URL = selectedVariables['G_BASE_URL']
            appId = selectedVariables['appId']
            platform = selectedVariables['platform']
            estadoOrden = selectedVariables['estadoOrden']
            ambiente = selectedVariables['ambiente']
            timeoutGeneral = selectedVariables['timeoutGeneral']
            timeoutScroll = selectedVariables['timeoutScroll']
            maxScrolls = selectedVariables['maxScrolls']
            textoScroll = selectedVariables['textoScroll']
            
        } catch (Exception e) {
            TestCaseMain.logGlobalVariableError(e)
        }
    }
}
