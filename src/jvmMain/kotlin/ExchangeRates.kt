import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import org.jsoup.Jsoup

fun getRateByName(name:String): Double {
    val connect = Jsoup.connect("http://data.fixer.io/api/latest" +
            "?access_key=f07466d31cfc67791dde385402b20866").ignoreContentType(true).get().body().text()
    val jsonString = StringBuilder(connect)
    val parser = Parser()
    val json = parser.parse(jsonString) as JsonObject
    return (json["rates"] as JsonObject)[name].toString().toDouble()

}
fun convert (rateFrom:String, rateTo:String): Double {
    val courseRateFrom = getRateByName(rateFrom)
    val courseRateTo = getRateByName(rateTo)
    val difRate =courseRateTo / courseRateFrom
    return (difRate * 100.0).toInt() / 100.0
}
fun getRate(): List<String> {
    val connect = Jsoup.connect("http://data.fixer.io/api/latest" +
            "?access_key=f07466d31cfc67791dde385402b20866").ignoreContentType(true).get().body().text()
    val jsonString = StringBuilder(connect)
    val parser = Parser()
    val json = parser.parse(jsonString) as JsonObject
    return (json["rates"] as JsonObject).map.keys.toList()
}