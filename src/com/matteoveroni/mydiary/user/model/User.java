package com.matteoveroni.mydiary.user.model;

/**
 *
 * @author Matteo Veroni
 */
public interface User {

    public String getUsername();

    public void setUsername(String username);

    public String getPassword();

    public void setPassword(String password);

    public String getName();

    public void setName(String name);

    public String getSurname();

    public void setSurname(String surname);

    public int getAge();

    public void setAge(int age);
    
    @Override
    public String toString();
}
