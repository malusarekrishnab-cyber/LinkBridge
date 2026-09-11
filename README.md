# LinkBridge

"One Bridge. All Your Devices."

LinkBridge is a native Phone ↔ Windows desktop application ecosystem designed to seamlessly connect your Android phone and Windows PC over Wi-Fi or USB.

## Features (Phase 1)
- Device discovery via mDNS / UDP
- QR Code pairing
- Secure connection using RSA public/private keys

## Architecture
- **Windows Client:** C# / .NET 8 / WPF / MVVM
- **Android Client:** Kotlin / Jetpack Compose / MVVM
- **Protocol:** Custom JSON-based protocol decoupled from the underlying transport.

See `ARCHITECTURE.md` and `PROTOCOL.md` for more details.
