using System;
using System.Runtime.InteropServices;

namespace LinkBridge.Windows.Services.RemoteControl
{
    public class InputSimulator
    {
        [DllImport("user32.dll")]
        private static extern void mouse_event(uint dwFlags, int dx, int dy, uint dwData, int dwExtraInfo);
        
        [DllImport("user32.dll")]
        private static extern void keybd_event(byte bVk, byte bScan, uint dwFlags, int dwExtraInfo);

        private const uint MOUSEEVENTF_MOVE = 0x0001;
        private const uint MOUSEEVENTF_LEFTDOWN = 0x0002;
        private const uint MOUSEEVENTF_LEFTUP = 0x0004;
        private const uint MOUSEEVENTF_RIGHTDOWN = 0x0008;
        private const uint MOUSEEVENTF_RIGHTUP = 0x0010;
        private const uint MOUSEEVENTF_WHEEL = 0x0800;

        private const uint KEYEVENTF_KEYUP = 0x0002;

        public void MoveMouse(int dx, int dy)
        {
            mouse_event(MOUSEEVENTF_MOVE, dx, dy, 0, 0);
        }

        public void Click(string button)
        {
            if (button.Equals("left", StringComparison.OrdinalIgnoreCase))
            {
                mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);
                mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
            }
            else if (button.Equals("right", StringComparison.OrdinalIgnoreCase))
            {
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0);
            }
        }

        public void Scroll(int delta)
        {
            mouse_event(MOUSEEVENTF_WHEEL, 0, 0, (uint)delta, 0);
        }

        public void KeyPress(byte keyCode)
        {
            keybd_event(keyCode, 0, 0, 0);
            keybd_event(keyCode, 0, KEYEVENTF_KEYUP, 0);
        }
    }
}
