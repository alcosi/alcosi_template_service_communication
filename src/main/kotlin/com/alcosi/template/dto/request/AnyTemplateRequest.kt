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
 * A request to generate a template document.
 *
 * @param templateName The name of the template.
 * @param templateParameters The parameters for the template.
 * @param documentType The document type of the template.
 * @param documentLanguage The language of the template.
 * @param engine The template engine to use.
 * @param responseCharset The character set of the response.
 */
open class AnyTemplateRequest(
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
)