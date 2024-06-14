package com.alcosi.template.dto

import Alcosi.Documents.GenerateDocumentRequest

/**
 * Enum class representing different template engines used by the TemplateEngine class.
 *
 * @property grpcType The corresponding GenerateDocumentRequest.Engine value for the template engine.
 */
enum class TemplateEngine(val grpcType: GenerateDocumentRequest.Engine) {
    JsReport(GenerateDocumentRequest.Engine.JsReport),
    EPPlus(GenerateDocumentRequest.Engine.EPPlus),
    Word(GenerateDocumentRequest.Engine.Word),
    Razor(GenerateDocumentRequest.Engine.Razor),
    ;
}