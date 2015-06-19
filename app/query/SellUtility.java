package query;

import query.DistrictUtility;
import core.meta.ActionsOnItem;
import dbaccess.NgocropBuilder;
import dbaccess.dbojbects.dao.NgocropDao;
import dbaccess.dbojbects.pojo.Ngocrop;
import play.mvc.Http;
import query.DBIHandlerManager;

import java.util.Map;

/**
 * Created by nhosgur on 1/5/15.
 */
public class SellUtility {

    /**
     * Sell string.
     *
     * @param request the request
     * @return the string
     */
    public static String sell(final Http.Request request) {
        Map<String, String[]> qs = request.queryString();

        String body = qs.get("Body")[0].replace(" ", "").toLowerCase();

        String[] splitted = body.split(":");
        if (splitted.length < 2)
            return "District is empty";
        if ((splitted.length - 1) % 3 != 0)
            return "Make sure for each district to have one item and weight in Kg";

        String district = splitted[1];
        if (!DistrictUtility.isThereADistricteNamed(district))
            return "District[" + district + "]does not exist";


        int ui = (splitted.length - 1) / 3;
        NgocropDao dbi = DBIHandlerManager.getCropDAO();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ui; i++) {
            int st = 3 * i + 1;
            String cropName = splitted[st + 1];
            Double quantity = Double.valueOf(splitted[st + 2]);
            if (quantity < 0) {
                sb.append("Did not update the district[" + district + "]with negative planting of[" + cropName + "]");
                continue;
            }
            Ngocrop.NgocropPK pk = new Ngocrop.NgocropPK(district, cropName, ActionsOnItem.Harvested.toString());
            if (null == pk) {
                sb.append(
                        "Did not update the district[" + district + "]with negative harvesting of[" + cropName + "] " +
                                "since there is no any available crops");
                continue;
            }
            Ngocrop n = dbi.getById(pk);

            if (null != n) {
                if (n.getQuantity() < quantity) {
                    return sb.append("There is only [" + n.getQuantity() + "] of crop " +
                            "so can not [" + quantity
                            + "]sell more than that.Please fix it and make another request").toString();
                } else if (n.getQuantity().equals(quantity)) {
                    dbi.delete(pk);
                    sb.append("Removed all").append("[").append(n.getCropname())
                            .append("]from the harvest on district of[")
                            .append(district).append("]");
                    updateSold(dbi, district, cropName, quantity);

                } else {
                    n.setQuantity(n.getQuantity() - quantity);
                    dbi.update(new Ngocrop.NgocropPK(n.getDistrict(), n.getCropname(), n.getAttribute()), n);
                    sb.append(String.format("[%s] is updated for the district of[%s]", cropName, district));
                    updateSold(dbi, district, cropName, quantity);
                }
            } else {
                sb.append(
                        "District of[" + district + "]does not have any[" + cropName + "]harvested so can not sell it");
            }
        }

        return sb.toString();

    }

    private static void updateSold(final NgocropDao dbi, final String district, final String cropName,
            final Double quantity) {

        Ngocrop.NgocropPK pk = new Ngocrop.NgocropPK(district, cropName, ActionsOnItem.Sold.toString());
        Ngocrop n = dbi.getById(pk);
        if (null != n) {
            n.setQuantity(n.getQuantity() + quantity);
            dbi.update(new Ngocrop.NgocropPK(n.getDistrict(), n.getCropname(), n.getAttribute()), n);
        } else {
            dbi.create(new NgocropBuilder()
                    .setDistrict(district)
                    .setCropname(cropName)
                    .setAttribute(ActionsOnItem.Sold.toString())
                    .setQuantity(quantity)
                    .createNgocrop());
        }

    }
}
