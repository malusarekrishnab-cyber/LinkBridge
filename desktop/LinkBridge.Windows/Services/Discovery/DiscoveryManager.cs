using System;
using System.Diagnostics;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace LinkBridge.Windows.Services.Discovery
{
    public class DiscoveryManager
    {
        private const int DiscoveryPort = 5555;
        private UdpClient? _udpClient;

        public event Action<string>? OnDeviceFound;

        public void StartListening()
        {
            _udpClient = new UdpClient(DiscoveryPort);
            Task.Run(ListenForDevices);
        }

        private async Task ListenForDevices()
        {
            while (true)
            {
                try
                {
                    if (_udpClient == null) break;
                    var result = await _udpClient.ReceiveAsync();
                    var message = Encoding.UTF8.GetString(result.Buffer);
                    
                    // Simple parse of JSON (placeholder)
                    if (message.Contains("discovery"))
                    {
                        OnDeviceFound?.Invoke(result.RemoteEndPoint.Address.ToString());
                    }
                }
                catch (Exception ex)
                {
                    Debug.WriteLine($"Discovery error: {ex.Message}");
                }
            }
        }
        
        public void StopListening()
        {
            _udpClient?.Close();
            _udpClient = null;
        }
    }
}
