package com.example.appv1;

import com.google.firebase.auth.FirebaseUser;

public class User
{
    private FirebaseUser user;
    private Venue[] favoriteVenues;

    public User(FirebaseUser user)
    {
        this.user = user;
    }

    public FirebaseUser getUser()
    {
        return user;
    }

    public void setUser(FirebaseUser user)
    {
        this.user = user;
    }

    public Venue[] getFavoriteVenues()
    {
        return favoriteVenues;
    }

    public void setFavoriteVenues(Venue[] favoriteVenues)
    {
        this.favoriteVenues = favoriteVenues;
    }
}
