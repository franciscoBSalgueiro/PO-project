package prr.clients;

public class NormalClient extends ClientType {
	public NormalClient(Client c) { super(c); }
 
	@Override
	public String toString() {
		return "NORMAL";
	}
}
