package com.example.slot;

public class SignUpdata {

    String name ;
    String email ;

    String phonenumber;

    public SignUpdata(String name, String email,  String phonenumber) {
        this.name = name;
        this.email = email;

        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }



    public String getPhonenumber() {
        return phonenumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
