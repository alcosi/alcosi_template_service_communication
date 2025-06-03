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
import java.nio.charset.Charset

/**
 * Represents a request to generate a document using a template.
 *
 * @param templateName the name of the template
 * @param templateParameters the template parameters
 * @param documentType the document type of the template (default is [AbstractTemplateRequest.defaultDocumentType])
 * @param documentLanguage the language of the template (default is [AbstractTemplateRequest.defaultDocumentLanguage])
 * @param engine the template engine to use (default is [AbstractTemplateRequest.defaultEngine])
 * @param responseCharset the character set of the response (default is [AbstractTemplateRequest.defaultServiceResponseCharset])
 */
open class MapTemplateRequest(
    templateName: String,
    templateParameters: Map<String, String?>,
    documentType: TemplateDocumentType = defaultDocumentType,
    documentLanguage: String = defaultDocumentLanguage,
    engine: TemplateEngine = defaultEngine,
    responseCharset: Charset = defaultServiceResponseCharset
) : AbstractTemplateRequest<Map<String, String?>>(
    templateName,
    templateParameters,
    documentType,
    documentLanguage,
    engine,
    responseCharset
)