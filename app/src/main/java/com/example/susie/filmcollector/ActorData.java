package com.example.susie.filmcollector;

// this class is used to hold data retrieved from the actor table
public class ActorData {

    private int id;
    private String firstName;
    private String lastName;

    public ActorData() {
    }

    public ActorData(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
