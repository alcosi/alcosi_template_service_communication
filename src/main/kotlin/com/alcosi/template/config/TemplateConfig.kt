package com.alcosi.template.config

import Alcosi.Documents.DocumentServiceClient
import Alcosi.Documents.GrpcDocumentServiceClient
import com.alcosi.template.dto.request.AbstractTemplateRequest
import com.alcosi.template.service.ExternalTemplateService
import com.alcosi.template.service.TemplateService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.squareup.wire.GrpcClient
import io.github.breninsul.namedlimitedvirtualthreadexecutor.service.VirtualNamedLimitedExecutorService
import io.github.breninsul.okhttp.logging.JavaLoggingLevel
import io.github.breninsul.okhttp.logging.OKLoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import java.nio.charset.Charset
import java.util.concurrent.ExecutorService

@AutoConfiguration
@ConditionalOnProperty(prefix = "template-service", value = ["enabled"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(TemplateProperties::class)
open class TemplateConfig {

    @Bean("alcosiTemplateGrpcClient")
    @ConditionalOnMissingBean(name = ["alcosiTemplateGrpcClient"])
    open fun getTemplateGrpcClient(properties: TemplateProperties): GrpcClient {
        val interceptors = mutableListOf<Interceptor>()
        if (properties.protocolLogging.loggingLevel != JavaLoggingLevel.OFF) {
            interceptors.add(OKLoggingInterceptor(properties.protocolLogging))
        }
        return createClient(properties, interceptors)
    }

    @Bean("alcosiTemplateObjectMapper")
    @ConditionalOnMissingBean(name = ["alcosiTemplateObjectMapper"])
    open fun getTemplateObjectMapper(properties: TemplateProperties): ObjectMapper {
        val mapper = jacksonObjectMapper()
        mapper.findAndRegisterModules()
        return mapper
    }

    @Autowired
    open fun setDefaultTemplateRequestParams(properties: TemplateProperties) {
        AbstractTemplateRequest.defaultDocumentType = properties.defaultDocumentType
        AbstractTemplateRequest.defaultDocumentLanguage = properties.defaultDocumentLanguage
        AbstractTemplateRequest.defaultEngine = properties.defaultEngine
        AbstractTemplateRequest.defaultServiceResponseCharset = Charset.forName(properties.defaultServiceResponseCharset)
    }

    @Bean("alcosiDocumentServiceClient")
    @ConditionalOnMissingBean(DocumentServiceClient::class)
    open fun getDocumentServiceClient(
        @Qualifier("alcosiTemplateGrpcClient") client: GrpcClient,
    ): DocumentServiceClient {
        return GrpcDocumentServiceClient(client)
    }

    @Bean("alcosiDocumentTemplateServiceClient")
    @ConditionalOnMissingBean(TemplateService::class)
    open fun getDocumentTemplateServiceClient(
        @Qualifier("alcosiDocumentServiceClient") client: DocumentServiceClient,
        @Qualifier("alcosiTemplateObjectMapper") objectMapper: ObjectMapper,
        @Qualifier("asyncAlcosiTemplateExecutorService") executorService: ExecutorService,
        properties: TemplateProperties

    ): TemplateService {
        return ExternalTemplateService(client, objectMapper, executorService, properties.maxParallelRequests, properties.serviceLoggingLevel.javaLevel)
    }

    @Bean("asyncAlcosiTemplateExecutorService")
    @ConditionalOnMissingBean(name = ["asyncAlcosiTemplateExecutorService"])
    open fun getAsyncTemplateExecutorService(
    ): ExecutorService {
        return VirtualNamedLimitedExecutorService("async-template-executor")
    }

    protected open fun createClient(properties: TemplateProperties, interceptors: List<Interceptor>) =
        GrpcClient.Builder()
            .client(configureOkHttpClient(properties.protocol.protocol, interceptors, properties))
            .baseUrl(properties.uri)
            .build()


    protected open fun configureOkHttpClient(
        protocol: Protocol,
        interceptors: List<Interceptor>,
        properties: TemplateProperties,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.protocols(listOf(protocol))
        builder.connectTimeout(properties.connectTimeout)
        builder.readTimeout(properties.readTimeout)
        builder.writeTimeout(properties.writeTimeout)
        interceptors.forEach { interceptor ->
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }
}