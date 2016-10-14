package com.thorsteinnth.kth.mmse.sep.cli.Domain;

/**
 * Created by tts on 14/10/2016.
 */
public class User
{
    public String Email;
    public String Password;

    public User(String email, String password)
    {
        this.Email = email;
        this.Password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
