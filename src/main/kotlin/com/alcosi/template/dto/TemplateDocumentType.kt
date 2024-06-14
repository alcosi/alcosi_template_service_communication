package com.alcosi.template.dto

import Alcosi.Documents.GenerateDocumentRequest

/**
 * The `TemplateDocumentType` enum class represents the different types of template documents that can be generated.
 *
 * @property grpcType The gRPC document type corresponding to the template document type.
 */
enum class TemplateDocumentType(val grpcType: GenerateDocumentRequest.DocumentType) {
    PDF(GenerateDocumentRequest.DocumentType.PDF),
    XLSX(GenerateDocumentRequest.DocumentType.XLSX),
    DOCX(GenerateDocumentRequest.DocumentType.DOCX),
    HTML(GenerateDocumentRequest.DocumentType.HTML),
    TEXT(GenerateDocumentRequest.DocumentType.TEXT),
    CSV(GenerateDocumentRequest.DocumentType.CSV),
}