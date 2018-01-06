package springfive.twitterconsumer.infra

import org.springframework.boot.SpringApplication
import springfive.twitterconsumer.TweetGatheringApplication
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.GeneralSecurityException
import java.util.*
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.util.Calendar
import java.util.UUID




/**
 * @author claudioed on 18/12/17.
 * Project twitter-consumer
 */
object TwitterUtils {

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
    private fun computeSignature(baseString: String, keyString: String): String {
        var secretKey: SecretKey? = null

        val keyBytes = keyString.toByteArray()
        secretKey = SecretKeySpec(keyBytes, "HmacSHA1")

        val mac = Mac.getInstance("HmacSHA1")
        mac.init(secretKey)

        val text = baseString.toByteArray()
        return String(Base64.getEncoder().encode(mac.doFinal(text))).trim()
    }

    fun generateHeader(twitterConsumerKey:String,twitterConsumerSecret: String): String {

        // this particular request uses POST
        val get_or_post = "POST"

        // I think this is the signature method used for all Twitter API calls
        val oauth_signature_method = "HMAC-SHA1"

        // generate any fairly random alphanumeric string as the "nonce". Nonce = Number used ONCE.
        var uuid_string = UUID.randomUUID().toString()
        uuid_string = uuid_string.replace("-".toRegex(), "")
        val oauth_nonce = uuid_string // any relatively random alphanumeric string will work here

        // get the timestamp
        val tempcal = Calendar.getInstance()
        val ts = tempcal.timeInMillis// get current time in milliseconds
        val oauth_timestamp = (ts / 1000).toString() // then divide by 1000 to get seconds

        // assemble the proper parameter string, which must be in alphabetical order, using your consumer key
        val parameter_string = "oauth_consumer_key=$twitterConsumerKey&oauth_nonce=$oauth_nonce&oauth_signature_method=$oauth_signature_method&oauth_timestamp=$oauth_timestamp&oauth_token=940015005860290560-m0WwSyxGvp5ufff9KW2zm5LGXLaFLov&$oauth_timestamp&oauth_version=1.0"
        println("parameter_string=" + parameter_string) // print out parameter string for error checking, if you want

        // specify the proper twitter API endpoint at which to direct this request
        val twitter_endpoint = "https://api.twitter.com/oauth/request_token"

        // assemble the string to be signed. It is METHOD & percent-encoded endpoint & percent-encoded parameter string
        // Java's native URLEncoder.encode function will not work. It is the wrong RFC specification (which does "+" where "%20" should be)...
        // the encode() function included in this class compensates to conform to RFC 3986 (which twitter requires)
        val signature_base_string = get_or_post + "&" + encode(twitter_endpoint) + "&" + encode(parameter_string)

        // now that we've got the string we want to sign (see directly above) HmacSHA1 hash it against the consumer secret
        var oauth_signature = ""
        try {
            oauth_signature = computeSignature(signature_base_string, twitterConsumerSecret + "&")  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }


        // each request to the twitter API 1.1 requires an "Authorization: BLAH" header. The following is what BLAH should look like
        val authorization_header_string = "OAuth oauth_consumer_key=\"" + twitterConsumerKey + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" +
                oauth_timestamp + "\",oauth_nonce=\"" + oauth_nonce+ "\",oauth_token=\"" + "940015005860290560-m0WwSyxGvp5ufff9KW2zm5LGXLaFLov" + "\",oauth_version=\"1.0\",oauth_signature=\"" + encode(oauth_signature) + "\""
        println("authorization_header_string=" + authorization_header_string)
        return authorization_header_string;

    }





}

open class HeaderRunner {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            TwitterUtils.generateHeader("gupfxwn43NBTdxCD3Tsf1JgMu","pH4uM5LlYxKzfJ7huYRwFbaFXn7ooK01LmqCP69QV9a9kZrHw5")
        }
    }

}