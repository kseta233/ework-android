package com.ework.eduplex.utils;

/**
 * Created by eWork on 3/11/2016.
 */
public class Constant {

//    public static final String API_URL = "https://private-6eba5-eduplex.apiary-mock.com";
//    public static String API_URL = "http://eduplex.rf.gd";
//    public static String API_URL = "http://192.168.143.70";
    public static String API_URL = "http://10.10.11.114";
    public static final String FAQ_URL = API_URL+"/web/faq";
    public static final String TERMS_URL = API_URL+"/web/terms";
    public static final String SMS_VERIFICATION_SENDER_NUMBER = "eduplex";
//    public static final String CHAT_URL_TAG = API_URL + "/4eduplex/client/";

    public static final String SECRET_CLIENT_KEY = "e10adc3949ba59abbe56e057f20f883e";

    public static void overRideAPIURL(String ip){
        Constant.API_URL = "http://"+ip;
    }

    //DATABASE
    public static class Database {
        public static final int VERSION = 9;
        public static final String NAME = "kpm.db";
    }

    //LANGUAGE
    public static class Language {
        public static final String EN = "kpm";
        public static final String INA = "";
    }

    //FLAG
    public static class Flag {
        public static final String EDIT_FLAG = "1";
        public static boolean EN_LANGUAGE = false;
    }

    //FLAG
    public static class ResultActivityCode {
        public static final int RESULT_EDIT_PROFILE = 777;
        public static final int RESULT_GPS_SETTING = 111;
    }

    //TIMELINE FLAG KEYS
    public static class TimelinesFlag {
        public static final String SUBMISSION_FLAG = "SUBMISSION";
        public static final String DUE_DATE_FLAG = "DUE_DATE";
        public static final String KTUNAI_IN_FLAG = "TOPUP";
        public static final String KTUNAI_OUT_FLAG = "PAY";
        public static final String SUBMISSION_SUCCESS_FLAG = "SUBMISSION_SUCCESS";
        public static final String SUBMISSION_FAILED_FLAG = "SUBMISSION_FAILED";
    }

    //KATALOG
    public static class CategoryID {
        public static final String CATEGORY_ID_KEY = "category_id";
        public static final String BUNDLE_KEY = "category_bundle_key";
        public static final String CATEGORY_NAME_KEY = "category_name_key";
        public static final String CATEGORY_POSITION = "category_position";
        public static final String BARANG_CATEGORY_ID = "1";
    }

    //INTENT EXTRA KEY
    public static class IntentExtraKeys {
        public static final String INBOX_JSON = "inbox_json";
        public static final String KEY_LOGIN_RESPONSE = "login_response";
        public static final String KEY_SUMMARY_PROFILE = "summary_profile";
        public static final String KEY_PRODUCT_ID = "product_id";
        public static final String KEY_PRODUCT_OBJECT = "product_object";

        public static final String KEY_IS_PROMO_PRODUCT = "is_promo_product";

        //SEARCH KEY
        public static final String SEARCH_KEY = "search_key";

        //SECRET
        public static final String SECRET_TOPUP = "secret_top_up";
        public static final String PAIR_ID_KEY = "pair_id_key";

        //ip change
        public static final String IP_CHANGES_FLAG = "IP_CHANGES_FLAG";

    }


    //SHAREDPREFERENCES
    public static class SharedPrefs {
        public static final String PREFS_NAME = "my_prefs";
        public static final String KEY_CURRENT_TOKEN = "current_token";
        public static final String KEY_LOGIN_RESPONSE = "login_response";
        public static final String KEY_SUMMARY_PROFILE = "summary_profile";
        public static final String KEY_USER_EMAIL = "user_email";
        public static final String KEY_USER_VERIF_STATE = "user_verif_state";
        public static final String KEY_VOUCHER = "voucher_eduplex_key";
        public static final String KEY_EDU_TABLE_ID = "edu_table_id";
        public static final String KEY_EDU_TABLE_AVAIBLE = "edu_table_avaible";

    }

    //RESPONSE CODES
    public static class ResponseCode {
        public static final String CODE_200 = "200";
    }

    //RESPONSE CODES
    public static class UserPrefs {
        public static final String LAST_USED_EMAIL = "last_used_email";
    }

    //APP SETTINGS
    public static class AppSetting {
        public static final String RELOGIN = "relogin_setting";

        public static final String LANGUAGE_SETTING = "language_setting";
        public static final String LANGUAGE_INA = "language_ina";
        public static final String LANGUAGE_EN = "language_en";
        public static final String LANGUAGE_CHECK = "language_check";
    }

    //SAVED FORM FIELDS
    public static class SavedFormFields {

        //PENGAJUAN KREDIT
        public static final String PK_JENIS_PEKERJAAN = "PK_JENIS_PEKERJAAN";
        public static final String PK_NAMA_PERUSAHAAN = "PK_NAMA_PERUSAHAAN";
        public static final String PK_PROVINCE_KANTOR = "PK_PROVINCE_KANTOR";
        public static final String PK_CITY_KANTOR = "PK_CITY_KANTOR";
        public static final String PK_KECAMATAN_KANTOR = "PK_KECAMATAN_KANTOR";
        public static final String PK_KELURAHAN_KANTOR = "PK_KELURAHAN_KANTOR";
        public static final String PK_ALAMAT_PERUSAHAAN = "PK_ALAMAT_PERUSAHAAN";
        public static final String PK_TELP_KANTOR = "PK_TELP_KANTOR";

