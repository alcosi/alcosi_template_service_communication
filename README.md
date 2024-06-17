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


Predefined request types like `MapTemplateRequest`,`DataTemplateRequest`,`AnyTemplateRequest` can be used, or own child for `AbstractTemplateRequest` created depending on template server requirements

Then use it
`service.send(MapTemplateRequest("templateName", mapOf("key" to "val")))`

Configure with properties:

| Parameter                                           | Type                 | Description                                     |
|-----------------------------------------------------|----------------------|-------------------------------------------------|
| `template-service.disabled`                         | boolean              | Enable autoconfig for this starter              |
| `template-service.uri`                              | URI                  | URI of template service                         |
| `template-service.protocol`                         | OkHttpProtocol       | Protocol                                        |
| `template-service.max-parallel-requests`            | Int                  | Max parallel requests to template server        |
| `template-service.protocol-logging-level`           | JavaLoggingLevel     | Request/response logging level (protocol layer) |
| `template-service.service-logging-level`            | JavaLoggingLevel     | Request/response logging level (service layer)  |
| `template-service.logging-disabled`                 | Boolean              | Request logging disabled  (protocol layer)      |
| `template-service.max-log-body-size`                | Int                  | Logging request/response body limit             |
| `template-service.connect-timeout`                  | Duration             | Connect timeout                                 |
| `template-service.read-timeout`                     | Duration             | Read timeout                                    |
| `template-service.write-timeout`                    | Duration             | Write timeout                                   |
| `template-service.default-document-language`        | String               | Default document language                       |
| `template-service.default-document-type`            | TemplateDocumentType | Default document type                           |
| `template-service.default-engine`                   | TemplateEngine       | Default template engine                         |
| `template-service.default-service-response-charset` | String               | Default document (response) charset             |
