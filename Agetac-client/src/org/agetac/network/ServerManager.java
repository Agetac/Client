package org.agetac.network;

public class ServerManager {

	private static ServerManager instance = new ServerManager();

	private ServerManager() {}

	public static ServerManager getInstance() {
		return instance;
	}
}
