package com.ironplug.payload.messeges;

public class ErrorMessages {




    private  ErrorMessages(){}


    public static final String TITLE_BOS = "title bos olanaaz";
    public static final String IMAGE_BOS = "image bos";
    public static final String IMAGE_VEYA_TITLE_BOS = "image veya title bos";
    public static final String KONTROL_BOS="kontrol sekmeleri bos olamaz";
    public static final String IMAGE_NOT_FOUND_MESSAGE ="image bulunamadi" ;
    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation";

    public static final String PASSWORD_DONT_CONSIST_DIGIT = "Your passwords must have a digit at least";
    public static final String PASSWORD_DONT_CONSIST_LETTER = "Your passwords must have a letter at least";
    public static final String PASSWORD_DONT_CONSIST_SPECIAL_CHAR = "Your passwords must have a special char at least";
    public static final String PASSWORD_NOT_MATCH = "Error:password doesnt match confirm password";
    public static final String OLD_PASSWORD_NOT_MATCH = "Error:Old password doesnt match";

    public static final String RESET_CODE_NOT_MATCH = "Error:Reset code is wrong";
    public static final String PASSWORD_LENGTH = "Your passwords must be 8 characters at least";
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error : User with email %s is already registered";

    public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";

    public static final String EMAIL_NOT_FOUND = "Email not found";

    public static final String RESET_CODE_EXPIRED = "Şifre sıfırlama kodunun süresi dolmuş.";
    public static final String MAX_ATTEMPTS_REACHED = "Maksimum deneme sayısına ulaşıldı.";
    public static final String INCORRECT_RESET_CODE = "Yanlış şifre sıfırlama kodu.";

    public static final String EMAIL_CANNOT_BE_NULL = "E-posta adresi boş olamaz.";
    public static final String USER_NOT_FOUND = "User not found with %s : %s";
    public static final String ONLY_TITLE_OWNER_CAN_PERFORM_THIS_ACTION = "This action can only be performed by the title owner.";
    public static final String TITLE_NOT_FOUND = "Title with ID %s not found.";
    public static final String DELETE_SUCCESSFUL = "Deletion successful.";
    public static final String ERROR_OCCURRED = "An error occurred: ";

    public static final String CONTENTS_NOT_FOUND = "Contents with ID %s not found.";






}
