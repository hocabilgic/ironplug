package com.ironplug.payload.messeges;

public class ErrorMessages {


    ;

    private  ErrorMessages(){}
    public static final String TOP_BIL_BULUNAMADI = "Toplantı Bilgileri Bulunamadı";

    public static final String USER_BULUNAMADI =  "kullanıcı bilgileri  bulunamadı";

    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error : User with email %s is already registered";

    public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with id %s";

    public static final String NOT_EMAIL= "E mail adresi boş ";
    public static final String RANDEVU_BULUNAMADI = "Belirtilen tarihte randevu bulunamadı.";
    public static final String TARIH_HATASI = "EndDate, startDate'den önce olamaz.";

    public static final String YETKISIZ_GIRIS="Bu toplantı bilgilerine erişim yetkiniz yok.";

    public static final String RESET_CODE_EXPIRED = "Şifre sıfırlama kodunun süresi dolmuş.";
    public static final String MAX_ATTEMPTS_REACHED = "Maksimum deneme sayısına ulaşıldı.";
    public static final String INCORRECT_RESET_CODE = "Yanlış şifre sıfırlama kodu.";




    public static final String BITIS_SURESI ="Bitiş tarihi, en fazla 13 ay ileri olmalıdır." ;
    public static final String KULLANICIYA_AIT_KAYIT_YOK = "Bu kullanıcıya ait herhangi bir kayıt bulunamadı.";
    public static final String KAYIT_BULUNAMADI = "Kayıt bulunamadı.";
    public static final String KAYIT_BULUNAMADI_ID = "ID ile eşleşen kayıt bulunamadı: %s";

    public static final String BILDIRIM_SURESI_HATASI = "Bildirim süresinden az bir süre kaldı. İşlem gerçekleştirilemez.";
    public static final String RANDEVU_ALREADY_INACTIVE = "Toplantı zaten pasif durumda. İşlem gerçekleştirilemez.";
    public static final String SORU_NOT_FOUND = "Soru bulunamadı: %s";
    public static final String EMAIL_CANNOT_BE_NULL = "E-posta adresi boş olamaz.";
    public static final String USER_NOT_FOUND = "User not found with %s : %s";
    public static final String TOPLANTI_BILGILERI_NOT_FOUND = "Meeting information not found with %s : %s";
    public static final String RANDEVU_NOT_FOUND = "Appointment not found with %s : %s";
    public static final String GECMIS_TARIH_HATASI = "Start date cannot be a past date.";





}