        public static final String PK_NAMA_IBU = "PK_NAMA_IBU";
        public static final String PK_NAMA_KONTAK_DARURAT = "PK_NAMA_KONTAK_DARURAT";
        public static final String PK_HUBUNGAN_KONTAK_DARURAT = "PK_HUBUNGAN_KONTAK_DARURAT";
        public static final String PK_PROVINCE_KONTAK_DARURAT = "PK_PROVINCE_KONTAK_DARURAT";
        public static final String PK_CITY_KONTAK_DARURAT = "PK_CITY_KONTAK_DARURAT";
        public static final String PK_KECAMATAN_KONTAK_DARURAT = "PK_KECAMATAN_KONTAK_DARURAT";
        public static final String PK_KELURAHAN_KONTAK_DARURAT = "PK_KELURAHAN_KONTAK_DARURAT";
        public static final String PK_ALAMAT_KONTAK_DARURAT = "PK_ALAMAT_KONTAK_DARURAT";
        public static final String PK_NOMOR_KONTAK_DARURAT = "PK_NOMOR_KONTAK_DARURAT";

        //FORM AGUNAN
        public static final String FA_TIPE_ASET = "FA_TIPE_ASET";
        public static final String FA_MEREK = "FA_MEREK";
        public static final String FA_DESKRIPSI = "FA_DESKRIPSI";
        public static final String FA_TAHUN = "FA_TAHUN";
        public static final String FA_NILAI_KEBUTUHAN = "FA_NILAI_KEBUTUHAN";
        public static final String FA_LAINNYA = "FA_LAINNYA";

        //FORM KMOB
        public static final String FKMOB_TIPE_MOBIL = "FKMOB_TIPE_MOBIL";
        public static final String FKMOB_PABRIKAN = "FKMOB_PABRIKAN";
        public static final String FKMOB_TAHUN = "FKMOB_TAHUN";
        public static final String FKMOB_NPWP = "FKMOB_NPWP";

        //FORM MOBIL
        public static final String FM_TIPE_MOBIL = "FKMOB_TIPE_MOBIL";
        public static final String FM_PABRIKAN = "FKMOB_PABRIKAN";
        public static final String FM_TAHUN = "FKMOB_TAHUN";

        //FORM MOBIL NON ASSET
        public static final String FMNA_TIPE_MOBIL = "FKMOB_TIPE_MOBIL";
        public static final String FMNA_PABRIKAN = "FKMOB_PABRIKAN";
        public static final String FMNA_TAHUN = "FKMOB_TAHUN";

        //FORM MOTOR
        public static final String FMTR_PEKERJAAN = "FMTR_PEKERJAAN";
        public static final String FMTR_PEMILIK_ASET = "FMTR_PEMILIK_ASET";
        public static final String FMTR_TAHUN = "FMTR_TAHUN";
        public static final String FMTR_MEREK = "FMTR_MEREK";

        //FORM MOTOR NON ASET
        public static final String FMTRNA_PEKERJAAN = "FMTR_PEKERJAAN";
        public static final String FMTRNA_PEMILIK_ASET = "FMTR_PEMILIK_ASET";
        public static final String FMTRNA_TAHUN = "FMTR_TAHUN";
        public static final String FMTRNA_MEREK = "FMTR_MEREK";

        //FORM KPR
        public static final String KPR_NILAI_KEBUTUHAN = "KPR_NILAI_KEBUTUHAN";
        public static final String KPR_TENOR = "KPR_TENOR";

        //FORM KPR PROPERTY 1
        public static final String KPR1_PENJUAL_PROPERTY = "KPR1_PENJUAL_PROPERTY";
        public static final String KPR1_NAMA_PENJUAL = "KPR1_NAMA_PENJUAL";
        public static final String KPR1_TELP_PENJUAL = "KPR1_TELP_PENJUAL";
        public static final String KPR1_TIPE_PROPERTI = "KPR1_TIPE_PROPERTI";
        public static final String KPR1_TUJUAN_PEMBIAYAAN = "KPR1_TUJUAN_PEMBIAYAAN";
        public static final String KPR1_NAMA_PERUMAHAN = "KPR1_NAMA_PERUMAHAN";
        public static final String KPR1_KODE_POS = "KPR1_KODE_POS";
        public static final String KPR1_PROVINCE = "KPR1_PROVINCE";
        public static final String KPR1_CITY = "KPR1_CITY";
        public static final String KPR1_KECAMATAN = "KPR1_KECAMATAN";
        public static final String KPR1_KELURAHAN = "KPR1_KELURAHAN";

        //FORM KPR PROPERTY 2
        public static final String KPR2_LTLB = "KPR2_LTLB";
        public static final String KPR2_STATUS_TANAH = "KPR2_STATUS_TANAH";
        public static final String KPR2_HARGA_PROPERTI = "KPR2_HARGA_PROPERTI";
        public static final String KPR2_UANG_MUKA = "KPR2_UANG_MUKA";
        public static final String KPR2_JUMLAH_KREDIT = "KPR2_JUMLAH_KREDIT";
    }
}
