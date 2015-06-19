package controllers;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.verbs.Sms;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
import config.NGOConfig;
import dbaccess.NgoregistrationBuilder;
import dbaccess.dbojbects.pojo.Ngocrop;
import dbaccess.dbojbects.pojo.Ngodistrict;
import dbaccess.dbojbects.pojo.Ngoregistration;
import org.apache.log4j.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import query.*;
import synchup.SynchTriggerer;

import java.util.Iterator;
import java.util.Map;

public class NgoTwilioApp extends Controller {
    private static final Logger itsLogger = Logger.getLogger(Application.class);
    private static final String FAILED =
            "Failed to execute your command. Make sure it is valid, text Tutorial or T to see the commands";
    private static final String HELP = "[ListDistricts,ld,l],[Harvest,h][Request,req,r]" +
            "[Plant,p][RegisterDistrict,rd,rn][RegisterPhoneNumber,rp,rn]" +
            "[ScheduleMeeting,sm,cm]," +
            "[cancelmeeting,dm]," +
            "[rsvp]," +
            "[lrsvp]," +
            "[bc,bca]" +
            "[Tutorial,T]";

    // Find your Account Sid and Token at twilio.com/user/account
    public static String TWILIO_ACCOUNT_SID = NGOConfig.TWILIO_ACCOUNT_SID();
    public static String TWILIO_AUTH_TOKEN = NGOConfig.TWILIO_AUTH_TOKEN();
    public static String TWILIO_NUMBER = NGOConfig.TWILIO_NUMBER();

