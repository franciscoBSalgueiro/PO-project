package prr.clients;

import java.io.Serializable;

import prr.communications.Communication;
import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.plans.PaymentPlan;

public abstract class ClientType implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
    private Client _client;
    private int _streakText = 0;
    private int _streakVideo = 0;
    private PaymentPlan _plan;

    public ClientType(Client client) {
        _client = client;
    }

    private int calculateCost(int cost, Communication comm) {
        if (comm.getOrigin().isFriend(comm.getDestination())) {
            cost *= 0.5;
        }
        return cost;
    }

    public int getTextCost(TextCommunication communication) {
        return calculateCost(_plan.getTextCost(communication), communication);
    }

    public int getVoiceCost(VoiceCommunication communication) {
        return calculateCost(_plan.getVoiceCost(communication), communication);
    }

    public int getVideoCost(VideoCommunication communication) {
        return calculateCost(_plan.getVideoCost(communication), communication);
    }

    public Client getClient() {
        return _client;
    }

    public void setPaymentPlan(PaymentPlan plan) {
        _plan = plan;
    }

    public void addText() {
        _streakText++;
        _streakVideo = 0;
        updateType();
    }

    public void addVideo() {
        _streakVideo++;
        _streakText = 0;
        updateType();
    }

    public int getStreakText() {
        return _streakText;
    }

    public int getStreakVideo() {
        return _streakVideo;
    }

    abstract public void updateType();

    @Override
    abstract public String toString();
}
