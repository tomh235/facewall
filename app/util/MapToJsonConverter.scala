package util

import play.api.libs.json.{JsString, JsObject, Json, JsValue}

object MapToJsonConverter {
    def toJson(map: Map[String, Any]): JsValue = {
        JsObject(
            map.mapValues { _ match {
                    case value: String => JsString(value)
                    case value: Map[String, Any] => toJson(value)
                }
            }.toSeq
        )
    }
}
