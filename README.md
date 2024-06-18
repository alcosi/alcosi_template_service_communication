### Library for low-level communication with Alcosi (C#) template service with spring boot initialization.

### Using:

- To use just add as dependency:

````kotlin
dependencies {
//Other dependencies
    implementation("com.alcosi:alcosi-template-service-communication:${version}")
//Other dependencies
}

````

- Then get service `com.alcosi.template.service.TemplateService` from spring boot context

Predefined request types like `MapTemplateRequest`,`DataTemplateRequest`,`AnyTemplateRequest` can be used, or own child
for `AbstractTemplateRequest` created depending on template server requirements

Then use it
`service.send(MapTemplateRequest("templateName", mapOf("key" to "val")))`

Configure with properties:

| Parameter                                                       | Type                 | Description                                      |
|-----------------------------------------------------------------|----------------------|--------------------------------------------------|
| `template-service.disabled`                                     | boolean              | Enable autoconfig for this starter               |
| `template-service.uri`                                          | URI                  | URI of template service                          |
| `template-service.protocol`                                     | OkHttpProtocol       | Protocol                                         |
| `template-service.max-parallel-requests`                        | Int                  | Max parallel requests to template server         |
| `template-service.service-logging-level`                        | JavaLoggingLevel     | Request/response logging level (service layer)   |
| `template-service.service-log-body`                             | Boolean              | Log body content or not                          |
| `template-service.connect-timeout`                              | Duration             | Connect timeout                                  |
| `template-service.read-timeout`                                 | Duration             | Read timeout                                     |
| `template-service.write-timeout`                                | Duration             | Write timeout                                    |
| `template-service.default-document-language`                    | String               | Default document language                        |
| `template-service.default-document-type`                        | TemplateDocumentType | Default document type                            |
| `template-service.default-engine`                               | TemplateEngine       | Default template engine                          |
| `template-service.default-service-response-charset`             | String               | Default document (response) charset              |
| `template-service.protocol-logging.enabled`                     | Boolean              | Enable autoconfig for this starter               |
| `template-service.protocol-logging.logging-level`               | JavaLoggingLevel     | Logging level of messages                        |
| `template-service.protocol-logging.max-body-size`               | Int                  | Max logging body size                            |
| `template-service.protocol-logging.order`                       | Int                  | Filter order (Ordered interface)                 |
| `template-service.protocol-logging.new-line-column-symbols`     | Int                  | How many symbols in first column (param name)    |
| `template-service.protocol-logging.request.id-included`         | Boolean              | Is request id included to log message (request)  |
| `template-service.protocol-logging.request.uri-included`        | Boolean              | Is uri included to log message (request)         |
| `template-service.protocol-logging.request.took-time-included`  | Boolean              | Is timing included to log message (request)      |
| `template-service.protocol-logging.request.headers-included`    | Boolean              | Is headers included to log message (request)     |
| `template-service.protocol-logging.request.body-included`       | Boolean              | Is body included to log message (request)        |
| `template-service.protocol-logging.response.id-included`        | Boolean              | Is request id included to log message (response) |
| `template-service.protocol-logging.response.uri-included`       | Boolean              | Is uri included to log message (response)        |
| `template-service.protocol-logging.response.took-time-included` | Boolean              | Is timing included to log message (response)     |
| `template-service.protocol-logging.response.headers-included`   | Boolean              | Is headers included to log message (response)    |
| `template-service.protocol-logging.response.body-included`      | Boolean              | Is body included to log message (response)       |


