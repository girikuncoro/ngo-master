package controllers;

import com.google.common.base.Strings;
import core.twilio.NGOAuthenticator;
import core.twilio.SMSMessageManager;
import dbaccess.dbojbects.pojo.*;
import org.apache.log4j.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import query.*;
import synchup.SynchTriggerer;

import java.text.ParseException;
import java.util.*;

import static play.data.Form.form;

/**
 * Created by nhosgur on 1/6/15.
 */
public class NGOUIApp extends Controller {
    private static final Logger itsLogger = Logger.getLogger(NGOUIApp.class);

    /**
     * Gets district.
     *
     * @param districtName the district name
     * @return the district
     */
    public static Result getDistrict(final String districtName) {
        Ngodistrict district = DistrictUtility.fetchDistrictByName(districtName);
        if (null == district) {
            return Results.notFound();
        }

        return ok(Json.toJson(DistrictUtility.fetchDistrictByName(districtName)));
    }

    /**
     * Gets districts.
     *
     * @return the districts
     */
    public static Result getDistricts() {
        Iterator<Ngodistrict> it = DistrictUtility.getDistricts();
        if (null == it || !it.hasNext()) {
            return ok();
        }

        List<Ngodistrict> l = new ArrayList<>();
        while (it.hasNext()) {
            l.add(it.next());
        }
        return ok(Json.toJson(Json.toJson(l)));
    }

    /**
     * Gets registrations.
     *
     * @return the registrations
     */
    public static Result getRegistrations() {
        Iterator<Ngoregistration> it = NgoRegistrationUtility.getAll();
        if (null == it || !it.hasNext()) {
            return ok();
        }

        List<Ngoregistration> l = new ArrayList<>();
        while (it.hasNext()) {
            l.add(it.next());
        }
        return ok(Json.toJson(Json.toJson(l)));
    }

    /**
     * Gets registrations by district.
     *
     * @param districtName the district name
     * @return the registrations by district
     */
    public static Result getRegistrationsByDistrict(final String districtName) {
        Iterator<Ngoregistration> it = NgoRegistrationUtility.getByDistrict(districtName);
        if (null == it || !it.hasNext()) {
            return ok();
        }

        List<Ngoregistration> l = new ArrayList<>();
        while (it.hasNext()) {
            l.add(it.next());
        }
        return ok(Json.toJson(Json.toJson(l)));
    }

    /**
     * Gets district crops.
     *
     * @param districtName the district name
     * @return the district crops
     */
    public static Result getDistrictCrops(final String districtName) {
        Iterator<Ngocrop> it = NgoCropUtility.getByDistrict(districtName);
        if (null == it || !it.hasNext()) {
            return ok();
        }

        List<Ngocrop> l = new ArrayList<>();
        while (it.hasNext()) {
            l.add(it.next());
        }
        return ok(Json.toJson(Json.toJson(l)));
    }

    /**
     * Gets crops.
     *
     * @return the crops
     */
    public static Result getCrops() {
        Iterator<Ngocrop> it = NgoCropUtility.getAll();
        if (null == it || !it.hasNext()) {
            return ok();
        }

        List<Ngocrop> l = new ArrayList<>();
        while (it.hasNext()) {
            l.add(it.next());
        }
        return ok(Json.toJson(Json.toJson(l)));
    }

    /**
     * Gets meetings by creator.
     *
     * @param creatorPhoneNumber the creator phone number
     * @return the meetings by creator
     * @throws ParseException the parse exception
     */
    public static Result getMeetingsByCreator(final String creatorPhoneNumber) throws ParseException {
        Iterator<NgoMeeting> it = MeetingUtility.getMeetingsByCreator(creatorPhoneNumber);
        return getResult(it);

    }

    /**
     * Gets all the meetings.
     *
     * @return the all the meetings
     * @throws ParseException the parse exception
     */
    public static Result getAllTheMeetings() throws ParseException {
        Iterator<NgoMeeting> it = MeetingUtility.getAllMeetings();
        return getResult(it);
    }

    private static Result getResult(Iterator<NgoMeeting> it) {
        if (null == it || !it.hasNext()) {
            return ok();
        }

        List<NgoMeeting> l = new ArrayList<>();
        while (it.hasNext()) {
            l.add(it.next());
        }
        return ok(Json.toJson(Json.toJson(l)));
    }


    private static boolean authenticateTwilioAccount() {
        DynamicForm requestData = form().bindFromRequest();
        String accountSid = requestData.get("accountSid");
        String accountToken = requestData.get("accountToken");

        if (Strings.isNullOrEmpty(accountSid) || Strings.isNullOrEmpty(accountToken)) {
            return false;
        }
        if (Objects.equals(NgoTwilioApp.TWILIO_ACCOUNT_SID, accountSid) &&
                Objects.equals(NgoTwilioApp.TWILIO_AUTH_TOKEN, accountToken)) {
            return true;
        }
        return false;
    }


