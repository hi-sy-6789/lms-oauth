package com.pranav.oauthresourceserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {

    //Setting expiration Time as 1 min from current time
    private static final int EXPIRATION_TIME=1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_Id", nullable = false,foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private User user;

    public VerificationToken(String token, User user){
        this.token=token;
        this.user=user;
        this.expirationTime=generateExpirationTime(EXPIRATION_TIME);
    }

    public VerificationToken(String token){
        this.token=token;
        this.expirationTime=generateExpirationTime(EXPIRATION_TIME);
    }

    private Date generateExpirationTime(int expirationTime) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
