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