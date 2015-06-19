package config

/**
 * Created by nhosgur on 8/11/14.
 */
import play.api.Play
object NGOConfig {
  val TWILIO_ACCOUNT_SID: String = Play.current.configuration.getString("twilio.accountSidId").getOrElse("twilio.accountSidId is not set")
  val TWILIO_AUTH_TOKEN: String = Play.current.configuration.getString("twilio.secretKey").getOrElse("twilio.secretKey is not set")
  val TWILIO_NUMBER: String = Play.current.configuration.getString("twilio.phoneNumber").getOrElse("twilio.phoneNumber is not set ")
  val NGO_ROOT_DIRECTORY: String  = Play.current.configuration.getString("twilio.ngoRootDirectory").getOrElse(
    (System.getProperty("user.home") + "/NGORepo/"))


}
