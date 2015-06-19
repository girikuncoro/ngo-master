package query;

import dbaccess.dbojbects.dao.NgoMeetingDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.mvc.Http;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * Created by nhosgur on 1/7/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class MeetingSchedulerUtilityTest {
    @Mock
    private Http.Request request;
    @Mock
    private NgoMeetingDao dbi;

    @Test
    public void test() throws ParseException {
       Map<String,String[]> qs = new HashMap<>();
       qs.put("Body",new String[]{"sm:Jan 2,2015,18:'We kind of rock'"});
       qs.put("Form",new String[]{"+6504685114"});
       when(request.queryString()).thenReturn(qs);
       MeetingSchedulerUtility.schedule(request,dbi);

    }

}
