using System;
using System.Diagnostics;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace LinkBridge.Windows.Services.Connection
{
    public enum ConnectionState
    {
        Disconnected,
        Connecting,
        Connected,
        Error
    }

    public class ConnectionManager
    {
        private TcpClient? _tcpClient;
        private StreamReader? _reader;
        private StreamWriter? _writer;

        public event Action<ConnectionState>? OnStateChanged;
        public event Action<string>? OnMessageReceived;

        public ConnectionState State { get; private set; } = ConnectionState.Disconnected;

        private void ChangeState(ConnectionState newState)
        {
            State = newState;
            OnStateChanged?.Invoke(newState);
        }

        public async Task ConnectAsync(string ipAddress, int port)
        {
            ChangeState(ConnectionState.Connecting);
            try
            {
                _tcpClient = new TcpClient();
                await _tcpClient.ConnectAsync(ipAddress, port);
                
                var stream = _tcpClient.GetStream();
                _reader = new StreamReader(stream, Encoding.UTF8);
                _writer = new StreamWriter(stream, Encoding.UTF8) { AutoFlush = true };

                ChangeState(ConnectionState.Connected);
                
                _ = Task.Run(ListenForMessages);
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"Connection error: {ex.Message}");
                ChangeState(ConnectionState.Error);
            }
        }

        private async Task ListenForMessages()
        {
            try
            {
                while (_reader != null)
                {
                    var message = await _reader.ReadLineAsync();
                    if (message == null) break;
                    
                    OnMessageReceived?.Invoke(message);
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"Read error: {ex.Message}");
            }
            finally
            {
                Disconnect();
            }
        }

        public async Task SendMessageAsync(string json)
        {
            if (_writer != null && State == ConnectionState.Connected)
            {
                await _writer.WriteLineAsync(json);
            }
        }

        public void Disconnect()
        {
            _reader?.Dispose();
            _writer?.Dispose();
            _tcpClient?.Close();
            
            _reader = null;
            _writer = null;
            _tcpClient = null;

            ChangeState(ConnectionState.Disconnected);
        }
    }
}
