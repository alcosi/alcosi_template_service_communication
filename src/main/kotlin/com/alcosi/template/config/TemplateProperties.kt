/*
 *
 *  * Copyright (c) 2025 Alcosi Group Ltd. and affiliates.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package com.alcosi.template.config

import com.alcosi.template.dto.TemplateDocumentType
import com.alcosi.template.dto.TemplateEngine
import io.github.breninsul.logging2.HttpLogSettings
import io.github.breninsul.logging2.JavaLoggingLevel

import io.github.breninsul.okhttp.logging.OkHttpLoggerProperties
import okhttp3.Protocol
import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

/**
 * The `TemplateProperties` class represents the configuration properties for the template service.
 *
 * @property enabled Indicates whether the template service is enabled.
 * @property uri The URI of the template service.
 * @property protocol The protocol to be used by the template service.
 * @property maxParallelRequests The maximum number of parallel requests allowed.
 * @property serviceLoggingLevel The logging level for the template service.
 * @property protocolLogging The logging settings for the protocol.
 * @property connectTimeout The connection timeout duration.
 * @property readTimeout The read timeout duration.
 * @property writeTimeout The write timeout duration.
 * @property defaultDocumentLanguage The default language for the template documents.
 * @property defaultDocumentType The default type for the template documents.
 * @property defaultEngine The default engine for the template service.
 * @property defaultServiceResponseCharset The default charset for the service response.
 */
@ConfigurationProperties(prefix = "template-service")
open class TemplateProperties(
    var enabled:Boolean = true,
    var uri: String = "http://127.0.0.1:1234",
    var protocol: OkHttpProtocol = OkHttpProtocol.H2_PRIOR_KNOWLEDGE,
    var maxParallelRequests:Int = 10,
    var serviceLoggingLevel: JavaLoggingLevel = JavaLoggingLevel.INFO,
    var serviceLogRequestBody: Boolean = true,
    var serviceLogResponseBody: Boolean = true,
    var protocolLogging: OkHttpLoggerProperties=OkHttpLoggerProperties(
        request = HttpLogSettings(bodyIncluded = false, tookTimeIncluded = false),
        response = HttpLogSettings(bodyIncluded = false, tookTimeIncluded = false)
    ),
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