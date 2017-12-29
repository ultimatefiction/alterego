package org.veritas.alterego.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ChatStats {

    private Long chatId;
    private int pizdaChance;
    private int huiChance;
    private int pizdaCap;
    private int huiCap;
    private int recoveryRate;

    private LocalDate lastShipped;
    private String currentPairing;
    private ArrayList<String> memberList;

    public ChatStats(Long chatId, int huiCap, int pizdaCap, int recoveryRate) {
        this.chatId = chatId;
        this.huiCap = this.huiChance = huiCap;
        this.pizdaCap = this.pizdaChance = pizdaCap;
        this.recoveryRate = recoveryRate;
        this.lastShipped = LocalDate.now().minusDays(1);
        this.currentPairing = "None.";
        memberList = new ArrayList<String>();
    }

    public void update() {
        if (huiChance < huiCap) {
            huiChance += recoveryRate;
        }
        if (pizdaChance < pizdaCap) {
            pizdaChance += recoveryRate;
        }
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public int getPizdaChance() {
        return pizdaChance;
    }

    public void setPizdaChance(int pizdaChance) {
        this.pizdaChance = pizdaChance;
    }

    public int getHuiChance() {
        return huiChance;
    }

    public void setHuiChance(int huiChance) {
        this.huiChance = huiChance;
    }

    public LocalDate getLastShipped() {
        return lastShipped;
    }

    public void setLastShipped(LocalDate lastShipped) {
        this.lastShipped = lastShipped;
    }

    public String getCurrentPairing() {
        return currentPairing;
    }

    public void setCurrentPairing(String currentPairing) {
        this.currentPairing = currentPairing;
    }

    public boolean isRegistered(String username) {
        return memberList.contains(username);
    }

    public void addMember(String username) {
       if (!isRegistered(username)) {memberList.add(username);}
    }

    public void removeMember(String username) {
        memberList.remove(username);
    }

    public int getSize() {
        return memberList.size();
    }

    public ArrayList<String> getNRandomMembers(int n) {
        ArrayList<String> result = new ArrayList<String>();
        Collections.shuffle(memberList);
        for (int i=0; i<n; i++) {
            result.add(memberList.get(i));
        }
        return result;
    }

    public String getMembers() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String username : memberList) {
            stringBuilder.append(String.format("@%s\n",username));
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return String.format("Chat ID: %s\nHui chance: %s, Pizda chance: %s\n", chatId, huiChance, pizdaChance);
    }
}
