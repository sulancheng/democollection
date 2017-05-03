package com.example.myokhttpgo.bean;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dolphin.tools.util.AppUtil;

/**
 * Created by 方奕峰 on 14-6-17.
 */
public class RequestEntity {
    public static Sysdata sysdata;
    private Object rqdata;

    public Sysdata getSysdata() {
        return sysdata;
    }

    public void setSysdata(Sysdata sysdata) {
        this.sysdata = sysdata;
    }

    public Object getRqdata() {
        return rqdata;
    }

    public void setRqdata(Object rqdata) {
        this.rqdata = rqdata;
    }

    public static class Sysdata {
        private String protocolType;
        private String appVer;
        private String deviceVer;
        private String blueVer;
        private String deviceId;
        private String mobileType;
        private String systemVer;
        private String systemLang;
        private String sendDate;
        private String timeZone;
        public static String manufCode = "001";

        public String getProtocolType() {
            return protocolType;
        }

        public void setProtocolType(String protocolType) {
            this.protocolType = protocolType;
        }

        public String getAppVer() {
            return appVer;
        }

        public void setAppVer(String appVer) {
            this.appVer = appVer;
        }

        public String getDeviceVer() {
            return deviceVer;
        }

        public void setDeviceVer(String deviceVer) {
            this.deviceVer = deviceVer;
        }

        public String getBlueVer() {
            return blueVer;
        }

        public void setBlueVer(String blueVer) {
            this.blueVer = blueVer;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getMobileType() {
            return mobileType;
        }

        public void setMobileType(String mobileType) {
            this.mobileType = mobileType;
        }

        public String getSystemVer() {
            return systemVer;
        }

        public void setSystemVer(String systemVer) {
            this.systemVer = systemVer;
        }

        public String getSystemLang() {
            return systemLang;
        }

        public void setSystemLang(String systemLang) {
            this.systemLang = systemLang;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }

        public String getManufCode() {
            return manufCode;
        }

        public void setManufCode(String manufCode) {
            this.manufCode = manufCode;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }
    }

    public static Sysdata generateSysData(Context context) {
        sysdata = new Sysdata();
        sysdata.setProtocolType("1");
        sysdata.setAppVer(AppUtil.getVerName(context));
//        //BtDev btDev = null;
//        try {
//            btDev = new BtDevServer(context).getBtDev(null,"");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        if (btDev != null) {
//            sysdata.setDeviceVer(btDev.getCoreVersion());
//            sysdata.setBlueVer(btDev.getNordicVersion());
//        }

        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        sysdata.setDeviceId(tm.getDeviceId());
        sysdata.setMobileType(android.os.Build.MODEL);
        sysdata.setSystemVer(android.os.Build.VERSION.RELEASE);
        sysdata.setTimeZone( Calendar.getInstance().getTimeZone().getID());
        Locale locale = context.getResources().getConfiguration().locale;
        locale.getLanguage();
        String language = "";//(Locale.CHINA.equals(locale) || Locale.CHINESE.equals(locale) ||
//                Locale.SIMPLIFIED_CHINESE.equals(locale) || Locale.TRADITIONAL_CHINESE.equals(locale)) ?
//                "zh" : "en";
        if(Locale.CHINA.equals(locale) || Locale.CHINESE.equals(locale) || Locale.SIMPLIFIED_CHINESE.equals(locale)
                || Locale.TRADITIONAL_CHINESE.equals(locale) || Locale.PRC.equals(locale) || Locale.TAIWAN.equals(locale)){
            language = "zh";
        }else if(Locale.ENGLISH.equals(locale) || Locale.UK.equals(locale) || Locale.US.equals(locale)){
            language = "en";
        }else if(Locale.FRANCE.equals(locale) || Locale.FRENCH.equals(locale) || Locale.CANADA_FRENCH.equals(locale)){
            language = "fr";
        }else if(Locale.GERMAN.equals(locale) || Locale.GERMANY.equals(locale)){
            language = "de";
        }else if(Locale.ITALIAN.equals(locale) || Locale.ITALY.equals(locale)){
            language = "it";
        }else if(Locale.JAPAN.equals(locale) || Locale.JAPANESE.equals(locale)){
            language = "jp";
        }else {
            language =  "en";
        }
        sysdata.setSystemLang(language);
        SimpleDateFormat timeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysdata.setSendDate(timeFormat1.format(new Date()));
        return sysdata;
    }
    
    
    public static Sysdata generateSysData(Context context,String ManufCode) {
//        sysdata = new Sysdata();
//        sysdata.setProtocolType("1");
//        sysdata.setAppVer(AppUtil.getVerName(context));
//        BtDev btDev = null;
//        try {
//            btDev = new BtDevServer(context).getBtDev(null);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        if (btDev != null) {
//            sysdata.setDeviceVer(btDev.getCoreVersion());
//            sysdata.setBlueVer(btDev.getNordicVersion());
//        }
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
//        sysdata.setDeviceId(tm.getDeviceId());
//        sysdata.setMobileType(android.os.Build.MODEL);
//        sysdata.setSystemVer(android.os.Build.VERSION.RELEASE);
//        Locale locale = context.getResources().getConfiguration().locale;
//        String language = (Locale.CHINA.equals(locale) || Locale.CHINESE.equals(locale) ||
//                Locale.SIMPLIFIED_CHINESE.equals(locale) || Locale.TRADITIONAL_CHINESE.equals(locale)) ?
//                "zh" : "en";
//        sysdata.setSystemLang(language);
//        sysdata.setSendDate(SystemContant.timeFormat1.format(new Date()));
//        sysdata.setManufCode(ManufCode);
//        return sysdata;

        return null;
    }
    
    public static int getBtDevMarksCode(Context context)
   	{
//       	try
//   		{
//   			String contentStr = new BtDevServer(context).getNordicVertion(null);
//   			if (contentStr == null)
//			{
//   				LogUtil.i("首次进入，数据库没有版本号数据，不修改厂商号，默认06");
//				return 0;
//			}
//   			contentStr.replace(',', '.');
//   			VersionServer.BtDevVersion btDevVersion = VersionServer.parseBtDevVer(contentStr);
//   			if (btDevVersion == null)
//			{
//   				LogUtil.i("首次进入，数据库没有版本号数据，不修改厂商号，默认06");
//				return 0;
//			}
//   			LogUtil.i("不是首次进入，数据库有版本号数据:" + btDevVersion.marksCode);
//   			return btDevVersion.marksCode;
//   		} catch (SQLException e)
//   		{
//   			return 0;
//   		}

        return 0;
   	}
}
