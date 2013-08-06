package util

import play.api.libs.json.{JsString, JsObject, JsValue}

object JsonConverter {
    def fromMap(map: Map[String, Any]): JsValue = {
        JsObject(
            map.mapValues { _ match {
                    case value: String => JsString(value)
                    case value: Map[String, Any] => fromMap(value)
                    case _ => throw new UnsupportedOperationException("Cannot convert non-Json type")
                }
            }.toSeq
        )
    }
}
