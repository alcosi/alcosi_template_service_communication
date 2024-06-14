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