package query;

import core.twilio.SMSMessageManager;
import dbaccess.dbojbects.pojo.Ngodistrict;
import org.apache.log4j.Logger;
import play.mvc.Http;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by nhosgur on 1/6/15.
 */
public class SMSBroadcastingUtility {
    private static final Logger itsLogger = Logger.getLogger(SMSBroadcastingUtility.class);

    public static String smsToDistrict(final Http.Request request) throws ParseException {
        String message = null;
        String district = null;
        String createdBy = null;
        try {
            Map<String, String[]> qs = request.queryString();

            String body = qs.get("Body")[0].toLowerCase();

            String[] splitted = body.split(":");

            if (splitted.length < 3)
                return "Make sure to have District name, and a message to send";

            district = splitted[1];
            if (!DistrictUtility.isThereADistricteNamed(district))
                return "District[" + district + "]does not exist";

            createdBy = qs.get("From")[0];
            Set<String> districts = new HashSet<>();
            districts.add(district);
            message = splitted[2];
            SMSMessageManager.sendMessageToDistrict(districts,"Message From:["+createdBy+"]["+message+"]");
            return "Your message is sent to the district of["+district+"]";
        }catch (Throwable e){
            String errMessage = "Message["+message+"]failed to be sent to the district of["+district+"]sent by["+createdBy+"]";
            itsLogger.error(errMessage+"due to :"+e.getMessage(),e);
            return errMessage;
        }

    }


    public static String smsToAllDistricts(Http.Request request) {
        String message = null;
        String createdBy = null;
        try {
            Map<String, String[]> qs = request.queryString();

            String body = qs.get("Body")[0].toLowerCase();

            String[] splitted = body.split(":");

            if (splitted.length < 2)
                return "Make sure to have the message";


            createdBy = qs.get("From")[0];
            Set<String> districts = new HashSet<>();
            Iterator<Ngodistrict> it = DistrictUtility.getDistricts();

            while(it.hasNext()) {
                districts.add(it.next().getDistrict());
            }

            message = splitted[1];
            SMSMessageManager.sendMessageToDistrict(districts,"Message From:["+createdBy+"]["+message+"]");
            return "Your message is sent to the all the districts";
        }catch (Throwable e){
            String errMessage = "Message["+message+"]failed to be sent to all the districts sent by["+createdBy+"]";
            itsLogger.error(errMessage+"due to :"+e.getMessage(),e);
            return errMessage;
        }
    }
}
