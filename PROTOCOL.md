# LinkBridge Protocol

## Overview
The LinkBridge Protocol is a transport-agnostic, JSON-based messaging protocol designed to allow Android devices and Windows PCs to communicate.

## Packet Structure
Every packet sent over a TCP or UDP socket (or WebSocket) must follow this schema:

```json
{
  "version": 1,
  "type": "<string>",
  "action": "<string>",
  "requestId": "<uuid>",
  "payload": {
    // Arbitrary JSON payload
  }
}
```

## Packet Types
- `discovery`: Used for UDP/mDNS broadcast.
- `pairing`: Used during the RSA public key exchange and verification.
- `command`: Used for features like remote mouse, keyboard, media controls.
- `clipboard`: Used for clipboard syncing.
- `status`: Used to communicate connection quality, battery level, etc.

## Phase 1 Packets

### Discovery
```json
{
  "version": 1,
  "type": "discovery",
  "action": "announce",
  "requestId": "...",
  "payload": {
    "deviceName": "My Phone",
    "deviceId": "uuid",
    "deviceType": "android",
    "tcpPort": 5555
  }
}
```

### Pairing
```json
{
  "version": 1,
  "type": "pairing",
  "action": "request",
  "requestId": "...",
  "payload": {
    "publicKey": "base64-encoded-rsa-public-key"
  }
}
```
