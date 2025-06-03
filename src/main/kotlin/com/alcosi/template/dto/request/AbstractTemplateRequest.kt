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
 * This is the base class for template requests.
 *
 * @param T the type of template parameters
 * @property templateName the name of the template
 * @property templateParameters the template parameters
 * @property documentType the document type of the template (default is [defaultDocumentType])
 * @property documentLanguage the language of the template (default is [defaultDocumentLanguage])
 * @property engine the template engine to use (default is [defaultEngine])
 * @property responseCharset the character set of the response (default is [defaultServiceResponseCharset])
 */
open class AbstractTemplateRequest<T> (
    open val templateName:String,
    open val templateParameters:T,
    open val documentType: TemplateDocumentType = defaultDocumentType,
    open val documentLanguage:String= defaultDocumentLanguage,
    open val engine: TemplateEngine = defaultEngine,
    open val responseCharset: Charset = defaultServiceResponseCharset
){
    companion object{
        /**
         * The default document type for template requests.
         *
         * Possible values are:
         * - [TemplateDocumentType.PDF]
         * - [TemplateDocumentType.XLSX]
         * - [TemplateDocumentType.DOCX]
         * - [TemplateDocumentType.HTML]
         * - [TemplateDocumentType.TEXT]
         * - [TemplateDocumentType.CSV]
         */
        var defaultDocumentType: TemplateDocumentType = TemplateDocumentType.PDF
        /**
         * The default language for documents.
         */
        var defaultDocumentLanguage:String=""
        /**
         * The default template engine used by template requests.
         */
        var defaultEngine: TemplateEngine = TemplateEngine.Razor
        /**
         * The default character set used for service response.
         */
        var defaultServiceResponseCharset: Charset =Charsets.UTF_8
    }
}