package application.Utill;

public interface SocketListener {
    public void onMessage(String line);
    public void onClosedStatus(boolean isClosed);
}
