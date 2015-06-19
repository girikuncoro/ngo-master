package com.ngo.util;

/**
 * Created by nhosgur on 8/7/14.
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.meta.ActionsOnItem;
import core.meta.NGOColumn;
import core.meta.NGOTable;
import dbaccess.NgocropBuilder;
import dbaccess.NgoregistrationBuilder;
import dbaccess.dbojbects.pojo.Ngocrop;
import dbaccess.dbojbects.pojo.Ngodistrict;
import dbaccess.dbojbects.pojo.Ngoregistration;
import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static core.dbi.NGOConstants.*;
import static core.meta.NGOMetaInfo.*;


/**
 * The type Sample data gen.
 */
public class SampleDataGen {
    /**
     * The constant districts.
     */
    public static final String[] districts = {"sinjar", "barru", "pangkep", "maros", "takalar"};

    /**
     * The constant crops.
     */
    public static final String[] crops = {"eggplant", "banana", "onions"};

    /**
     * The constant phones.
     */
    public static final String[] phones = {"12345", "67890"};

    private static final Double[] quantities = {12.4D, 22.3D, 20D, 12D, 8D, 1D, 3D, 7D};
    private static final Map<String, SimpleResultSet> tnToRs;
    private static final Random rand = new Random();

    static {
        tnToRs = generateTableNameToRS();
        genererateSeedData();

    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String... args) {

    }

    /**
     * Genererate seed data.
     */
    public static void genererateSeedData() {

        try {
            genDistricts();
            genDistrictCropsSeedData();
            genRegistrationSeedData();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private static Map<String, SimpleResultSet> generateTableNameToRS() {
        Map<String, SimpleResultSet> m = new HashMap<>();
        Map<String, NGOTable> metaInfo = getTablesMetoInfo();
        for (String tn : metaInfo.keySet()) {
            m.put(tn, genFarmMetaData(metaInfo.get(tn)));
        }
        return m;
    }


    /**
     * Gen district crops seed data.
     *
     * @throws SQLException the sQL exception
     */
    public static void genDistrictCropsSeedData() throws SQLException {

        SimpleResultSet rs = tnToRs.get(NGO_CROP_TABLE_NAME);
        for (String district : districts) {
            for (String name : crops) {
                for (ActionsOnItem attribute : ActionsOnItem.values()) {
                    Ngocrop f = new NgocropBuilder()
                            .setDistrict(district)
                            .setCropname(name)
                            .setAttribute(attribute.toString())
                            .setQuantity(quantities[randInt(quantities.length - 1)])
                            .setCreatedate(getNow())
                            .setUpdatedate(getNow())
                            .createNgocrop();
                    addFarm(tnToRs.get(NGO_CROP_TABLE_NAME), f);
                }
            }
            Ngocrop f = new NgocropBuilder()
                    .setDistrict(district)
                    .setCropname(COMPOST)
                    .setAttribute(ActionsOnItem.Sold.toString())
                    .setQuantity(quantities[randInt(quantities.length - 1)])
                    .setUpdatedate(getNow())
                    .setCreatedate(getNow())
                    .createNgocrop();
            addFarm(rs, f);
        }
        String repLocation = ROOT + NGO_CROP_TABLE_NAME + CSV_EXTENXION;
        new Csv().write(repLocation, rs, null);
        rs.close();
    }

    /**
     * Gen districts.
     *
     * @throws SQLException the sQL exception
     */
    public static void genDistricts() throws SQLException {
        SimpleResultSet rs = tnToRs.get(NGO_DISTRICT_TABLE_NAME);

        for (String district : districts) {
            Ngodistrict reg = new Ngodistrict(district, getNow(), getNow());
            addDistrictRow(rs, reg);
        }


        String repLocation = ROOT + NGO_DISTRICT_TABLE_NAME + CSV_EXTENXION;
        new Csv().write(repLocation, rs, null);
        rs.close();

    }

    /**
     * Gen registration seed data.
     *
     * @throws Exception the exception
     */
    public static void genRegistrationSeedData() throws Exception {
        SimpleResultSet rs = tnToRs.get(NGOREGISTRATION_TABLE_NAME);
        Ngoregistration reg = new NgoregistrationBuilder()
                .setCreatedate(getNow())
                .setUpdatedate(getNow())
                .setPhonenumber("+1234567")
                .setDistrict(districts[0])
                .createNgoregistration();
        addRegistrationRow(rs, reg);
        String repLocation = ROOT + NGOREGISTRATION_TABLE_NAME + CSV_EXTENXION;
        new Csv().write(repLocation, rs, null);
        rs.close();
    }

    private static SimpleResultSet genFarmMetaData(NGOTable ngoTable) {
        SimpleResultSet rs = new SimpleResultSet();
        for (NGOColumn ci : ngoTable.getColumnList()) {
            rs.addColumn(ci.getColumnName(), ci.getCsvType(), 255, 0);
        }
        return rs;
    }

    private static void addDistrictRow(SimpleResultSet rs, Ngodistrict reg) {

        convertoJsonString(reg);
        rs.addRow(reg.getDistrict(),
                reg.getCreatedate(),
                reg.getUpdatedate());
    }

    private static void addRegistrationRow(SimpleResultSet rs, Ngoregistration reg) {
        convertoJsonString(reg);

        rs.addRow(reg.getPhonenumber(),
                reg.getDistrict(),
                reg.getUpdatedate(),
                reg.getCreatedate());
    }


    private static void addFarm(SimpleResultSet rs, Ngocrop f) {
        convertoJsonString(f);
        rs.addRow(f.getDistrict(),
                f.getCropname(),
                f.getAttribute(),
                f.getQuantity(),
                f.getCreatedate(),
                f.getUpdatedate());
    }

    private static void convertoJsonString(Object reg) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String res = mapper.writeValueAsString(reg);
            System.out.println(res);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets now.
     *
     * @return the now
     */
    public static Timestamp getNow() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new java.sql.Timestamp(now.getTime());
    }

    /**
     * Say hi.
     *
     * @param name the name
     * @return the string
     */
    public static String sayHi(String name) {
        return "Hi " + name;
    }



    /**
     * Rand int.
     *
     * @param max the max
     * @return the int
     */
    public static int randInt(int max) {
        return randInt(0, max);
    }

    /**
     * Rand int.
     *
     * @param min the min
     * @param max the max
     * @return the int
     */
    public static int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

}
