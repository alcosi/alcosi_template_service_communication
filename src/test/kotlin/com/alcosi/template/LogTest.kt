package com.alcosi.template

import com.alcosi.template.config.TemplateConfig
import com.alcosi.template.config.TemplateProperties
import com.alcosi.template.dto.request.MapTemplateRequest
import com.alcosi.template.service.TemplateService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

class LogTest {
    val service: TemplateService

    init {
        val config = TemplateConfig()
        val executor = config.getAsyncTemplateExecutorService()
        val properties = TemplateProperties(uri = "https://test-c.free.beeceptor.com", protocol = TemplateProperties.OkHttpProtocol.H2_PRIOR_KNOWLEDGE)
        val grpc = config.getTemplateGrpcClient(properties)
        val client = config.getDocumentServiceClient(grpc)
        config.setDefaultTemplateRequestParams(properties)
        service = config.getDocumentTemplateServiceClient(client, jacksonObjectMapper(), executor, properties)
    }

    @Test
    fun `test` () {
        service.send(MapTemplateRequest("name", mapOf("key" to "val")))
    }


}
