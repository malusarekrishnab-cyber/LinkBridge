using System;
using System.Diagnostics;
using System.Collections.Generic;

namespace LinkBridge.Windows.Services.Command
{
    public class CommandService
    {
        // Whitelist of allowed, safe commands to prevent arbitrary execution
        private readonly HashSet<string> _allowedCommands = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
        {
            "lock_workstation",
            "mute_volume",
            "unmute_volume",
            "play_pause_media"
        };

        public bool ExecuteCommand(string commandName)
        {
            if (!_allowedCommands.Contains(commandName))
            {
                Debug.WriteLine($"Blocked unsafe or unknown command: {commandName}");
                return false;
            }

            try
            {
                switch (commandName.ToLower())
                {
                    case "lock_workstation":
                        Process.Start(new ProcessStartInfo("rundll32.exe", "user32.dll,LockWorkStation") { CreateNoWindow = true });
                        return true;
                    // Additional safe commands will be routed here
                    default:
                        Debug.WriteLine($"Command {commandName} allowed but not fully implemented yet.");
                        return false;
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"Command execution failed: {ex.Message}");
                return false;
            }
        }
    }
}
