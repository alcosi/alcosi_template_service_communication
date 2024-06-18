package com.alcosi.template.service

import Alcosi.Documents.DocumentServiceClient
import Alcosi.Documents.GenerateDocumentReply
import Alcosi.Documents.GenerateDocumentRequest
import com.alcosi.template.dto.request.AbstractTemplateRequest
import com.alcosi.template.exception.TemplateException
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.breninsul.javatimerscheduler.sync
import kotlinx.coroutines.runBlocking
import java.nio.charset.Charset
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Semaphore
import java.util.logging.Level
import java.util.logging.Logger

/**
 * This class represents an external template service that implements the
 * TemplateService interface.
 *
 * @property client the DocumentServiceClient used to generate documents
 * @property objectMapper the ObjectMapper used to serialize data
 * @property asyncExecutor the ExecutorService used for async operations
 * @property maxParallelProcesses the maximum number of parallel processes allowed
 * @property loggingLevel service layer logging level
 * @property semaphore a semaphore used to limit the parallel processes (null if maxParallelProcesses <= 0)
 */
open class ExternalTemplateService(
    protected open val client: DocumentServiceClient,
    protected open val objectMapper: ObjectMapper,
    protected open val asyncExecutor: ExecutorService,
    maxParallelProcesses: Int,
    val loggingLevel: Level,
    val logBody:Boolean
) : TemplateService {
    protected open val logger: Logger = Logger.getLogger(this.javaClass.name)
    protected open val semaphore: Semaphore? = if (maxParallelProcesses > 0) Semaphore(maxParallelProcesses) else null

    /**
     * Synchronizes the execution of a callable if the semaphore exists.
     *
     * @param call the callable to be executed
     * @return the result of the callable's execution
     */
    protected open fun <T> Semaphore?.syncIfExist(call: Callable<T>): T {
        return if (this == null) {
            call.call()
        } else {
            this.sync(call)
        }
    }

    /**
     * Sends a template request and returns the response as a string.
     *
     * @param request the template request to send
     * @return the response as a string
     */
    override fun <T : Any> send(request: AbstractTemplateRequest<T>): String {
        try {
            val generateDocumentRequest = createRequest(request)
            val generateDocumentReply = semaphore.syncIfExist { client.GenerateDocument().executeBlocking(generateDocumentRequest) }
            val content = getContent(generateDocumentReply, request.responseCharset)
            return content
        } catch (t: Throwable) {
            return processException(t)
        }
    }

    /**
     * Sends a template request asynchronously and returns a CompletableFuture
     * that completes with the response as a string.
     *
     * @param request the template request to send
     * @return a CompletableFuture that completes with the response as a string
     */
    override fun <T : Any> sendAsync(request: AbstractTemplateRequest<T>): CompletableFuture<String> {
        try {
            val generateDocumentRequest = createRequest(request)
            val content = CompletableFuture.supplyAsync({
                semaphore.syncIfExist {
                    runBlocking {
                        getContent(client.GenerateDocument().execute(generateDocumentRequest), request.responseCharset)
                    }
                }
            }, asyncExecutor)
            return content
        } catch (t: Throwable) {
            return CompletableFuture.completedFuture(processException(t))
        }
    }

    /**
     * Returns the content of the generated document as a string.
     *
     * @param generateDocumentReply the GenerateDocumentReply object containing
     *     the generated document
     * @param charset the charset used to decode the document string
     * @return the content of the generated document as a string
     */
    protected open fun getContent(
        generateDocumentReply: GenerateDocumentReply,
        charset: Charset
    ): String {
        val content= generateDocumentReply.document.string(charset)
        logger.log(loggingLevel,constructRsBody(content))
        return content
    }

    /**
     * Creates a [GenerateDocumentRequest] based on the given [request].
     *
     * @param request the template request to create the
     *     GenerateDocumentRequest from
     * @return a GenerateDocumentRequest object
     */
    protected open fun <T : Any> createRequest(request: AbstractTemplateRequest<T>): GenerateDocumentRequest {
        val rq = GenerateDocumentRequest(
            template_id = request.templateName,
            document_type = request.documentType.grpcType,
            lang = request.documentLanguage,
            data_ = serializeData(request.templateParameters),
            engine = request.engine.grpcType
        )
        logger.log(loggingLevel, constructRqBody(rq))
        return rq
    }

    /**
     * Constructs the request body for the given GenerateDocumentRequest.
     *
     * @param body the GenerateDocumentRequest object to construct the request
     *     body from
     * @return the constructed request body as a string
     */
    protected open fun constructRqBody(
        body: GenerateDocumentRequest,
    ): String {
        val logString =
            """
            ===========================CLIENT GRPC Template request begin===========================
            =Body         :${if (logBody)objectMapper.writeValueAsString(body) else "hidden"}
            =Body size    :${body.data_.length}
            =Body hash    :${body.data_.hashCode()}
            ===========================CLIENT GRPC Template request end  ==========================
            """.trimIndent()
        return logString
    }

    protected open fun constructRsBody(
        body: String,
    ): String {
        val logString =
            """
            ===========================CLIENT GRPC Template response begin===========================
            =Body         : ${if (logBody)body else "hidden"}
            =Body size    :${body.length}
            =Body hash    :${body.hashCode()}
            ===========================CLIENT GRPC Template response end  ==========================
            """.trimIndent()
        return logString
    }

    /**
     * Processes the given exception and returns a formatted string
     * representation of the exception.
     *
     * @param t the exception to be processed
     * @return a formatted string representation of the exception
     * @throws TemplateException if an exception occurs during the processing
     */
    protected open fun processException(t: Throwable): String {
        logger.log(java.util.logging.Level.SEVERE, "Exception Template service:", t)
        throw TemplateException(exception = t)
    }

    /**
     * Serializes the given data into a string representation using the
     * objectMapper.
     *
     * @param params the data to be serialized
     * @return the serialized data as a string
     * @throws NullPointerException if the serialization process returns null
     */
    protected open fun serializeData(params: Any) = objectMapper.writeValueAsString(params)!!

}
