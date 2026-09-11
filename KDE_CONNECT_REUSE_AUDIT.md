# KDE Connect Reuse Audit

## 1. Overview
The workspace contains the Android source code for KDE Connect (`kdeconnect-android`). The Windows desktop application source code for KDE Connect is not present in the provided workspace.

## 2. License Analysis
- **Primary License**: GNU General Public License v2 (GPLv2) or later (found in `COPYING`).
- **Implications**: GPLv2 is a strong copyleft license. Any derivative work or software that links to or incorporates GPLv2 code must also be licensed under GPLv2 (or a compatible license) and its source code must be made publicly available.
- **Decision on Code Reuse**: Because LinkBridge is required to have its "own architecture, protocol, and identity", and given the strong copyleft nature of GPLv2, **direct code reuse is not recommended** unless LinkBridge is also intended to be released under the GPLv2 license. We will use the KDE Connect source code strictly as a **technical reference** for understanding how certain Android APIs and network concepts are handled.

## 3. Modules Reviewed
The KDE Connect Android codebase is modular and structured around a core device/network system and a plugin architecture.

### 3.1 Device Discovery & Networking
- **Location**: `src/main/java/org/kde/kdeconnect/backends/lan`
- **Mechanism**: Uses UDP broadcasts and mDNS (via `NsdManager` or `JmDNS`) for discovering devices on the local network (`LanLinkProvider.java`, `MdnsDiscovery.kt`).
- **Encryption**: Uses TLS over TCP for secure communication once a device is discovered.

### 3.2 Pairing
- **Location**: `src/main/java/org/kde/kdeconnect/PairingHandler.kt`, `Device.kt`
- **Mechanism**: Exchanges RSA public keys during the pairing phase. Subsequent connections use these keys to authenticate devices.

### 3.3 Plugin System
- **Location**: `src/main/java/org/kde/kdeconnect/plugins`
- **Mechanism**: Each feature is an independent plugin (`Plugin.kt`, `PluginFactory.kt`) that registers for specific packet types.

### 3.4 Key Features Analyzed
- **Remote Input**: Located in `plugins/mousepad` and `plugins/remotekeyboard`. Maps touch events to mouse coordinates and sends virtual keyboard events.
- **Clipboard**: Located in `plugins/clipboard`. Monitors Android clipboard changes and syncs them.
- **File Transfer**: Located in `plugins/share`. Uses standard Android `ContentResolver` and streams data over the network socket.
- **Notifications**: Located in `plugins/notifications` and `plugins/receivenotifications`. Uses `NotificationListenerService` to intercept notifications.
- **Media Control**: Located in `plugins/mpris` and `plugins/mprisreceiver`. Interfaces with Android's `MediaSessionManager`.
- **Device Information**: Located in `plugins/battery` and `plugins/connectivityreport`. Uses Android's `BatteryManager` and `TelephonyManager`.

## 4. Risks & Recommendations
### Risks
- **License Violation**: Accidentally copying GPLv2 code into LinkBridge could legally require LinkBridge to be open-sourced under the GPL.
- **Protocol Coupling**: Adopting KDE Connect's packet structure (JSON-based with specific `type` fields like `kdeconnect.clipboard`) will tie LinkBridge to KDE Connect's legacy architecture, violating the requirement for a custom protocol.
- **USB Support**: KDE Connect relies primarily on LAN/Wi-Fi and does not have robust native USB transport built-in for Android-to-Windows (it typically requires adb port forwarding). LinkBridge requires first-class USB support.

### Recommendations
1. **Clean Room Implementation**: Do not copy any source files from KDE Connect. Write LinkBridge from scratch using C#/.NET for Windows and Kotlin for Android.
2. **Custom Protocol**: Design a new, lightweight, and transport-agnostic protocol (as requested in the specification) using TCP/UDP or WebSockets, without reusing KDE Connect's packet schemas.
3. **Reference Only**: Use KDE Connect strictly to understand complex Android APIs (like `NotificationListenerService` or `MediaSessionManager`), but implement the logic independently.
4. **Custom Architecture**: Build a transport layer that transparently handles both Wi-Fi and USB, which KDE Connect lacks natively.

## 5. Conclusion
LinkBridge will be built entirely from scratch. The KDE Connect source code will serve only as a reference for Android API capabilities.
