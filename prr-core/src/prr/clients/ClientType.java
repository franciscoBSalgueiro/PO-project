package prr.clients;

import java.io.Serializable;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.plans.PaymentPlan;

public abstract class ClientType implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
    private Client _client;
    private int _streakText;
    private int _streakVideo;
    protected PaymentPlan _plan; // FIXME: maybe should be private?

    public ClientType(Client client, int streakText, int streakVideo) {
        _client = client;
        _streakText = streakText;
        _streakVideo = streakVideo;
    }

    public int getTextCost(TextCommunication communication) {
        return _plan.getTextCost(communication);
    }

    public int getVoiceCost(VoiceCommunication communication) {
        return _plan.getVoiceCost(communication);
    }

    public int getVideoCost(VideoCommunication communication) {
        return _plan.getVideoCost(communication);
    }

    public Client getClient() {
        return _client;
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
