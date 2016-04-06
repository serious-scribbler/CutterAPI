package com.cutterapi.systemevents;

/**
 * Interface for SsidListeners - Overwrite the onSsidInfoUpdate to interpret the informations about connected and available wireless networks
 * @author Phil
 *
 */
public interface SsidListener {
	void onSsidInfoUpdate(NetworkInformation update);
}
