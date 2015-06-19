package query;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nhosgur on 8/10/14.
 */
public enum NgoCommand {
    Tutorial,T,
    Request,req,r,
    RegisterPhoneNumber,rn,rp,
    RegisterDistrict, rd,
    Harvest,h,
    Plant,p,
    Sell,s,
    ListDistricts,ld,l,
    ScheduleMeeting,sm,cm,
    cancelmeeting,dm,
    updatemeeting,
    rsvp,
    lrsvp,
    bc,bca;
    private static final Map<String,NgoCommand> m;
    static{
        m = new ConcurrentHashMap<>();
        for(NgoCommand v:values()){
            m.put(v.toString().toLowerCase(),v);
        }
    }

    public static NgoCommand fromString(String c){
        if(null==c||c.trim().isEmpty()||
                !m.containsKey(c.toLowerCase()))throw new IllegalArgumentException("Invalid Command");
        return m.get(c.toLowerCase());

    }

}
