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
     * @return the response as a string
     */
    fun <T:Any> send(
        request: AbstractTemplateRequest<T>
    ): String

    /**
     * Sends a template request asynchronously and returns a CompletableFuture that completes with the response as a string.
     *
     * @param request the template request to send
     * @return a CompletableFuture that completes with the response as a string
     */
    fun  <T:Any> sendAsync(
        request: AbstractTemplateRequest<T>
    ): CompletableFuture<String>
}
