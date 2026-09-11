using System;
using System.Windows;

namespace LinkBridge.Windows.Services.Clipboard
{
    public class ClipboardService
    {
        public void SetText(string text)
        {
            Application.Current.Dispatcher.Invoke(() =>
            {
                try
                {
                    System.Windows.Clipboard.SetText(text);
                }
                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine($"Failed to set clipboard: {ex.Message}");
                }
            });
        }

        public string GetText()
        {
            return Application.Current.Dispatcher.Invoke(() =>
            {
                try
                {
                    if (System.Windows.Clipboard.ContainsText())
                    {
                        return System.Windows.Clipboard.GetText();
                    }
                }
                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine($"Failed to get clipboard: {ex.Message}");
                }
                return string.Empty;
            });
        }
    }
}
