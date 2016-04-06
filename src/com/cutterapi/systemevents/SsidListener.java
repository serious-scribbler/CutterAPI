package com.cutterapi.systemevents;
/*

Copyright (C) 2015 Phil Niehus

The CutterAPI is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

The CutterAPI is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
/**
 * Interface for SsidListeners - Overwrite the onSsidInfoUpdate to interpret the informations about connected and available wireless networks
 * @author Phil Niehus
 *
 */
public interface SsidListener {
	void onSsidInfoUpdate(NetworkInformation update);
}
