package util

import play.api.libs.json.{JsString, JsObject, Json, JsValue}

object JsonConverter {
    def fromMap(map: Map[String, Any]): JsValue = {
        JsObject(
            map.mapValues { _ match {
                    case value: String => JsString(value)
                    case value: Map[String, Any] => fromMap(value)
                }
            }.toSeq
        )
    }
}
