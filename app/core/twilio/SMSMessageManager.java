package core.twilio;

import com.google.common.util.concurrent.*;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import config.NGOConfig;
import controllers.DistrictNamesWithMessage;
import dbaccess.dbojbects.pojo.NgoMeeting;
import dbaccess.dbojbects.pojo.NgoRSVP;
import dbaccess.dbojbects.pojo.Ngoregistration;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import play.libs.Json;
import query.NgoRegistrationUtility;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by nhosgur on 1/6/15.
 */
public class SMSMessageManager {
    private static Logger LOGGER = Logger.getLogger(SMSMessageManager.class);
    /**
     * The constant TWILIO_ACCOUNT_SID.
     */
    public static String TWILIO_ACCOUNT_SID = NGOConfig.TWILIO_ACCOUNT_SID();
    /**
     * The constant TWILIO_AUTH_TOKEN.
     */
    public static String TWILIO_AUTH_TOKEN = NGOConfig.TWILIO_AUTH_TOKEN();
    /**
     * The constant TWILIO_NUMBER.
     */
    public static String TWILIO_NUMBER = NGOConfig.TWILIO_NUMBER();
    private static final ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    /**
     * Handle new meeting.
     *
     * @param m the m
     */
    public static void handleNewMeeting(final NgoMeeting m) {
        Iterator<Ngoregistration> it = NgoRegistrationUtility.getByDistrict(m.getDistrict());
        long milliseconds = m.getMeetingdate().getTime() + (m.getMeetingdate().getNanos() / 1000000);
        Date date = new Date(milliseconds);
        final String message = "You are invited to a meeting for district [" + m.getDistrict() + "] with subject [" + m.getNote() + "] on [" + date +
                "]. Please RSVP by sending \"RSVP:" + m.getMeetingid() + ":Yes/No/Tentative\"";

        while (it.hasNext()) {
            final Ngoregistration n = it.next();
            sendSMSMessageTo(message, n.getPhonenumber());
        }


    }

    /**
     * Send message to district.
     *
     * @param districtNames the district names
     * @param message       the message
     */
    public static void sendMessageToDistrict(final Set<String> districtNames, final String message) {

        String[] sa = new String[districtNames.size()];
        int i=0;
        for(String s:districtNames){
            sa[i]= s;
        }
        System.out.println(Json.toJson(new DistrictNamesWithMessage(sa,message)).toString());
        Iterator<String> it = districtNames.iterator();
        while (it.hasNext()) {
            sendMessageToDistrict(it.next(), message);
        }
    }



    /**
     * Send message to district.
     *
     * @param districtName the district name
     * @param message      the message
     */
    public static void sendMessageToDistrict(final String districtName, final String message) {
        Iterator<Ngoregistration> it = NgoRegistrationUtility.getByDistrict(districtName);
        while (it.hasNext()) {
            final Ngoregistration n = it.next();
            sendSMSMessageTo(message, n.getPhonenumber());
        }
    }

    /**
     * Send message to all.
     *
     * @param message the message
     */
    public static void sendMessageToAll(final String message) {
        Iterator<Ngoregistration> it = NgoRegistrationUtility.getAll();
        while (it.hasNext()) {
            final Ngoregistration n = it.next();
            sendSMSMessageTo(message, n.getPhonenumber());
        }
    }

    /**
     * Send message to given numbers.
     *
     * @param message the message
     * @param numbers the numbers
     */
    public static void sendMessageToGivenNumbers(final String message, List<String> numbers) {
        Iterator<String> it = numbers.iterator();
        while (it.hasNext()) {
            sendSMSMessageTo(message, it.next());
        }
    }

    /**
     * Send sMS message to.
     *
     * @param message the message
     * @param number  the number
     */
    public static void sendSMSMessageTo(final String message, final String number) {

        ListenableFuture<Message> smsSender = service.submit(new Callable<Message>() {
            public Message call() throws TwilioRestException {
                return smsToTheMessageOf(number, message);
            }
        });

        Futures.addCallback(smsSender, new FutureCallback<Message>() {
            // we want this handler to run immediately after we push the big red button!
            public void onSuccess(Message sentMessage) {

            }

            public void onFailure(Throwable e) {
                LOGGER.error("Failed to send SMS message [ " + message + "] due to: " + e.getMessage(), e);
            }
        });


    }

    public static void handleAck(final NgoRSVP rsvp, final NgoMeeting meeting) throws TwilioRestException {
        if(null==rsvp || null==meeting){
            throw new IllegalArgumentException("Neither rsvp nor meeting can be null");
        }
        long milliseconds = meeting.getMeetingdate().getTime() + (meeting.getMeetingdate().getNanos() / 1000000);
        Date date = new Date(milliseconds);
        String message = "RSVP ["+rsvp.getRsvresponse()+"] from ["+rsvp.getRsvnumber()+"] for the meeting ["+meeting.getNote()+
                "] on ["+date+"] for district ["+meeting.getDistrict()+"]for Meeting Id["+meeting.getMeetingid()+"]";
        smsToTheMessageOf(meeting.getCreatedby(), message);
    }

    private static Message smsToTheMessageOf(final String number, final String message) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Account account = client.getAccount();

        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("To", number)); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", TWILIO_NUMBER)); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("Body", message));
        return messageFactory.create(params);
    }


    public static void meetingDelete(NgoMeeting m) {
        Iterator<Ngoregistration> it = NgoRegistrationUtility.getByDistrict(m.getDistrict());
        long milliseconds = m.getMeetingdate().getTime() + (m.getMeetingdate().getNanos() / 1000000);
        Date date = new Date(milliseconds);
        final String message = "Canceled the meeting for the district of[" + m.getDistrict() + "] with subject [" + m.getNote() + "] on [" + date +
                "]for the meeting id of[" + m.getMeetingid() + "]";

        while (it.hasNext()) {
            final Ngoregistration n = it.next();
            sendSMSMessageTo(message, n.getPhonenumber());
        }
    }
}
