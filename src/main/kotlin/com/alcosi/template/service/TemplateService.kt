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

package com.alcosi.template.service

import com.alcosi.template.dto.request.AbstractTemplateRequest
import java.util.concurrent.CompletableFuture

/**
 * The TemplateService interface provides methods for sending template requests and receiving template responses.
 */
interface TemplateService {
    /**
     * Sends a template request and returns the response as a string.
     *
     * @param request the template request to send
     * @param logRequestBody flag indicating whether to log the request body (default is null)
     * @param logResponseBody flag indicating whether to log the response body (default is null)
     * @return the response as a string
     */
    fun <T:Any> send(
        request: AbstractTemplateRequest<T>,logRequestBody:Boolean?=null,logResponseBody:Boolean?=null
    ): String

    /**
     * Asynchronously sends a template request and returns the response as a CompletableFuture of String.
     *
     * @param request the template request to send
     * @param logRequestBody flag indicating whether to log the request body (default is null)
     * @param logResponseBody flag indicating whether to log the response body (default is null)
     * @return a CompletableFuture representing the response as a string
     */
    fun  <T:Any> sendAsync(
        request: AbstractTemplateRequest<T>,logRequestBody:Boolean?=null,logResponseBody:Boolean?=null
    ): CompletableFuture<String>
}
