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