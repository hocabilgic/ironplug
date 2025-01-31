package com.ironplug.payload.messeges;

public class ErrorMessages {



    private  ErrorMessages(){}


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
