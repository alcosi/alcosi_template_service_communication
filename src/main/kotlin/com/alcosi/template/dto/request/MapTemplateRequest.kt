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