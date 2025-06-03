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

package com.alcosi.template.dto.request

import com.alcosi.template.dto.TemplateDocumentType
import com.alcosi.template.dto.TemplateEngine
import com.fasterxml.jackson.annotation.JsonProperty
import java.nio.charset.Charset

/**
 * Represents a request to generate a data-based template document.
 *
 * @param templateName the name of the template
 * @param templateParameters the template parameters
 * @param documentType the document type of the template
 * @param documentLanguage the language of the template
 * @param engine the template engine to use
 * @param responseCharset the character set of the response
 */
open class DataTemplateRequest(
    templateName: String,
    templateParameters: Any,
    documentType: TemplateDocumentType = defaultDocumentType,
    documentLanguage: String = defaultDocumentLanguage,
    engine: TemplateEngine = defaultEngine,
    responseCharset: Charset = defaultServiceResponseCharset
) : AbstractTemplateRequest<Any>(
    templateName,
    templateParameters,
    documentType,
    documentLanguage,
    engine,
    responseCharset
) {
    open class Data(
        @JsonProperty("data") val data: List<Field>,
    ) {
        constructor(map: Map<String, String>) : this(map.map { Field(it.key, it.value) })
    }

    open class Field(
        @JsonProperty("key") val key: String,
        @JsonProperty("value") val value: String,
    )
}