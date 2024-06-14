package com.alcosi.template.exception

/**
 * TemplateException is an open class that extends the RuntimeException class.
 * It is used to represent exceptions that occur during template processing.
 *
 * @param message the detail message (optional).
 * @param exception the cause of the exception (optional).
 */
open class TemplateException(message:String?=null,exception: Throwable? = null) : RuntimeException(message,exception)
