package com.alcosi.template.config

import com.alcosi.lib.logging.JavaLoggingLevel
import com.alcosi.template.dto.TemplateDocumentType
import com.alcosi.template.dto.TemplateEngine
import okhttp3.Protocol
import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

/**
 * Configuration properties for the Template service.
 *
 * @property enabled Flag indicating if the Template service is enabled.
 * @property uri The URI of the Template service.
 * @property protocol The protocol to be used for communication with the Template service.
 * @property maxParallelRequests The maximum number of parallel requests to the Template service.
 * @property protocolLoggingLevel The logging level for the Template service.
 * @property loggingDisabled Flag indicating if logging is disabled for the Template service.
 * @property maxLogBodySize The maximum size of the log body for the Template service.
 * @property connectTimeout The connect timeout for communicating with the Template service.
 * @property readTimeout The read timeout for communicating with the Template service.
 * @property writeTimeout The write timeout for communicating with the Template service.
 * @property defaultDocumentLanguage The default document language for the Template service.
 * @property defaultDocumentType The default document type for the Template service.
 * @property defaultEngine The default template engine for the Template service.
 * @property defaultServiceResponseCharset The default character set for the Template service response.
 */
@ConfigurationProperties(prefix = "template-service")
open class TemplateProperties(
    var enabled:Boolean = true,
    var uri: String = "http://127.0.0.1:1234",
    var protocol: OkHttpProtocol = OkHttpProtocol.H2_PRIOR_KNOWLEDGE,
    var maxParallelRequests:Int = 10,
    var protocolLoggingLevel: JavaLoggingLevel = JavaLoggingLevel.FINEST,
    var serviceLoggingLevel: JavaLoggingLevel = JavaLoggingLevel.INFO,
    var loggingDisabled: Boolean = false,
    var maxLogBodySize: Int = 10000,
    var connectTimeout: Duration = Duration.ofSeconds(10),
    var readTimeout: Duration = Duration.ofSeconds(120),
    var writeTimeout: Duration = Duration.ofSeconds(120),
    var defaultDocumentLanguage:String ="",
    var defaultDocumentType:TemplateDocumentType =TemplateDocumentType.PDF,
    var defaultEngine: TemplateEngine = TemplateEngine.Razor,
    var defaultServiceResponseCharset:String = "UTF-8",
    ) {
    /**
     * Enum class representing the supported protocols for OkHttp.
     *
     * @property protocol The corresponding OkHttp protocol.
     */
    enum class OkHttpProtocol(val protocol: Protocol) {
        HTTP_1_0(Protocol.HTTP_1_0),
        HTTP_1_1(Protocol.HTTP_1_1),
        HTTP_2(Protocol.HTTP_2),
        @Deprecated("OkHttp has dropped support for SPDY. Prefer {@link #HTTP_2}.")
        SPDY_3(Protocol.SPDY_3),
        H2_PRIOR_KNOWLEDGE(Protocol.H2_PRIOR_KNOWLEDGE),
        QUIC(Protocol.QUIC)
    }
}