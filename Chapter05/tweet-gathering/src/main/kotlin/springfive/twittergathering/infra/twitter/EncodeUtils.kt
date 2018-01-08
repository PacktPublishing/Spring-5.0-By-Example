package springfive.twittergathering.infra.twitter

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.GeneralSecurityException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


/**
 * @author claudioed on 18/12/17.
 * Project twitter-consumer
 */
object EncodeUtils {

    fun encode(value: String): String {
        var encoded: String? = null
        try {
            encoded = URLEncoder.encode(value, "UTF-8")
        } catch (ignore: UnsupportedEncodingException) {
        }

        val buf = StringBuilder(encoded!!.length)
        var focus: Char
        var i = 0
        while (i < encoded.length) {
            focus = encoded[i]
            if (focus == '*') {
                buf.append("%2A")
            } else if (focus == '+') {
                buf.append("%20")
            } else if (focus == '%' && i + 1 < encoded.length
                    && encoded[i + 1] == '7' && encoded[i + 2] == 'E') {
                buf.append('~')
                i += 2
            } else {
                buf.append(focus)
            }
            i++
        }
        return buf.toString()
    }

    @Throws(GeneralSecurityException::class, UnsupportedEncodingException::class)
    fun computeSignature(baseString: String, keyString: String): String {
        val keyBytes = keyString.toByteArray()
        val secretKey = SecretKeySpec(keyBytes, "HmacSHA1")
        val mac = Mac.getInstance("HmacSHA1")
        mac.init(secretKey)
        val text = baseString.toByteArray()
        return String(Base64.getEncoder().encode(mac.doFinal(text))).trim()
    }

}