    /**
     * Create user.
     *
     * @return the result
     * @throws ParseException the parse exception
     */
    public static Result createUser() throws ParseException {
        if (!authenticateTwilioAccount()) {
            return Results.unauthorized();
        }
        DynamicForm requestData = form().bindFromRequest();
        String emailAddress = requestData.get("emailAddress");
        String password = requestData.get("password");
        String userName = requestData.get("userName");
        String phoneNumber = requestData.get("phoneNumber");
        String name = requestData.get("name");
        String lastName = requestData.get("lastName");

        NgoAccount account = new NgoAccount();
        account.setEmailAddress(emailAddress);
        account.setUsername(userName);
        account.setPhoneNumber(phoneNumber);
        account.setFirstName(name);
        account.setLastName(lastName);

        NGOAuthenticator.createDigestAndSaltForTheAccount(password, account);
        NgoAccountUtility.createNgoAccount(account);
        return Results.ok();
    }

    /**
     * Send message to districts.
     *
     * @return the result
     */
    public static Result sendMessageToDistricts() {
        try {
            Form<DistrictNamesWithMessage> dmf = form(DistrictNamesWithMessage.class).bindFromRequest();

            if (dmf.hasErrors()) {
                return Results.badRequest("DistrictNamesWithMessage Object has errors");
            }

            DistrictNamesWithMessage mo = dmf.get();
            if(null==mo.getDistricts()||mo.getDistricts().length==0||Strings.isNullOrEmpty(mo.getMessage())){
                return Results.badRequest("Neither districts nor message may be set to null");
            }
            String message = mo.getMessage();
            Set<String> hs = new HashSet<>();
            for (String d : mo.getDistricts()) {
                hs.add(d);
            }
            SMSMessageManager.sendMessageToDistrict(hs, message);
            return ok("Triggered to send the messages");
        } catch (Throwable e) {
            return internalServerError(e.getMessage());
        }

    }

    /**
     * Authenticate result.
     *
     * @return the result
     * @throws ParseException the parse exception
     */
    public static Result authenticate() throws ParseException {
        Form<NgoAccount> loginForm = form(NgoAccount.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return Results.unauthorized("{authorized:false}");
        } else {
            session().clear();
            session("email", loginForm.get().getPhoneNumber());
            NgoAccount account = NgoAccountUtility.getNgoAccountByEmail(loginForm.get().getEmailAddress());
            if (null == account) {
                return Results.unauthorized("{authorized:false}");
            }
            return redirect(
                    routes.Application.index()
            );
        }
    }

    /**
     * Authenticate user.
     *
     * @return the result
     * @throws ParseException the parse exception
     */
    public static Result authenticateUser() throws ParseException {
        DynamicForm requestData = form().bindFromRequest();
        String emailAddress = requestData.get("emailAddress");
        String password = requestData.get("password");

        if (Strings.isNullOrEmpty(emailAddress) || Strings.isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Neither user name nor password may be null");
        }
        NgoAccount account = NgoAccountUtility.getNgoAccountByEmail(emailAddress);
        if (null == account) {
            return Results.unauthorized();
        }

        if (NGOAuthenticator.authenticateByPassword(account, password)) {
            return Results.ok(account.getPhoneNumber());
        }

        return Results.unauthorized();
    }
    /**
     * Create meeting.
     *
     * @return the result
     * @throws ParseException the parse exception
     */
    public static Result createMeeting() throws ParseException {
        try {
            DynamicForm requestData = form().bindFromRequest();
            String note = requestData.get("note");
            String createdby = requestData.get("createdby");
            String district = requestData.get("district");
            String meetingdate = requestData.get("meetingdate");
            String duration = requestData.get("duration");

            return ok(MeetingSchedulerUtility.createTheMeeting(district, meetingdate, note, createdby));
        } catch (Throwable e) {
            return internalServerError(e.getMessage());
        }
    }

    /**
     * Delete meetings by creator.
     *
     * @return the result
     */
    public static Result deleteMeetingsByCreator() {
        try {
            DynamicForm requestData = form().bindFromRequest();
            String meetingId = requestData.get("meetingid");
            String ownerPhoneNumber = requestData.get("createdby");

            return ok(MeetingSchedulerUtility.deleteMeeting(meetingId, ownerPhoneNumber));
        } catch (Throwable e) {
            return internalServerError(e.getMessage());
        }
    }

    /**
     * Fetch meeting rSV ps.
     *
     * @param meetingId the meeting id
     * @return the result
     */
    public static Result fetchMeetingRSVPs(final String meetingId) {
        try {
            return ok(RSVPUtility.fetchTheRSVPs(meetingId));
        } catch (Throwable e) {
            return internalServerError(e.getMessage());
        }
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
