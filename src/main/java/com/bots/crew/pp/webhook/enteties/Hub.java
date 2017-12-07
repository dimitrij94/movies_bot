package com.bots.crew.pp.webhook.enteties;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hub {
    private String mode;
    @JsonProperty("verification_token")
    private String verificationToken;
    private String challenge;

    public Hub() {
    }

    public Hub(String mode, String verificationToken, String challenge) {
        this.mode = mode;
        this.verificationToken = verificationToken;
        this.challenge = challenge;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hub hub = (Hub) o;

        if (mode != null ? !mode.equals(hub.mode) : hub.mode != null) return false;
        if (verificationToken != null ? !verificationToken.equals(hub.verificationToken) : hub.verificationToken != null)
            return false;
        return challenge != null ? challenge.equals(hub.challenge) : hub.challenge == null;
    }

    @Override
    public int hashCode() {
        int result = mode != null ? mode.hashCode() : 0;
        result = 31 * result + (verificationToken != null ? verificationToken.hashCode() : 0);
        result = 31 * result + (challenge != null ? challenge.hashCode() : 0);
        return result;
    }
}