    public static TwilioRestClient client = new TwilioRestClient(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
    public static Account mainAccount = client.getAccount();

    public static String processRequst() throws TwilioRestException {
        try {
            NgoCommand command = parseCommand();

            switch (command) {
                case ListDistricts:
                case ld:
                case l: {
                    return listDistricts();
                }
                case Tutorial:
                case T: {
                    return HELP;
                }
                case Harvest:
                case h: {
                    return HarvestUtility.harvest(request());
                }
                case Request:
                case req:
                case r: {
                    return queryDistrict();
                }
                case Plant:
                case p: {
                    return PlantUtility.plant(request());
                }
                case RegisterDistrict:
                case rd: {
                    return registerDistrcit();
                }
                case Sell:
                case s: {
                    return SellUtility.sell(request());
                }
                case RegisterPhoneNumber:
                case rp:
                case rn: {
                    return registerPhoneNumberToDistrict();
                }
                case ScheduleMeeting:
                case cm:
                case sm: {
                    return  MeetingSchedulerUtility.schedule(request());
                }
                case dm:
                case cancelmeeting:{
                    return  MeetingSchedulerUtility.delete(request());
                }
                case rsvp:{
                    return  RSVPUtility.rsvp(request());
                }
                case lrsvp:{
                    return  RSVPUtility.lrsvp(request());
                }
                case updatemeeting:{
                    return  MeetingSchedulerUtility.schedule(request());
                }
                case bc:{
                    return SMSBroadcastingUtility.smsToDistrict(request());
                }
                case bca:{
                    return SMSBroadcastingUtility.smsToAllDistricts(request());
                }

                default: {
                    throw new IllegalArgumentException(FAILED);
                }
            }
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
            return FAILED;
        }

    }

    private static String listDistricts() {
        StringBuilder sb = new StringBuilder();
        Iterator<Ngodistrict> it = DistrictUtility.getDistricts();
        int i = 0;
        while (it.hasNext()) {
            i++;
            sb.append(it.next().getDistrict()).append(",");
        }
        if (i == 0)
            return "There is currently no Districts";
        return (i > 1 ? "The districts are [" : "The only district currently registered is [") + sb
                .substring(0, sb.length() - 1).toString() + "]";


    }

    private static String registerDistrcit() {
        Map<String, String[]> qs = request().queryString();

        String body = qs.get("Body")[0].replace(" ", "").toLowerCase();

        String[] splitted = body.split(":");
        if (splitted.length < 2)
            return "District is empty";
        if (splitted.length != 2)
            return "It seems there are extra parameters, make sure to have one district";

        String district = splitted[1].toLowerCase();
        if (DistrictUtility.isThereADistricteNamed(district))
            return "District[" + district + "]already exist";

        try {
            Ngodistrict distObj = new Ngodistrict();
            distObj.setDistrict(district);
            DistrictUtility.createDistrict(distObj);
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
            return "Failed to create the district";
        }
        return "Created the district of [" + district + "]";
    }

    private static NgoCommand parseCommand() {
        Map<String, String[]> qs = request().queryString();
        String body = qs.get("Body")[0].replace(" ", "").toLowerCase();
        String[] split = body.split(":");
        return NgoCommand.fromString(split[0]);
    }

    private static String registerPhoneNumberToDistrict() {
        Map<String, String[]> qs = request().queryString();
        String phoneNumber = qs.get("From")[0];
        String body = qs.get("Body")[0].replace(" ", "").toLowerCase();


        String[] splitted = body.split(":");
        if (splitted.length < 2)
            return "District Name is empty";
        if (splitted.length > 2)
            return "There can be only one district";
        String district = splitted[1];

        return registerWithTheDistrict(phoneNumber, district);
    }

    private static String queryDistrict() {
        Map<String, String[]> qs = request().queryString();
        String body = qs.get("Body")[0].replace(" ", "").toLowerCase();
        String[] splitted = body.split(":");

        Ngoregistration crd = null;
        if (splitted.length < 2 && null == (crd = findCurrentlyRegisteredDistrict())) {
            return "It seems you are not registered with any district. Please either register by sending \"Rp:DISTRICT\" or provide a district name.";
        }
        String district = (splitted.length < 2 || null == splitted[1]) ? crd.getDistrict().toLowerCase() : splitted[1];
        if (!DistrictUtility.isThereADistricteNamed(district))
            return "District [" + district + "] does not exist";
        Iterator<Ngocrop> it = NgoCropUtility.getByDistrict(district);
        if (null == it || !it.hasNext()) {
            return "There is nothing in the district of [" + district + "]";
        }
        StringBuilder sb = new StringBuilder("District [");
        sb.append(district).append("] has: ");

        while (it.hasNext()) {
            Ngocrop dr = it.next();
            sb.append("[").append(dr.getQuantity()).append("]").append(dr.getCropname()).append("[")
                    .append(dr.getAttribute()).append("]").append(",");
        }
        return sb.substring(0, sb.length() - 1).toString();
    }

    private static Ngoregistration findCurrentlyRegisteredDistrict() {
        Map<String, String[]> qs = request().queryString();
        String[] phoneNumbers = qs.get("From");
        if (null == phoneNumbers)
            throw new IllegalStateException("Could not acquire the phone number");
        return NgoRegistrationUtility.getByPhoneNumber(phoneNumbers[0]);
    }


    private static String registerWithTheDistrict(String phoneNumber, String district) {
        if (!DistrictUtility.isThereADistricteNamed(district))
            return "District[" + district + "]does not exist";
        Ngoregistration reg = findCurrentlyRegisteredDistrict();

        if (null == reg) {
            Ngoregistration otc = new NgoregistrationBuilder().setDistrict(district).setPhonenumber(phoneNumber).createNgoregistration();
            reg = NgoRegistrationUtility.create(otc);
            String res =
                    String.format("Registered phone number [%s] with district [%s]", phoneNumber, district);
            if (itsLogger.isDebugEnabled()) {
                itsLogger.debug(res);
            }
            if (null == reg)
                throw new IllegalArgumentException("Could not create it");
            return res;
        } else {
            String res = String.format("Changed the registered district from [%s] to [%s]", reg.getDistrict(), district);
            reg.setDistrict(district);
            NgoRegistrationUtility.update(reg.getPhonenumber(), reg);
            return res;

        }
    }

    // Render a TwiML document to give instructions for an outbound call
    public static Result smsAnswer() throws TwiMLException, TwilioRestException {
        TwiMLResponse tr = new TwiMLResponse();

        String smsResponse = null;
        try {
            smsResponse = processRequst();
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
            smsResponse = "Could not execute the request";
        }

        Sms sms = new Sms(smsResponse);

        response().setContentType("text/xml");
        tr.append(sms);
        return ok(tr.toXML());
    }

    static {
        try {
            SynchTriggerer.calllMeToTrigger();
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

}
