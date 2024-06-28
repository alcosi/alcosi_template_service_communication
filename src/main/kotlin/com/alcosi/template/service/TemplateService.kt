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
