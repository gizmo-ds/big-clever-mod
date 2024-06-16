# big-clever-mod

[![爱发电](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fafdian.net%2Fapi%2Fuser%2Fget-profile%3Fuser_id%3D75e549844b5111ed8df552540025c377&query=%24.data.user.name&label=%E7%88%B1%E5%8F%91%E7%94%B5&color=%23946ce6)](https://afdian.net/a/gizmo)
[![License](https://img.shields.io/github/license/gizmo-ds/big-clever-mod)](./LICENSE)

This is a simple mod for the minecraft.

## Features

### Server Tunneling `Forge/Fabric`

This mod supports server connection tunneling using non-TCP protocols such as WebSockets, initially implemented with
support for [wstunnel](https://github.com/erebe/wstunnel). It is designed to hide the server’s IP and enable CDN
protection, thereby preventing IP leaks and reducing vulnerability to DDoS attacks. While the current version
specifically supports wstunnel, the feature is not limited to it and can be extended to other tools in the future.
