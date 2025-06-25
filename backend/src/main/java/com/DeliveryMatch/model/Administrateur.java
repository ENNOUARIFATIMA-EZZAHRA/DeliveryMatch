package com.DeliveryMatch.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMINISTRATEUR")
public class Administrateur extends User {

}

