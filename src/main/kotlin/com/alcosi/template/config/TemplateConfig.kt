package com.alcosi.template.config

import Alcosi.Documents.DocumentServiceClient
import Alcosi.Documents.GrpcDocumentServiceClient
import com.alcosi.lib.logging.http.okhttp.OKLoggingInterceptor
import com.alcosi.template.dto.request.AbstractTemplateRequest
import com.alcosi.template.dto.request.AnyTemplateRequest
import com.alcosi.template.dto.request.MapTemplateRequest
import com.squareup.wire.GrpcClient
import io.github.breninsul.namedlimitedvirtualthreadexecutor.service.VirtualNamedLimitedExecutorService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.charset.Charset
import java.util.concurrent.ExecutorService

@Configuration
@ConditionalOnProperty(prefix = "template-service", value = ["enabled"], havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(TemplateProperties::class)
open class TemplateConfig {
    @Bean("templateGrpcClient")
    @ConditionalOnMissingBean(name = ["templateGrpcClient"])
    open fun getTemplateGrpcClient(properties: TemplateProperties): GrpcClient {
        val interceptors = mutableListOf<Interceptor>()
        if (!properties.loggingDisabled) {
            interceptors.add(OKLoggingInterceptor(properties.maxLogBodySize, properties.loggingLevel.javaLevel, 1))
        }
        return createClient(properties, interceptors)
    }

    @Autowired
    open fun setDefaultTemplateRequestParams(properties: TemplateProperties) {
        AbstractTemplateRequest.defaultDocumentType = properties.defaultDocumentType
        AbstractTemplateRequest.defaultDocumentLanguage = properties.defaultDocumentLanguage
        AbstractTemplateRequest.defaultEngine = properties.defaultEngine
        AbstractTemplateRequest.defaultServiceResponseCharset = Charset.forName(properties.defaultServiceResponseCharset)
    }

    @Bean
    @ConditionalOnMissingBean(DocumentServiceClient::class)
    open fun getDocumentServiceClient(
        @Qualifier("templateGrpcClient") client: GrpcClient,
    ): DocumentServiceClient {
        return GrpcDocumentServiceClient(client)
    }

    @Bean("asyncTemplateExecutorService")
    @ConditionalOnMissingBean(name = ["asyncTemplateExecutorService"])
    open fun getAsyncTemplateExecutorService(
        @Qualifier("templateGrpcClient") client: GrpcClient,
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