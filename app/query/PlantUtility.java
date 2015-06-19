package query;

import core.meta.ActionsOnItem;
import dbaccess.NgocropBuilder;
import dbaccess.dbojbects.dao.NgocropDao;
import dbaccess.dbojbects.pojo.Ngocrop;
import org.apache.log4j.Logger;
import play.mvc.Http;

import java.util.Map;

/**
 * Created by nhosgur on 1/5/15.
 */
public class PlantUtility {
    private static final Logger itsLogger = Logger.getLogger(PlantUtility.class);

    /**
     * Plant string.
     *
     * @param request the request
     * @return the string
     */
    public static String plant(final Http.Request request) {
        Map<String, String[]> qs = request.queryString();

        String body = qs.get("Body")[0].replace(" ", "").toLowerCase();

        String[] splitted = body.split(":");
        if (splitted.length < 2)
            return "District is empty";
        if ((splitted.length - 1) % 3 != 0)
            return "Make sure for each district to have one item and weigtht in Kg";

        String district = splitted[1];
        if (!DistrictUtility.isThereADistricteNamed(district))
            return "District[" + district + "]does not exist";


        int ui = (splitted.length - 1) / 3;
        NgocropDao dbi = DBIHandlerManager.getCropDAO();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ui; i++) {
            int st = 3 * i + 1;
            String cropName = splitted[st + 1];
            Double quantity = null;
            try {
                quantity = Double.valueOf(splitted[st + 2]);
            } catch (Throwable e) {
                itsLogger.error(e.getMessage(), e);
                sb.append("Did not update the district[" + district + "]since weight is invalid[" + splitted[st + 2]
                        + "]");
                continue;
            }
            if (quantity < 0) {
                sb.append("Did not update the district[" + district + "]with negative harvesting of[" + cropName + "]");
                continue;
            }
            Ngocrop.NgocropPK pk = new Ngocrop.NgocropPK(district, cropName, ActionsOnItem.Planted.toString());
            Ngocrop n = dbi.getById(pk);
            boolean u = false;
            if (null != n) {
                u = true;
                n.setQuantity(n.getQuantity() + quantity);
                dbi.update(new Ngocrop.NgocropPK(n.getDistrict(), n.getCropname(), n.getAttribute()), n);
            } else {
                dbi.create(new NgocropBuilder()
                        .setDistrict(district)
                        .setCropname(cropName)
                        .setAttribute(ActionsOnItem.Planted.toString())
                        .setQuantity(quantity)
                        .createNgocrop());
            }
            sb.append(String.format((u ? "Updated" : "Created") + " [%s] for the district of[%s]", cropName, district));

        }

        return sb.toString();
    }
}
